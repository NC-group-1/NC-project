package com.nc.project.service.library.impl;

import com.nc.project.dao.compound.CompoundDao;
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
    public Compound createCompound(Compound compound) {
        if (compound == null) {
            throw new NoSuchElementException("Compound not found");
        }
        return compoundDao.createCompound(compound);
    }

    @Override
    public Compound getCompoundById(int id) {
        return compoundDao.findActionById(id);
    }

    @Override
    public List<Compound> getAllCompounds() {
        return compoundDao.findAllCompounds();
    }

    @Override
    public Compound editCompound(Compound compound) {
        return compoundDao.editCompound(compound);
    }

    @Override
    public void deleteCompound(Compound deleteCompound) {
        compoundDao.removeCompound(deleteCompound.getCompoundId());
    }
}
