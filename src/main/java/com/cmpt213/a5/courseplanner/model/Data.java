package com.cmpt213.a5.courseplanner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {

//    private static Data dataInstance;

    private List<Department> departments = new ArrayList<>();

//    public static Data getInstance() {
//        if (dataInstance == null) {
//            dataInstance = new Data();
//        }
//        return dataInstance;
//    }

    public Data() {

    }

    public Data(List<RawData> rawData) {

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

                departments.add(new Department(departmentRawData));

                departmentRawData.clear();
                departmentRawData.add(currentRawData);
            }
        }
        // add the last department.
        departments.add(new Department(departmentRawData));

    }


    public void printInModelDumpFormat() {
        for (Department department: departments) {
            department.printInModeDumpFormat();
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
