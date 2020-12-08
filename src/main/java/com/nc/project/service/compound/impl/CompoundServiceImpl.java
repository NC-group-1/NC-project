package com.nc.project.service.compound.impl;

import com.nc.project.dao.compound.CompoundDao;
import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.compound.CompoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Slf4j
@Service
public class CompoundServiceImpl implements CompoundService {

    private final CompoundDao compoundDao;

    public CompoundServiceImpl(CompoundDao compoundDao) {
        this.compoundDao = compoundDao;
    }

//rewrite
    @Override
    public Action createCompound(Compound compound) {
        if (compound == null) {
            throw new NoSuchElementException("Compound not found");
        }
        ActionOfCompound[] actions = compound.getActions();
        List<ActionOfCompound> actionsOfCompound = new ArrayList<>();
        Arrays.stream(actions).forEach(action -> {
            if (action.getAction().getType().equals(ActionType.COMPOUND)) {
                actionsOfCompound.addAll(compoundDao.getActionsOfCompound(action.getAction().getId()));
            } else {
                actionsOfCompound.add(action);
            }
        });
        IntStream.range(0, actionsOfCompound.size()).forEach(i -> actionsOfCompound.get(i).setOrderNum(i + 1));
        compound.setActions(actionsOfCompound.toArray(ActionOfCompound[]::new));
        return compoundDao.createCompound(compound);
    }

    @Override
    public Compound getCompoundById(int id) {
        Compound compoundById = compoundDao.findCompoundById(id);
        compoundById.setActions(compoundDao.getActionsOfCompound(id).toArray(ActionOfCompound[]::new));
        return compoundById;
    }

    @Override
    public List<Compound> getAllCompounds() {
        return compoundDao.findAllCompounds();
    }

    @Override
    public Action editCompound(Action compound) {
        return compoundDao.editCompound(compound);
    }

    @Override
    public void deleteCompound(int compoundId) {
        compoundDao.removeCompound(compoundId);
    }

    @Override
    public List<ActionOfCompound> getActionsOfCompound(int compoundId) {
        return compoundDao.getActionsOfCompound(compoundId);
    }

    @Override
    public void postActionInCompound(ActionOfCompound actionOfCompound, int compoundId) {
        compoundDao.postActionInCompound(actionOfCompound, compoundId);
    }

    @Override
    public void deleteActionFromCompound(int actionId, int compoundId) {
        compoundDao.deleteActionFromCompound(actionId, compoundId);
    }

//    @Override
//    public Page<Compound> getCompoundsByPage(int page, int size) {
//        int numberOfCompounds = compoundDao.getNumberOfCompounds();
//        Page<Compound> pageOfCompounds = new Page<>();
//        if (numberOfCompounds > size * page) {
//            pageOfCompounds.setList(compoundDao.getCompoundsByPage(size, size * page));
//            pageOfCompounds.setSize(numberOfCompounds);
//        }
//        return pageOfCompounds;
//    }

    @Override
    public void editActionsOrderInCompound(Action[] actions, int compoundId) {
        compoundDao.editActionsOrderInCompound(actions, compoundId);
    }

    @Override
    public Page<Compound> getCompoundsByPage(Integer page, Integer size, String filterName, String filterDescription, String orderBy, String direction) {
        int numberOfCompounds = compoundDao.getNumberOfCompounds(filterName, filterDescription);
        String order = (orderBy.equals("description") ? "description " : "name ")
                + (direction.toLowerCase().equals("desc") ? "desc" : "asc");
        Page<Compound> pageOfCompounds = new Page<>();
        if (numberOfCompounds > size * page) {
            pageOfCompounds.setList(compoundDao.getCompoundsByPage(size, size * page, filterName, filterDescription, order));
            pageOfCompounds.setSize(numberOfCompounds);
        }
        return pageOfCompounds;
    }

    @Override
    public void changeActions(ActionOfCompound[] actions, Integer compoundId) {
        compoundDao.changeActions(actions, compoundId);
    }
}
