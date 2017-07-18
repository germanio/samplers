package org.cientopolis.samplers.model;

import org.cientopolis.samplers.ui.take_sample.SelectOneFragment;


import java.util.ArrayList;

/**
 * Created by Xavier on 27/06/2016.
 */
public class SelectOneStep extends BaseStep {

    private ArrayList<SelectOption> optionsToSelect;
    private String title;


    public SelectOneStep(int id) {

        this(id,new ArrayList<SelectOption>(),"");
    }

    public SelectOneStep(int id, ArrayList<SelectOption> anOptionsToSelect, String title) {
        super(id);
        stepFragmentClass = SelectOneFragment.class;
        optionsToSelect = anOptionsToSelect;
        this.title = title;
    }

    public ArrayList<SelectOption> getOptionsToSelect() {
        return optionsToSelect;
    }

    public SelectOption getSelectedOption() {
        SelectOption selectedOption = null;

        for (SelectOption option: optionsToSelect) {
            if (option.isSelected()) {
                selectedOption = option;
                break;
            }
        }

        return selectedOption;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Integer getNextStepId() {
        if (stepResult == null) {
            throw new RuntimeException("You must set the StepResult first");
        }


        return null;
    }


}
