package com.nc.project.service.library.impl;

import com.nc.project.dao.compound.CompoundDao;
import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.Compound;
import com.nc.project.service.library.CompoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class CompoundServiceImpl implements CompoundService {

    private final CompoundDao compoundDao;

    public CompoundServiceImpl(CompoundDao compoundDao) {
        this.compoundDao = compoundDao;
    }

//rewrite
    @Override
    public Action createCompound(Action action) {
        if (action == null) {
            throw new NoSuchElementException("Compound not found");
        }
        return compoundDao.createCompound(action);
    }

    @Override
    public Action getCompoundById(int id) {
        return compoundDao.findCompoundById(id);
    }

    @Override
    public List<Action> getAllCompounds() {
        return compoundDao.findAllCompounds();
    }

    @Override
    public Action editCompound(Action compound) {
        return compoundDao.editCompound(compound);
    }

    @Override
    public void deleteCompound(Action deleteCompound) {
        compoundDao.removeCompound(deleteCompound.getId());
    }

    @Override
    public List<Action> getActionsOfCompound(int compoundId) {
        return compoundDao.getActionsOfCompound(compoundId);
    }

    @Override
    public void postActionInCompound(Compound compound) {
        compoundDao.postActionInCompound(compound);
    }

    @Override
    public void deleteActionFromCompound(Compound compound) {
        compoundDao.deleteActionFromCompound(compound);
    }

    @Override
    public Page<Action> getCompoundsByPage(int page, int size) {
        int numberOfCompounds = compoundDao.getNumberOfCompounds();
        Page<Action> pageOfCompounds = new Page<>();
        if (numberOfCompounds >= size * page) {
            pageOfCompounds.setList(compoundDao.getCompoundsByPage(size, (size * page)-size));
            pageOfCompounds.setSize(numberOfCompounds);
        }
        return pageOfCompounds;
    }
}
