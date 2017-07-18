package org.cientopolis.samplers;

import org.cientopolis.samplers.model.InformationStep;
import org.cientopolis.samplers.model.MultipleSelectStep;
import org.cientopolis.samplers.model.PhotoStep;
import org.cientopolis.samplers.model.SelectOneStep;
import org.cientopolis.samplers.model.SelectOption;
import org.cientopolis.samplers.model.Step;
import org.cientopolis.samplers.model.Workflow;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by lilauth on 3/14/17.
 */

public class SamplersModelUnitTest {

    private MultipleSelectStep multipleSelectStep;

    @Test
    public void informationStep() throws Exception {
        InformationStep is = new InformationStep(1, "an informative text");
        assertEquals("an informative text", is.getTextToShow());
        assertEquals(1, is.getId());
    }

    @Test
    public void emptyMultipleSelectStep() throws Exception {
        multipleSelectStep = new MultipleSelectStep(2);
        assertEquals(0, multipleSelectStep.getOptionsToSelect().size());
        assertEquals(2, multipleSelectStep.getId());
    }

    @Test
    public void MultipleSelectStepWithSelectedOptions() throws Exception {
        //options
        ArrayList<SelectOption> optionsToSelect = new ArrayList<SelectOption>();
        optionsToSelect.add(new SelectOption(1,"Option 1", false));
        optionsToSelect.add(new SelectOption(2,"Option 2", false));
        SelectOption selectedOption1 = new SelectOption(3,"Option 3", true);
        optionsToSelect.add(selectedOption1);
        SelectOption selectedOption2 = new SelectOption(4,"Option 4", true);
        optionsToSelect.add(selectedOption2);
        // MultipleSelectStep
        multipleSelectStep = new MultipleSelectStep(3, optionsToSelect,"Seleccione lo que ve");
        //
        assertEquals(2, multipleSelectStep.getSelectedOptions().size());
        assertEquals(3, multipleSelectStep.getId());
    }

    @Test
    public void PhotoStepTest () throws Exception {
        PhotoStep photoStep = new PhotoStep(4, "instructions","path to image");
        assertEquals("instructions", photoStep.getInstructionsToShow());
        assertEquals(4, photoStep.getId());
    }

    @Test
    public void SelectOneStepTestWithSelection () throws Exception {
        //options
        ArrayList<SelectOption> optionsToSelect = new ArrayList<SelectOption>();
        optionsToSelect.add(new SelectOption(1,"Option 1", false));
        optionsToSelect.add(new SelectOption(2,"Option 2", false));
        SelectOption selectedOption1 = new SelectOption(3,"Option 3", true);
        optionsToSelect.add(selectedOption1);
        SelectOption selectedOption2 = new SelectOption(4,"Option 4", true);
        optionsToSelect.add(selectedOption2);

        SelectOneStep selectOneStep = new SelectOneStep(5,optionsToSelect, "Seleccione uno solo");
        //returns the first selected
        assertEquals("Option 3", selectOneStep.getSelectedOption().getTextToShow());
        assertEquals(5, selectOneStep.getId());
    }

    @Test
    public void workflowTestNextStep () throws Exception {
        //options
        Step step0 = new InformationStep(0,"paso 0");
        Step step1 = new InformationStep(1,"paso 1");
        Step step2 = new InformationStep(2,"paso 2");
        Step step3 = new InformationStep(3,"paso 3");

        Workflow workflow = new Workflow();

        workflow.addStep(step0);
        workflow.addStep(step1);
        workflow.addStep(step2);
        workflow.addStep(step3);

        workflow.nextStep(); //step 0
        Step testStep = workflow.nextStep(); //step 1

        assertEquals(1,workflow.getStepPosition());
        assertEquals(1, testStep.getId());
    }

    @Test
    public void workflowTestOutOfBounds () throws Exception {
        Step step0 = new InformationStep(0, "step 0");
        Step step1 = new InformationStep(1, "step 1");

        Workflow workflow = new Workflow();

        workflow.addStep(step0);
        workflow.addStep(step1);

        workflow.nextStep(); //step 0
        workflow.nextStep(); //step 1
        workflow.nextStep(); //inexisting step
        workflow.nextStep(); //inexisting step
        //
        assertEquals(1, workflow.getStepPosition());
    }

    @Test
    public void workflowTestPriortStep () throws Exception {
        //options
        Step step0 = new InformationStep(0,"step 0");
        Step step1 = new InformationStep(1,"step 1");
        Step step2 = new InformationStep(2,"step 2");
        Step step3 = new InformationStep(3,"step 3");

        Workflow workflow = new Workflow();

        workflow.addStep(step0);
        workflow.addStep(step1);
        workflow.addStep(step2);
        workflow.addStep(step3);

        workflow.nextStep(); //step 0
        workflow.nextStep(); //step 1
        workflow.nextStep(); //step 2
        workflow.nextStep(); //step 3

        Step testStep = workflow.previuosStep(); //step 2

        assertEquals(2,workflow.getStepPosition());
        assertEquals(2, testStep.getId());
    }
}
