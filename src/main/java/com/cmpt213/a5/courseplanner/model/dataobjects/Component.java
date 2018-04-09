package com.cmpt213.a5.courseplanner.model.dataobjects;

import com.cmpt213.a5.courseplanner.model.RawData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Component implements Comparable<Component> {

    @JsonProperty("type")
    private String componentCode;

    @JsonProperty("enrollmentCap")
    private int enrollmentCapacity;

    private int enrollmentTotal;

    public Component() {

    }

    public Component(List<RawData> componentRawData) {
        componentCode = componentRawData.get(0).getComponentCode();
        enrollmentCapacity = 0;
        enrollmentTotal = 0;

        for (RawData data: componentRawData) {
            enrollmentCapacity += data.getEnrollmentCapacity();
            enrollmentTotal += data.getEnrollmentTotal();
        }
    }


    // getters and setters for json fields.
    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public void setEnrollmentCapacity(int enrollmentCapacity) {
        this.enrollmentCapacity = enrollmentCapacity;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }


    public void printInModeDumpFormat() {
        System.out.println("\t\tType=" + componentCode + ", Enrollment=" + enrollmentTotal + "/" + enrollmentCapacity);
    }

    public void updateComponent(RawData newRawData) {
        enrollmentCapacity += newRawData.getEnrollmentCapacity();
        enrollmentTotal += newRawData.getEnrollmentTotal();
    }

    @Override
    public int compareTo(Component o) {
        return componentCode.compareTo(o.getComponentCode());
    }
}
