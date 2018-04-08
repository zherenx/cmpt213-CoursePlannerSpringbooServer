package com.cmpt213.a5.courseplanner.model;

import java.io.File;
import java.util.List;

public class DataManager {
    private static DataManager instance = new DataManager();

    private Data data;

    public static DataManager getInstance() {
//        if (instance == null) {
//            instance = new DataManager();
//        }
        return instance;
    }

    private DataManager() {
//        String pathname = "./";
//        String pathname = "./data/test_data.csv";
//        String pathname = "./data/course_data_2016.csv";
        String pathname = "./data/course_data_2018.csv";

        File csvFile = new File(pathname);

        data = DataProcessor.processCourseDataFile(csvFile);
    }

    public void printInModelDumpFormat() {
        data.printInModelDumpFormat();
    }

    public List<Department> getAllDepartments() {
        return data.getDepartments();
    }

    public List<Course> getAllCoursesOfDepartment(long departmentId) {
        return data.getAllCoursesOfDepartment(departmentId);
    }
}
