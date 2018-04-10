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
    private List<GraphData> graphDataList = new ArrayList<>();

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


        // construct graph data.
        Collections.sort(departmentRawData, new RawDataSortBySemester());

        RawData oldestData = departmentRawData.get(0);
        RawData newestData = departmentRawData.get(departmentRawData.size() - 1);

        graphDataList = getDummyGraphDataList(oldestData.getSemester(), newestData.getSemester());

        for (RawData rawData: departmentRawData) {
            if (rawData.getComponentCode().equals("LEC")) {
                for (GraphData graphData: graphDataList) {
                    if (rawData.getSemester() == graphData.getSemesterCode()) {
                        graphData.incrementEnrollmentTotal(rawData.getEnrollmentTotal());
                    }
                }
            }
        }

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

    public List<GraphData> getGraphDataList() {
        return graphDataList;
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

        // update graphDataList.
        if (newRawData.getSemester() < graphDataList.get(0).getSemesterCode()) {
            List<GraphData> newGraphDataList = getDummyGraphDataList(
                    newRawData.getSemester(),
                    graphDataList.get(0).getSemesterCode());
            newGraphDataList.remove(newGraphDataList.size() - 1);
            if (newRawData.getComponentCode().equals("LEC")) {
                newGraphDataList.get(0).incrementEnrollmentTotal(newRawData.getEnrollmentTotal());
            }
            graphDataList.addAll(newGraphDataList);
            Collections.sort(graphDataList);
        } else if (newRawData.getSemester() > graphDataList.get(graphDataList.size() - 1).getSemesterCode()) {
            List<GraphData> newGraphDataList = getDummyGraphDataList(
                            graphDataList.get(graphDataList.size() - 1).getSemesterCode(),
                            newRawData.getSemester());
            newGraphDataList.remove(0);
            if (newRawData.getComponentCode().equals("LEC")) {
                newGraphDataList.get(newGraphDataList.size() - 1).incrementEnrollmentTotal(newRawData.getEnrollmentTotal());
            }
            graphDataList.addAll(newGraphDataList);
            Collections.sort(graphDataList);
        } else {
            for (GraphData graphData: graphDataList) {
                if (newRawData.getSemester() == graphData.getSemesterCode()) {
                    graphData.incrementEnrollmentTotal(newRawData.getEnrollmentTotal());
                }
            }
        }
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

    private List<GraphData> getDummyGraphDataList(int oldestSemester, int newestSemester) {
        List<GraphData> newGraphDataList = new ArrayList<>();
        int currentSemester = oldestSemester;
        // semesterOffset indicates which term it is, 0 == Spring, 1 == Summer, 2 == Fall.
        int semesterOffset = currentSemester % 10 / 3;
        while(currentSemester <= newestSemester) {
            newGraphDataList.add(new GraphData(currentSemester, 0));
            switch (semesterOffset) {
                case 0:
                    currentSemester += 3;
                    break;
                case 1:
                    currentSemester += 3;
                    break;
                case 2:
                    currentSemester += 4;
                    break;
            }
            semesterOffset = (semesterOffset + 1) % 3;
        }
        return newGraphDataList;
    }
}
