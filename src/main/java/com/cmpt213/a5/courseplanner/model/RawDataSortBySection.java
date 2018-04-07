package com.cmpt213.a5.courseplanner.model;

import java.util.Comparator;

public class RawDataSortBySection implements Comparator<RawData> {
    @Override
    public int compare(RawData o1, RawData o2) {
        if (o1.getSemester() < o2.getSemester()) {
            return -1;
        } else if (o1.getSemester() == o2.getSemester()) {
            return o1.getLocation().compareTo(o2.getLocation());
        } else {
            return 1;
        }
    }
}
