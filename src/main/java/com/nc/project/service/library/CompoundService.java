package com.nc.project.service.library;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.Compound;

import java.util.List;

public interface CompoundService {

    Action createCompound(Action action);

    Action getCompoundById(int id);

    List<Action> getAllCompounds();

    Action editCompound(Action compound);

    void deleteCompound(Action deleteCompound);

    List<Action> getActionsOfCompound(int compoundId);

    void postActionInCompound(Compound compound);

    void deleteActionFromCompound(Compound compound);

    Page<Action> getCompoundsByPage(int page, int size);
}
