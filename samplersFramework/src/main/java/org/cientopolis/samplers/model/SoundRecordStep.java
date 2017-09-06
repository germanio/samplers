package org.cientopolis.samplers.model;

import org.cientopolis.samplers.ui.take_sample.SoundRecordFragment;

/**
 * Created by laura on 05/09/17.
 */

public class SoundRecordStep extends BaseStep {

    private String instructionsToShow;
    private Integer nextStepId;

    public SoundRecordStep(int id, String anInstructionsToShow, Integer nextStepId){
        super(id);
        this.instructionsToShow = anInstructionsToShow;
        this.nextStepId = nextStepId;
        stepFragmentClass = SoundRecordFragment.class;
    }

    @Override
    public Integer getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Integer nextStepId) {

        this.nextStepId = nextStepId;
    }


}
