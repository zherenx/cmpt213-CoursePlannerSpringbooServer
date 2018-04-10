package com.cmpt213.a5.courseplanner.controllers;

import com.cmpt213.a5.courseplanner.model.*;
import com.cmpt213.a5.courseplanner.model.dataobjects.*;
import com.cmpt213.a5.courseplanner.model.managers.DataManager;
import com.cmpt213.a5.courseplanner.model.managers.WatcherManager;
import com.cmpt213.a5.courseplanner.model.watcherobjects.Watcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoursePlannerController {

    private DataManager dataManager = DataManager.getInstance();

    private WatcherManager watcherManager = WatcherManager.getInstance();

    @GetMapping("/api/about")
    public Object getDescription() {
        return new Object() {
            public String appName = "Course Planner";
            public String authorName = "Zheren (Justin) Xiao";
        };
    }

    @GetMapping("/api/dump-model")
    public void processToModelDumpFormat() {
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

    @GetMapping("/api/stats/students-per-semester")
    public List<GraphData> getGraphDataOfDepartment(@RequestParam(value = "deptId") long departmentId) {
        return dataManager.getGraphDataOfDepartment(departmentId);
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody RawData newRawData) {
        dataManager.addOffering(newRawData);
        watcherManager.processNewEvent(newRawData);
    }

    @GetMapping("/api/watchers")
    public List<Watcher> getWatchers() {
        return watcherManager.getWatchers();
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewWatcher(@RequestBody WatcherRequestBody watcherRequestBody) {
        String subject = dataManager.getSubjectById(watcherRequestBody.deptId);
        String catalogNumber = dataManager.getCatalogNumberOfCourse(watcherRequestBody.deptId, watcherRequestBody.courseId);
        watcherManager.addNewWatcher(watcherRequestBody.deptId, subject, watcherRequestBody.courseId, catalogNumber);
    }

    @GetMapping("/api/watchers/{id}")
    public List<String> getEventsByWatcherId(@PathVariable("id") long watcherId) {
        return watcherManager.getEventsByWatcherId(watcherId);
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcherById(@PathVariable("id") long watcherId) {
        watcherManager.deleteWatcherById(watcherId);
    }

}
