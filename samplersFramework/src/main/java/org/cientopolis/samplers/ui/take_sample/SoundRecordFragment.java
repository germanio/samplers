package org.cientopolis.samplers.ui.take_sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import org.cientopolis.samplers.R;
import org.cientopolis.samplers.model.SoundRecordStep;
import org.cientopolis.samplers.model.SoundRecordStepResult;
import org.cientopolis.samplers.model.Step;
import org.cientopolis.samplers.model.StepResult;
import org.cientopolis.samplers.service.RecordingService;

import java.io.File;

/**
 * Created by laura on 06/09/17.
 */

public class SoundRecordFragment extends StepFragment {

    private Chronometer mChronometer;
    private TextView mRecordingPrompt;
    private Button mRecordButton;

    private int mRecordPromptCount = 0;
    long timeWhenPaused = 0; //stores time when user clicks pause button

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;

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
        return new SoundRecordStepResult(getStep().getId(),"");
    }

    /*functionality*/
    private void onRecord(boolean start){

        /**
         * // Explicit intent to wrap
         Intent intent = new Intent(this, LoginActivity.class);

         // Create pending intent and wrap our intent
         PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
         try {
         // Perform the operation associated with our pendingIntent
         pendingIntent.send();
         } catch (PendingIntent.CanceledException e) {
         e.printStackTrace();
         }
         */

        /**
         * int seconds = 3;
         // Create an intent that will be wrapped in PendingIntent
         Intent intent = new Intent(this, MyReceiver.class);

         // Create the pending intent and wrap our intent
         PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

         // Get the alarm manager service and schedule it to go off after 3s
         AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
         alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (seconds * 1000), pendingIntent);

         Toast.makeText(this, "Alarm set in " + seconds + " seconds", Toast.LENGTH_LONG).show();*/

        Intent intent = new Intent(getActivity(), RecordingService.class);
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
            getActivity().startService(intent);
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

            getActivity().stopService(intent);
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
