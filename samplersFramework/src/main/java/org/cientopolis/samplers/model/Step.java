package org.cientopolis.samplers.model;

import org.cientopolis.samplers.ui.take_sample.StepFragment;

import java.io.Serializable;

/**
 * Created by Xavier on 06/06/2016.
 */
public interface Step extends Serializable{

    public <T extends StepFragment> Class<T> getStepFragmentClass();
    public <T extends StepFragment> void setStepFragmentClass(Class<T> type);

    public int getId();
    public Integer getNextStepId();

    public void setStepResult(StepResult stepResult);
    public StepResult getStepResult();
}
