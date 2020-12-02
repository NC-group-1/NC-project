package com.nc.project.service.compound;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CompoundService {

    Action createCompound(Compound compound);

    Compound getCompoundById(int id);

    List<Compound> getAllCompounds();

    Action editCompound(Action compound);

    void deleteCompound(int compoundId);

    List<ActionOfCompound> getActionsOfCompound(int compoundId);

    void postActionInCompound(ActionOfCompound actionOfCompound, int compoundId);

    void deleteActionFromCompound(int actionId, int compoundId);

//    Page<Compound> getCompoundsByPage(int page, int size);

    void editActionsOrderInCompound(Action[] actions, int compoundId);

    Page<Compound> getCompoundsByPage(Integer page, Integer size, String filterName, String filterDescription, String orderBy, String direction);

    void changeActions(ActionOfCompound[] actions, Integer compoundId);
}
