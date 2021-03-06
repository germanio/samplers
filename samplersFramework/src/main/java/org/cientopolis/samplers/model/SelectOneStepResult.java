package org.cientopolis.samplers.model;


/**
 * Created by Xavier on 07/02/2017.
 */

public class SelectOneStepResult extends StepResult {

    private SelectOneOption selectedOption;

    public SelectOneStepResult(int stepId, SelectOneOption selectedOption) {
        super(stepId);
        this.selectedOption = selectedOption;
    }

    public SelectOneOption getSelectedOption() {
        return selectedOption;
    }
}
