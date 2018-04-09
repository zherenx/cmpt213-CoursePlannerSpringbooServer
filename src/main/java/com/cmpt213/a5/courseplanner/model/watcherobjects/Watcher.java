package com.cmpt213.a5.courseplanner.model.watcherobjects;

import com.cmpt213.a5.courseplanner.model.dataobjects.SimpleCourse;
import com.cmpt213.a5.courseplanner.model.dataobjects.SimpleDepartment;

import java.util.ArrayList;
import java.util.List;

public class Watcher {
    private long watcherId;
    private SimpleDepartment department;
    private SimpleCourse course;
    private List<String> events = new ArrayList<>();

    public Watcher(long deptId, String subject, long courseId, String catalogNumber, long watcherId) {
        this.watcherId = watcherId;
        department = new SimpleDepartment(deptId, subject);
        course = new SimpleCourse(courseId, catalogNumber);
    }

    public long getWatcherId() {
        return watcherId;
    }

    public void setWatcherId(long watcherId) {
        this.watcherId = watcherId;
    }

    public SimpleDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SimpleDepartment department) {
        this.department = department;
    }

    public SimpleCourse getCourse() {
        return course;
    }

    public void setCourse(SimpleCourse course) {
        this.course = course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
