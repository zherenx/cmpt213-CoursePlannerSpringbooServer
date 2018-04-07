package com.cmpt213.a5.courseplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {

    private String subject;

    private List<Course> courses = new ArrayList<>();


    public Department() {

    }

    public Department(List<RawData> departmentRawData) {
        subject = departmentRawData.get(0).getSubject();


        Collections.sort(departmentRawData, new RawDataByCatalogNumber());

        List<RawData> courseRawData = new ArrayList<>();

        courseRawData.add(departmentRawData.get(0));

        for (int i = 1; i < departmentRawData.size(); i++) {

            RawData previousRawData = departmentRawData.get(i - 1);
            RawData currentRawData = departmentRawData.get(i);

            if (currentRawData.hasSameCatalogNumber(previousRawData)) {
                courseRawData.add(currentRawData);
            } else {

                courses.add(new Course(courseRawData));

                courseRawData.clear();
                courseRawData.add(currentRawData);
            }
        }
        courses.add(new Course(courseRawData));

    }


    public void printInModeDumpFormat() {
        for (Course course: courses) {
            course.printInModeDumpFormat();
        }
    }
}
