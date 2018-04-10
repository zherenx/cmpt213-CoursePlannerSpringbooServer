package com.cmpt213.a5.courseplanner.model.dataobjects;

/**
 * This class is a simple version of Course class;
 * it includes information needed for watcher class.
 */
public class SimpleCourse {
    private long courseId;
    private String catalogNumber;

    public SimpleCourse(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
}
