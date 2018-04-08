package com.cmpt213.a5.courseplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Data {

    private List<Department> departments = new ArrayList<>();

    public Data() {

    }

    public Data(List<RawData> rawData) {

        AtomicLong nextId = new AtomicLong();


        if (rawData.size() == 0) {
            return;
        }


        Collections.sort(rawData, new RawDataSortBySubject());

        List<RawData> departmentRawData = new ArrayList<>();

        departmentRawData.add(rawData.get(0));

        for (int i = 1; i < rawData.size(); i++) {

            RawData previousRawData = rawData.get(i - 1);
            RawData currentRawData = rawData.get(i);

            if (currentRawData.isSameSubject(previousRawData)) {
                departmentRawData.add(currentRawData);
            } else {

                departments.add(new Department(departmentRawData, nextId.incrementAndGet()));

                departmentRawData.clear();
                departmentRawData.add(currentRawData);
            }
        }
        // add the last department.
        departments.add(new Department(departmentRawData, nextId.incrementAndGet()));

    }


    public void printInModelDumpFormat() {
        for (Department department: departments) {
            department.printInModeDumpFormat();
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Course> getAllCoursesOfDepartment(long departmentId) {
        for (Department department: departments) {
            if (department.getDepartmentId() == departmentId) {
                return department.getCourses();
            }
        }
        // TODO: throw exception.
        return null;
    }
}
