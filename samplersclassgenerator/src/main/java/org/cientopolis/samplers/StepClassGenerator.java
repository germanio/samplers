package org.cientopolis.samplers;

import java.util.List;

/**
 * Created by Xavier on 02/06/2017.
 */

public interface StepClassGenerator {

    public List<String> generateStep(int stepIndex, String workflow_var);
}
