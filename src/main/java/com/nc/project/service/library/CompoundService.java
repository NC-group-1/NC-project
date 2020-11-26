package com.nc.project.service.library;

import com.nc.project.model.Action;
import com.nc.project.model.Compound;

import java.util.List;

public interface CompoundService {

    Compound createCompound(Compound compound);

    Compound getCompoundById(int id);

    List<Compound> getAllCompoundByPage(int page, int size);

    List<Compound> getAllCompounds();

    Compound editCompound(Compound compound);

    void deleteCompound(Compound deleteCompound);
}
