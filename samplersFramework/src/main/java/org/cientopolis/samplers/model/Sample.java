package org.cientopolis.samplers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Xavier on 07/02/2017.
 */

public class Sample implements Serializable {

    private Long id;
    private List<StepResult> steps;
    private Date startDateTime;
    private Date endDateTime;

    public Sample(){
        startDateTime = new Date();
        steps = new ArrayList<StepResult>();
    }

    public void addStepResult(StepResult stepResult) {
        steps.add(stepResult);
    }

    public List<StepResult> getStepResults() {
        return steps;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
