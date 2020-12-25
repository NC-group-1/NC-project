package com.nc.project.service.runTestCase.impl;

import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.runTestCase.SharedContainerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SharedContainerServiceImpl implements SharedContainerService {

    private final ConcurrentHashMap<Integer, CopyOnWriteArrayList<ActionInstRunDto>> sharedStorage;
    private final ConcurrentHashMap<Integer, TestingStatus> targetStatuses;

    public SharedContainerServiceImpl() {
        sharedStorage = new ConcurrentHashMap<>();
        targetStatuses = new ConcurrentHashMap<>();
    }

    @Override
    public List<ActionInstRunDto> getFromSharedStorage(Integer testCaseId) {
        return sharedStorage.get(testCaseId);
    }

    protected TestingStatus getFromTargetStatuses(Integer testCaseId) {
        return targetStatuses.get(testCaseId);
    }

    protected void putToTargetStatuses(Integer testCaseId, TestingStatus testingStatus) {
        targetStatuses.put(testCaseId, testingStatus);
    }

    protected void initInSharedMaps(Integer testCaseId) {
        targetStatuses.put(testCaseId, TestingStatus.IN_PROGRESS);
        sharedStorage.put(testCaseId, new CopyOnWriteArrayList<>());
    }

    protected void deleteFromSharedMaps(Integer testCaseId) {
        sharedStorage.remove(testCaseId);
        targetStatuses.remove(testCaseId);
    }

}
