package org.cientopolis.samplers.model;

import org.cientopolis.samplers.ui.take_sample.PhotoFragment;


/**
 * Created by Xavier on 06/06/2016.
 */
public class PhotoStep extends BaseStep {

    private String instructionsToShow;
    // TODO: 15/03/2017 Add functionality to support this
    private String imageToOverlay;
    private Integer nextStepId;

    public PhotoStep(int id, String anInstructionsToShow, String anImageToOverlay, Integer nextStepId) {
        super(id);
        stepFragmentClass = PhotoFragment.class;
        instructionsToShow = anInstructionsToShow;
        imageToOverlay = anImageToOverlay; //comment here!
        this.nextStepId = nextStepId;
    }

    public String getInstructionsToShow() {
        return instructionsToShow;
    }

    public String getImageToOverlay() {
        return imageToOverlay;
    }

    @Override
    public Integer getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Integer nextStepId) {
        this.nextStepId = nextStepId;
    }

}
