package com.cmpt213.a5.courseplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RawData {

    @JsonIgnore
    private final int NUMBER_OF_COLUMNS = 8;

    private int semester;

    @JsonProperty("subjectName")
    private String subject;

    private String catalogNumber;
    private String location;

    @JsonProperty("enrollmentCap")
    private int enrollmentCapacity;

    private int enrollmentTotal;
    private String instructor;

    @JsonProperty("component")
    private String componentCode;


    public RawData() {

    }

    public RawData(String[] args) {
        if (args.length != NUMBER_OF_COLUMNS) {
            System.out.println("Invalid raw data line detected,");
            for (String col: args) {
                System.out.println("/" + col + "/");
            }

            semester = -1;
            subject = "N/A";
            catalogNumber = "N/A";
            location = "N/A";
            enrollmentCapacity = -1;
            enrollmentTotal = -1;
            instructor = "N/A";
            componentCode = "N/A";
            return;
        }

        semester = Integer.parseInt(args[0]);

        subject = args[1];

        catalogNumber = args[2];

        location = args[3];

        enrollmentCapacity = Integer.parseInt(args[4]);

        enrollmentTotal = Integer.parseInt(args[5]);

        instructor = args[6];

        componentCode = args[7];
    }



    public boolean isSameSubject(RawData other) {
        return subject.equals(other.getSubject());
    }

    public boolean hasSameCatalogNumber(RawData other) {
        return catalogNumber.equals(other.getCatalogNumber());
    }

    public boolean isSameOffering(RawData other) {
        return location.equals(other.getLocation()) && semester == other.getSemester();
    }

    public boolean isSameComponent(RawData other) {
        return componentCode.equals(other.componentCode);
    }

    public boolean isSameSemester(RawData other) {
        return semester == other.getSemester();
    }




    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public void setEnrollmentCapacity(int enrollmentCapacity) {
        this.enrollmentCapacity = enrollmentCapacity;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }



}
