package com.cmpt213.a5.courseplanner.model.dataobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.RawDataSortByCatalogNumber;
import com.cmpt213.a5.courseplanner.model.RawDataSortBySemester;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Department implements Comparable<Department> {

    @JsonProperty("deptId")
    private long departmentId;

    @JsonProperty("name")
    private String subject;



    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    @JsonIgnore
    private List<GraphData> graphData = new ArrayList<>();

    @JsonIgnore
    private AtomicLong nextId = new AtomicLong();


    public Department() {

    }

    public Department(List<RawData> departmentRawData, long departmentId) {

        this.departmentId = departmentId;
        subject = departmentRawData.get(0).getSubject();

        Collections.sort(departmentRawData, new RawDataSortByCatalogNumber());

        List<RawData> courseRawData = new ArrayList<>();

        courseRawData.add(departmentRawData.get(0));

        for (int i = 1; i < departmentRawData.size(); i++) {

            RawData previousRawData = departmentRawData.get(i - 1);
            RawData currentRawData = departmentRawData.get(i);

            if (currentRawData.hasSameCatalogNumber(previousRawData)) {
                courseRawData.add(currentRawData);
            } else {

                courses.add(new Course(courseRawData, nextId.incrementAndGet()));

                courseRawData.clear();
                courseRawData.add(currentRawData);
            }
        }
        courses.add(new Course(courseRawData, nextId.incrementAndGet()));


//        // construct graph data.
//        Collections.sort(departmentRawData, new RawDataSortBySemester());
//
//        GraphData graphDataOfOneSemester;
//
//        if (departmentRawData.get(0).getComponentCode().equals("LEC")) {
//            graphDataOfOneSemester =
//                    new GraphData(departmentRawData.get(0).getSemester(), departmentRawData.get(0).getEnrollmentTotal());
//        } else {
//            graphDataOfOneSemester =
//                    new GraphData(departmentRawData.get(0).getSemester(), 0);
//        }
//
//        for (int i = 1; i < departmentRawData.size(); i++) {
//            RawData previousRawData = departmentRawData.get(i - 1);
//            RawData currentRawData = departmentRawData.get(i);
//
//            if (currentRawData.isSameSemester(previousRawData)) {
//                if (currentRawData.getComponentCode().equals("LEC")) {
//                    graphDataOfOneSemester.incrementEnrollmentTotal(currentRawData.getEnrollmentTotal());
//                }
//            } else {
//                graphData.add(graphDataOfOneSemester);
//
//                int gap = currentRawData.getSemester() - previousRawData.getSemester();
//
//                // one year gap == three semester gap.
//                int yearGap = gap / 10;
//                int semesterGap = gap % 10;
//
//                // number of semesters between previous and current semester (in included).
//                int offset = yearGap * 3 + semesterGap / 3 - 1;
//
//                int previousTermCode = previousRawData.getSemester() % 10;
//
//                int semesterOffset = previousTermCode / 3;
//
//
//                for (int counter = 0; counter < offset; counter++) {
//
//                }
//
//            }
//        }

    }


    // getters and setters for json fields.
    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    public void printInModeDumpFormat() {
        for (Course course: courses) {
            course.printInModeDumpFormat();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Offering> getOfferingsOfCourse(long courseId) {
        for (Course course: courses) {
            if (course.getCourseId() == courseId) {
                return course.getOfferings();
            }
        }
        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }

    public List<Component> getComponentsOfOffering(long courseId, long offeringId) {
        for (Course course: courses) {
            if (course.getCourseId() == courseId) {
                return course.getComponentsOfOffering(offeringId);
            }
        }
        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }

    public List<GraphData> getGraphData() {
        return graphData;
    }

    public void addOffering(RawData newRawData) {
        boolean isNewCourse = true;
        for (Course course: courses) {
            if (newRawData.getCatalogNumber().equals(course.getCatalogNumber())) {
                course.addOffering(newRawData);
                isNewCourse = false;
            }
        }
        if (isNewCourse) {
            List<RawData> newRawDataList = new ArrayList<>();
            newRawDataList.add(newRawData);
            courses.add(new Course(newRawDataList, nextId.incrementAndGet()));
            Collections.sort(courses);
        }
        // TODO: update graph data.
    }

    public String getCatalogNumberById(long courseId) {
        for (Course course: courses) {
            if (course.getCourseId() == courseId) {
                return course.getCatalogNumber();
            }
        }
        throw new CourseNotFoundException("Course of ID " + courseId + " not found.");
    }

    @Override
    public int compareTo(Department o) {
        return subject.compareTo(o.getSubject());
    }
}
