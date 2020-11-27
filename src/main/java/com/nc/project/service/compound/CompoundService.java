package com.nc.project.service.compound;

import com.nc.project.model.Compound;

import java.util.List;

public interface CompoundService {

    Compound createCompound(Compound compound);

    Compound getCompoundById(int id);

    List<Compound> getAllCompounds();

    Compound editCompound(Compound compound);

    void deleteCompound(Compound deleteCompound);
}
