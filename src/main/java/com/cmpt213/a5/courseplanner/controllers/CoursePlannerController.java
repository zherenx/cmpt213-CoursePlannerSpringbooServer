package com.cmpt213.a5.courseplanner.controllers;

import com.cmpt213.a5.courseplanner.model.*;
import com.cmpt213.a5.courseplanner.model.dataobjects.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {

    private DataManager dataManager = DataManager.getInstance();

//    private Data data;

    @GetMapping("/api/about")
    public Object getDescription() {
        return new Object() {
            public String appName = "Course Planner";
            public String authorName = "Zheren (Justin) Xiao";
        };
    }

    @GetMapping("/api/dump-model")
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

    @GetMapping("/api/departments")
    public List<Department> getDepartments() {
        return dataManager.getDepartments();
    }

    @GetMapping("/api/departments/{id}/courses")
    public List<Course> getCoursesOfDepartment(@PathVariable("id") long departmentId) {
        return dataManager.getCoursesOfDepartment(departmentId);
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public List<Offering> getSectionsOfCourse(
            @PathVariable("deptId") long departmentId,
            @PathVariable("courseId") long courseId) {
        return dataManager.getOfferingsOfCourse(departmentId, courseId);
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public List<Component> getComponentOfOffering(
            @PathVariable("deptId") long departmentId,
            @PathVariable("courseId") long courseId,
            @PathVariable("offeringId") long offeringId) {
        return dataManager.getComponentsOfOffering(departmentId, courseId, offeringId);
    }

//    @GetMapping("/api/stats/students-per-semester")
//    public List<GraphData> getGraphDataOfDepartment(@RequestParam(value = "deptId") long departmentId) {
//        return dataManager.getGraphDataOfDepartment(departmentId);
//    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody RawData newRawData) {
        dataManager.addOffering(newRawData);
    }

}
