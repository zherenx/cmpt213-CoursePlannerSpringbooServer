package com.cmpt213.a5.courseplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Course {

    private long courseId;

    private String catalogNumber;


    @JsonIgnore
    private String subject;

    @JsonIgnore
    private List<Section> sections = new ArrayList<>();

    public Course() {

    }

    public Course(List<RawData> courseRawData, long courseId) {

        AtomicLong nextId = new AtomicLong();

        this.courseId = courseId;
        catalogNumber = courseRawData.get(0).getCatalogNumber();

        subject = courseRawData.get(0).getSubject();

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

                sections.add(new Section(sectionRawData, nextId.incrementAndGet()));

                sectionRawData.clear();
                sectionRawData.add(currentRawData);
            }
        }
        sections.add(new Section(sectionRawData, nextId.incrementAndGet()));
    }

    public void printInModeDumpFormat() {
        System.out.println(subject + " " + catalogNumber);
        for (Section section: sections) {
            section.printInModeDumpFormat();
        }
    }
}
