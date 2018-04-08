package com.cmpt213.a5.courseplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Offering {

    private long courseOfferingId;

    private String location;

    private List<String> instructors = new ArrayList<>();

    private int year;
    private int semesterCode;
    private String term;


    @JsonIgnore
    private List<Component> components = new ArrayList<>();

    public Offering() {

    }

    public Offering(List<RawData> offeringRawData, long courseOfferingId) {

        this.courseOfferingId = courseOfferingId;
        location = offeringRawData.get(0).getLocation();
        semesterCode = offeringRawData.get(0).getSemester();

        year = 1900 + semesterCode / 100;

        switch (semesterCode % 10) {
            case 1:
                term = "Spring";
                break;
            case 4:
                term = "Summer";
                break;
            case 7:
                term = "Fall";
                break;
            default:
                term = "Invalid input";
                System.out.println("Error, semester code " + semesterCode + " is invalid.");
                break;
        }

        // get all instructors.
        Collections.sort(offeringRawData, new RawDataSortByInstructor());
        instructors.add(offeringRawData.get(0).getInstructor());
        for (int i = 1; i < offeringRawData.size(); i++) {
            String previousInstructor = offeringRawData.get(i - 1).getInstructor();
            String currentInstructor = offeringRawData.get(i).getInstructor();
            if (!currentInstructor.equals(previousInstructor)) {
                instructors.add(currentInstructor);
            }
        }

        Collections.sort(offeringRawData, new RawDataSortByComponentCode());

        List<RawData> componentRawData = new ArrayList<>();

        componentRawData.add(offeringRawData.get(0));

        for (int i = 1; i < offeringRawData.size(); i++) {

            RawData previousRawData = offeringRawData.get(i - 1);
            RawData currentRawData = offeringRawData.get(i);

            if (currentRawData.isSameComponent(previousRawData)) {
                componentRawData.add(currentRawData);
            } else {

                components.add(new Component(componentRawData));

                componentRawData.clear();
                componentRawData.add(currentRawData);
            }
        }
        components.add(new Component(componentRawData));
    }


    public void printInModeDumpFormat() {
        System.out.print("\t" + semesterCode + " in " + location + " by " + instructors.get(0));
        for (int i = 1; i < instructors.size(); i++) {
            System.out.print(", " + instructors.get(i));
        }
        System.out.println();

        for (Component component: components) {
            component.printInModeDumpFormat();
        }
    }


    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public List<Component> getComponents() {
        return components;
    }
}
