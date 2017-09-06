package org.cientopolis.samplers.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by laura on 06/09/17.
 */

public class RecordingService extends Service {
    private MediaRecorder mRecorder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public interface OnTimerChangedListener {
        void onTimerChanged(int seconds);
    }


    public void startRecording(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

    }
}
