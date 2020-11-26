package com.nc.project.dao.compound;

import com.nc.project.model.Action;
import com.nc.project.model.Compound;

import java.util.List;

public interface CompoundDao {

    Compound createCompound(Compound compound);

    Compound findActionById(int id);

    List<Compound> findAllCompounds();

    List<Compound> findAllCompoundsByPage(int size, int number);

    Compound editCompound(Compound compound);

    void removeCompound(int id);

}
