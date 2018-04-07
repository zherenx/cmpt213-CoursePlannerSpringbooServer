package com.cmpt213.a5.courseplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Section {

    private int semester;
    private String location;
    private List<String> instructors = new ArrayList<>();

    private List<Component> components = new ArrayList<>();

    public Section() {

    }

    public Section(List<RawData> sectionRawData) {
        semester = sectionRawData.get(0).getSemester();
        location = sectionRawData.get(0).getLocation();

        // get all instructors.
        Collections.sort(sectionRawData, new RawDataSortByInstructor());
        instructors.add(sectionRawData.get(0).getInstructor());
        for (int i = 1; i < sectionRawData.size(); i++) {
            String previousInstructor = sectionRawData.get(i - 1).getInstructor();
            String currentInstructor = sectionRawData.get(i).getInstructor();
            if (!currentInstructor.equals(previousInstructor)) {
                instructors.add(currentInstructor);
            }
        }

        Collections.sort(sectionRawData, new RawDataSortByComponentCode());

        List<RawData> componentRawData = new ArrayList<>();

        componentRawData.add(sectionRawData.get(0));

        for (int i = 1; i < sectionRawData.size(); i++) {

            RawData previousRawData = sectionRawData.get(i - 1);
            RawData currentRawData = sectionRawData.get(i);

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
        System.out.print("\t" + semester + " in " + location + " by " + instructors.get(0));
        for (int i = 1; i < instructors.size(); i++) {
            System.out.print(", " + instructors.get(i));
        }
        System.out.println();

        for (Component component: components) {
            component.printInModeDumpFormat();
        }
    }
}
