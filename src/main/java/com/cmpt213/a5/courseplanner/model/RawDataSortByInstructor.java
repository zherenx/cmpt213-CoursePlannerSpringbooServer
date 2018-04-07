package com.cmpt213.a5.courseplanner.model;

import java.util.Comparator;

public class RawDataSortByInstructor implements Comparator<RawData> {
    @Override
    public int compare(RawData o1, RawData o2) {
        return o1.getInstructor().compareTo(o2.getInstructor());
    }
}
