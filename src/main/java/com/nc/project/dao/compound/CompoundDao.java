package com.nc.project.dao.compound;

import com.nc.project.model.Action;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CompoundDao {

    Compound createCompound(Compound compound);

    Compound findCompoundById(int id);

    List<Compound> findAllCompounds();

    Action editCompound(Action compound);

    void removeCompound(int id);

    List<ActionOfCompound> getActionsOfCompound(int compoundId);

    void postActionInCompound(ActionOfCompound compound, int compoundId);

    void deleteActionFromCompound(int actionId, int compoundId);

    List<Compound> getCompoundsByPage(int limit, int offset, String filterName, String filterDescription, String orderByWithDirection);

    Integer getNumberOfCompounds(String name, String description);

    void editActionsOrderInCompound(Action[] actions, int compoundId);
}
