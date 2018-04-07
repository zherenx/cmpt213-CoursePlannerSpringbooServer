package com.cmpt213.a5.courseplanner.controllers;

import com.cmpt213.a5.courseplanner.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class CoursePlannerController {

    private DataManager dataManager = DataManager.getInstance();

//    private Data data;

    @GetMapping("/about")
    public String getDescription() {
        return "Course planner written by Zheren (Justin) Xiao!";
    }

    @GetMapping("/dump-model")
    public void processToModelDumpFormat() {

////        String pathname = "./";
////        String pathname = "./data/test_data.csv";
////        String pathname = "./data/course_data_2016.csv";
//        String pathname = "./data/course_data_2018.csv";
//
//        File csvFile = new File(pathname);
//
////        Data data;
//
//        data = DataProcessor.processCourseDataFile(csvFile);
//
//        data.printInModelDumpFormat();

        dataManager.printInModelDumpFormat();

    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {

        String pathname = "./data/course_data_2018.csv";

        File csvFile = new File(pathname);

        Data data;

        data = DataProcessor.processCourseDataFile(csvFile);

        return data.getDepartments();
    }

//    @GetMapping("/departments/{id}/courses")
//    public List<Course> getAllCoursesOfDepartment(@PathVariable("id") long departmentId) {
//
//    }


}
