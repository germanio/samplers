package org.cientopolis.samplers.ui.take_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import org.cientopolis.samplers.R;
import org.cientopolis.samplers.model.Step;
import org.cientopolis.samplers.model.StepResult;

/**
 * Created by laura on 06/09/17.
 */

public class SoundRecordFragment extends StepFragment {

    private Chronometer mChronometer;
    private TextView mRecordingPrompt;
    private Button mRecordButton;
    private Button mPauseButton;

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
        mPauseButton = (Button)  rootView.findViewById(R.id.btnPause);
        mPauseButton.setVisibility(View.GONE); //pause does not show until start recording


    }

    @Override
    protected <T extends Step> T getStep() {
        return null;
    }

    @Override
    protected boolean validate() {
        return false;
    }

    @Override
    protected StepResult getStepResult() {
        return null;
    }
}
