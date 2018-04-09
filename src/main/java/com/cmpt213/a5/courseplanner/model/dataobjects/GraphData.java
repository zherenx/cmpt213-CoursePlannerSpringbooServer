package com.cmpt213.a5.courseplanner.model.dataobjects;

public class GraphData {
    private int semesterCode;
    private int totalCoursesTaken;

    public GraphData(int semesterCode, int totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(int totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }

    public void incrementEnrollmentTotal(int enrollmentTotal) {
        totalCoursesTaken += enrollmentTotal;
    }
}
