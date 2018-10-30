package com.sagar.ccetmobileapp.network.models;

public class SignUpModel {

    private String email;
    private String firstName;
    private String lastName;
    private String admissionYear;
    private int admissionSemester;
    private String passWord;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public int getAdmissionSemester() {
        return admissionSemester;
    }

    public void setAdmissionSemester(int admissionSemester) {
        this.admissionSemester = admissionSemester;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
