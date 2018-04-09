package com.cmpt213.a5.courseplanner.model.watcherobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.dataobjects.SimpleCourse;
import com.cmpt213.a5.courseplanner.model.dataobjects.SimpleDepartment;

import java.util.ArrayList;
import java.util.Date;
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

    public void addNewEvent(RawData newRawData) {
        int semesterCode = newRawData.getSemester();
        String term;
        int year = 1900 + semesterCode / 10;

        switch (semesterCode % 10) {
            case 1:
                term = "Spring";
                break;
            case 4:
                term = "Summer";
                break;
            case 7:
                term = "Fall";
                break;
            default:
                term = "Invalid";
                System.out.println("Error, semester code " + semesterCode + " is invalid.");
                break;
        }

        Date currentTime = new Date();

        String newEvent = currentTime.toString()
                + ": Added section " + newRawData.getComponentCode()
                + " with enrollment (" + newRawData.getEnrollmentTotal()
                + " / " + newRawData.getEnrollmentCapacity()
                + ") to offering " + term + " " + year;

        events.add(newEvent);
    }
}
