package com.sagar.ccetmobileapp.network.models;


import com.sagar.ccetmobileapp.network.models.serverentities.Syllabus;

import java.util.List;

public class Syllabuses {

    private List<Syllabus> syllabuses;


    public Syllabuses(List<Syllabus> syllabusList) {
        this.syllabuses = syllabusList;
    }


    public List<Syllabus> getSyllabuses() {
        return syllabuses;
    }

    public void setAssignments(List<Syllabus> syllabuses) {
        this.syllabuses = syllabuses;
    }
}
