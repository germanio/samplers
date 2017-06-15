package org.cientopolis.samplers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier on 07/06/2017.
 */

public class MultipleSelectStepClassGenerator implements StepClassGenerator {

    private List<SelectOption> optionsToShow;
    private String title;

    public MultipleSelectStepClassGenerator(String title) {
        this.optionsToShow = new ArrayList<>();
        this.title = title;
    }

    public void addOptionToSelect(int id, String textToShow) {
        SelectOption selectOption = new SelectOption(id, textToShow);
        optionsToShow.add(selectOption);
    }

    @Override
    public List<String> generateStep(int stepIndex, String workflow_var) {

        List<String> output = new ArrayList<>();
        String varNameTitle = "multipleSelectTitle"+ String.valueOf(stepIndex);
        String varNameOptions = "optionsToSelect" + String.valueOf(stepIndex);

        XMLManagement.addString(varNameTitle, this.title);
        
        output.add("    String "+varNameTitle +" = getResources().getString(R.string."+varNameTitle+"); ");
        output.add("    ArrayList<SelectOption> "+varNameOptions +" = new ArrayList<SelectOption>();");

        for (int i=0; i<optionsToShow.size(); i++) {
            SelectOption option = optionsToShow.get(i);

            String varNameOptionText = "multipleSelectOptionText" + String.valueOf(stepIndex)+"_"+String.valueOf(i);
            XMLManagement.addString(varNameOptionText, option.getTextToShow());

            output.add("    String "+varNameOptionText +" = getResources().getString(R.string."+varNameOptionText+"); ");
            output.add("    "+varNameOptions +".add(new SelectOption("+option.getId()+","+varNameOptionText+", false));");
        }

        output.add("    "+workflow_var+".addStep(new MultipleSelectStep("+varNameOptions+","+varNameTitle+")); ");

        return output;
    }


}
