package com.cmpt213.a5.courseplanner.model.managers;

import com.cmpt213.a5.courseplanner.model.RawData;
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

    public List<String> getEventsByWatcherId(long watcherId) {
        for (Watcher watcher: watchers) {
            if (watcher.getWatcherId() == watcherId) {
                return watcher.getEvents();
            }
        }
        throw new ResourceNotFoundException("Unable to find requested watcher.");
    }

    public void deleteWatcherById(long watcherId) {
        for (int i = 0; i < watchers.size(); i++) {
            if (watchers.get(i).getWatcherId() == watcherId) {
                watchers.remove(i);
                return;
            }
        }
        throw new ResourceNotFoundException("Unable to find requested watcher.");
    }

    public void processNewEvent(RawData newRawData) {
        for (Watcher watcher: watchers) {
            if (watcher.getDepartment().getName().equals(newRawData.getSubject())) {
                watcher.addNewEvent(newRawData);
            }
        }
    }
}
