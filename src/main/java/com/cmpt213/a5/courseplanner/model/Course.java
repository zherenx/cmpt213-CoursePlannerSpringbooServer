package com.cmpt213.a5.courseplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {

    private String subject;
    private String catalogNumber;

    private List<Section> sections = new ArrayList<>();

    public Course() {

    }

    public Course(List<RawData> courseRawData) {
        subject = courseRawData.get(0).getSubject();
        catalogNumber = courseRawData.get(0).getCatalogNumber();


        // sort courseRawData by semester and location.
        Collections.sort(courseRawData, new RawDataSortBySection());

        List<RawData> sectionRawData = new ArrayList<>();

        sectionRawData.add(courseRawData.get(0));

        for (int i = 1; i < courseRawData.size(); i++) {

            RawData previousRawData = courseRawData.get(i - 1);
            RawData currentRawData = courseRawData.get(i);

            if (currentRawData.isSameSection(previousRawData)) {
                sectionRawData.add(currentRawData);
            } else {

                sections.add(new Section(sectionRawData));

                sectionRawData.clear();
                sectionRawData.add(currentRawData);
            }
        }
        sections.add(new Section(sectionRawData));
    }

    public void printInModeDumpFormat() {
        System.out.println(subject + " " + catalogNumber);
        for (Section section: sections) {
            section.printInModeDumpFormat();
        }
    }
}
