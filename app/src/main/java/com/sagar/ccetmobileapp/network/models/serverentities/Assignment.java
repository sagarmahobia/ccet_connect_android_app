package com.sagar.ccetmobileapp.network.models.serverentities;

public class Assignment {

    private int id;
    private int branchId;
    private int semester;
    private int assignmentNo;
    private String lastDate;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getAssignmentNo() {
        return assignmentNo;
    }

    public void setAssignmentNo(int assignmentNo) {
        this.assignmentNo = assignmentNo;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

}
