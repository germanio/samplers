package org.cientopolis.samplers.model;

import org.cientopolis.samplers.ui.take_sample.InformationFragment;


/**
 * Created by Xavier on 06/06/2016.
 */
public class InformationStep extends BaseStep {

    private Integer nextStepId;
    private String textToShow;

    public InformationStep(String aTextToShow) {
        stepFragmentClass = InformationFragment.class;
        textToShow = aTextToShow;
    }

    public String getTextToShow(){

        return textToShow;
    }


    @Override
    public Integer getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Integer nextStepId) {
        this.nextStepId = nextStepId;
    }
}
