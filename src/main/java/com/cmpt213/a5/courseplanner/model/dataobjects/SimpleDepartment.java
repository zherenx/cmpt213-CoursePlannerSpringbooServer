package com.cmpt213.a5.courseplanner.model.dataobjects;

/**
 * This class is a simple version of Department class;
 * it includes information needed for watcher class.
 */
public class SimpleDepartment {
    private long deptId;
    private String name;

    public SimpleDepartment(long deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
