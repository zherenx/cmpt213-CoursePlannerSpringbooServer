package com.cmpt213.a5.courseplanner.model.managers;

import com.cmpt213.a5.courseplanner.model.watcherobjects.Watcher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class WatcherManager {
    private static WatcherManager instance = new WatcherManager();

    private List<Watcher> watchers = new ArrayList<>();

    @JsonIgnore
    private AtomicLong nextId = new AtomicLong();

    public static WatcherManager getInstance() {
        return instance;
    }

    private WatcherManager() {
    }

    public List<Watcher> getWatchers() {
        return watchers;
    }

    public void addNewWatcher(long deptId, String subject, long courseId, String catalogNumber) {
        watchers.add(new Watcher(deptId, subject, courseId, catalogNumber, nextId.incrementAndGet()));
    }
}
