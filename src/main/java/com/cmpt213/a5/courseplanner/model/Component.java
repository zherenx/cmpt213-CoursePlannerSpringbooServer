package com.cmpt213.a5.courseplanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Component {

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

    public void printInModeDumpFormat() {
        System.out.println("\t\tType=" + componentCode + ", Enrollment=" + enrollmentTotal + "/" + enrollmentCapacity);
    }
}
