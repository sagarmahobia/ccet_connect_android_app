package com.sagar.ccetmobileapp.network.models.assignments;

import java.util.List;

public class Assignments {

    private List<Assignment> assignments;

    public Assignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }
}
