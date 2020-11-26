package com.nc.project.dao.compound;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.Compound;

import java.util.List;

public interface CompoundDao {

    Action createCompound(Action compound);

    Action findCompoundById(int id);

    List<Action> findAllCompounds();

    Action editCompound(Action compound);

    void removeCompound(int id);

    List<Action> getActionsOfCompound(int compoundId);

    void postActionInCompound(Compound compound);

    void deleteActionFromCompound(Compound compound);

    List<Action> getCompoundsByPage(int page, int size);

    Integer getNumberOfCompounds();
}
