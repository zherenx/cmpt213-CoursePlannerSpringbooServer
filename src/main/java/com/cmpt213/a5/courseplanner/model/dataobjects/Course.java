package com.cmpt213.a5.courseplanner.model.dataobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.cmpt213.a5.courseplanner.model.RawDataSortByOffering;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Course implements Comparable<Course> {

    private long courseId;

    private String catalogNumber;

    @JsonIgnore
    private String subject;

    @JsonIgnore
    private List<Offering> offerings = new ArrayList<>();

    @JsonIgnore
    private AtomicLong nextId = new AtomicLong();

    public Course() {

    }

    public Course(List<RawData> courseRawData, long courseId) {

        this.courseId = courseId;
        catalogNumber = courseRawData.get(0).getCatalogNumber();

        subject = courseRawData.get(0).getSubject();

        // sort courseRawData by semester and location.
        Collections.sort(courseRawData, new RawDataSortByOffering());

        List<RawData> OfferingRawData = new ArrayList<>();

        OfferingRawData.add(courseRawData.get(0));

        for (int i = 1; i < courseRawData.size(); i++) {

            RawData previousRawData = courseRawData.get(i - 1);
            RawData currentRawData = courseRawData.get(i);

            if (currentRawData.isSameOffering(previousRawData)) {
                OfferingRawData.add(currentRawData);
            } else {

                offerings.add(new Offering(OfferingRawData, nextId.incrementAndGet()));

                OfferingRawData.clear();
                OfferingRawData.add(currentRawData);
            }
        }
        offerings.add(new Offering(OfferingRawData, nextId.incrementAndGet()));
    }


    // getters and setters for json fields.
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



    public void printInModeDumpFormat() {
        System.out.println(subject + " " + catalogNumber);
        for (Offering offering : offerings) {
            offering.printInModeDumpFormat();
        }
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public List<Component> getComponentsOfOffering(long offeringId) {
        for (Offering offering: offerings) {
            if (offering.getCourseOfferingId() == offeringId) {
                return offering.getComponents();
            }
        }
        throw new OfferingNotFoundException("Course offering of ID " + offeringId + " not found.");
    }

    public void addOffering(RawData newRawData) {
        boolean isNewOffering = true;
        for (Offering offering: offerings) {
            if (newRawData.getSemester() == offering.getSemesterCode()
                    && newRawData.getLocation().equals(offering.getLocation())) {
                offering.updateOffering(newRawData);
                isNewOffering = false;
            }
        }
        if (isNewOffering) {
            List<RawData> newRawDataList = new ArrayList<>();
            newRawDataList.add(newRawData);
            offerings.add(new Offering(newRawDataList, nextId.incrementAndGet()));
            Collections.sort(offerings);
        }
    }

    @Override
    public int compareTo(Course o) {
        return catalogNumber.compareTo(o.catalogNumber);
    }
}
