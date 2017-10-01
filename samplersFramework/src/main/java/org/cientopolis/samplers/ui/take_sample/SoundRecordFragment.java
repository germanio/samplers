package org.cientopolis.samplers.ui.take_sample;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.cientopolis.samplers.R;
import org.cientopolis.samplers.model.SoundRecordStep;
import org.cientopolis.samplers.model.SoundRecordStepResult;
import org.cientopolis.samplers.model.Step;
import org.cientopolis.samplers.model.StepResult;
import org.cientopolis.samplers.service.RecordingItem;
import org.cientopolis.samplers.service.RecordingService;

import java.io.File;

/**
 * Created by laura on 06/09/17.
 */

public class SoundRecordFragment extends StepFragment {

    private Chronometer mChronometer;
    private TextView mRecordingPrompt;
    private Button mRecordButton;
    private Button mPlayButton;
    private String fileName;
    boolean mBound = false;
    private RecordingItem mRecordingItem;

    private int mRecordPromptCount = 0;
    long timeWhenPaused = 0; //stores time when user clicks pause button

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;
    private RecordingService mRecordingService;

    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RecordingService.LocalBinder binder = (RecordingService.LocalBinder) service;
            mRecordingService = binder.getService();
            mRecordingService.startRecording();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //
        }
    };


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sound_record;
    }

    @Override
    protected void onCreateViewStepFragment(View rootView, Bundle savedInstanceState) {
        mChronometer = (Chronometer) rootView.findViewById(R.id.chronometer);
        mRecordingPrompt = (TextView) rootView.findViewById(R.id.recording_status_text);
        //assign listeners
        mRecordButton = (Button) rootView.findViewById(R.id.btnStart);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });
        mPlayButton = (Button) rootView.findViewById(R.id.btnPlay);
        mPlayButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    PlaybackFragment playbackFragment = new PlaybackFragment().newInstance(mRecordingItem);
                    FragmentTransaction transaction = ((FragmentActivity) getActivity())
                            .getSupportFragmentManager()
                            .beginTransaction();

                    playbackFragment.show(transaction, "dialog_playback");

                } catch (Exception e) {
                    Log.e("playback exc", "exception", e);
                }
            }
        });
    }

    @Override
    protected SoundRecordStep getStep() {
        return (SoundRecordStep) step;
    }

    @Override
    protected boolean validate() {
        return true;
    }

    @Override
    protected StepResult getStepResult() {
        return new SoundRecordStepResult(getStep().getId(),fileName);
    }

    /*functionality*/
    private void onRecord(boolean start){

        Intent intent = new Intent(getActivity(), RecordingService.class);
        /** Permission for audio source*/
         if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 10);
         } else {


        /*comment to exclude permission request*/

        if (start) {
            // start recording
            /*this change image from "start" to "stop"*/
            //mRecordButton.setImageResource(R.drawable.ic_media_stop);
            mRecordButton.setText(getString(R.string.stop_recording_button));

            //start Chronometer
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (mRecordPromptCount == 0) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
                    } else if (mRecordPromptCount == 1) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "..");
                    } else if (mRecordPromptCount == 2) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "...");
                        mRecordPromptCount = -1;
                    }

                    mRecordPromptCount++;
                }
            });
            //start RecordingService
            getActivity().bindService(intent, mConnection, getActivity().BIND_AUTO_CREATE);
            //keep screen on while recording
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
            mRecordPromptCount++;

        } else {
            //stop recording
            //mRecordButton.setImageResource(R.drawable.ic_mic_white_36dp);
            mRecordButton.setText(getString(R.string.start_recording_button));
            mChronometer.stop();
            mChronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            mRecordingPrompt.setText(getString(R.string.record_prompt));

            //getActivity().stopService(intent);
            if(mBound) {
                fileName = mRecordingService.getFileName();
                mRecordingItem = new RecordingItem();
                mRecordingItem.setName(fileName);
                /*get Duration*/
                Uri uri = Uri.parse(fileName);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(getActivity(),uri);
                String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int millSecond = Integer.parseInt(durationStr);
                mRecordingItem.setLength(millSecond);

                mRecordingService.stopRecording();
                getActivity().unbindService(mConnection);
                mBound = false;
            }
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

         } /*comment to exclude permission request*/
    }

    /*test for playing recorded audio*/

    private void startPlaying() {
        //
    }

}
