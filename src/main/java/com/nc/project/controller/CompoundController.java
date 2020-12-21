package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;

import com.nc.project.service.compound.CompoundService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/ncp/compound")
//@PreAuthorize("hasRole('MANAGER')")
public class CompoundController {
    private final CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

    @GetMapping
    public Page<Compound> getCompounds(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(defaultValue = "") String name,
                                       @RequestParam(defaultValue = "") String description,
                                       @RequestParam(defaultValue = "name") String orderBy,
                                       @RequestParam(defaultValue = "ASC") String direction){
        return compoundService.getCompoundsByPage(page, size, name, description, orderBy, direction);
    }

    @PostMapping
    public Action createCompound(@RequestBody Compound compound){
        return compoundService.createCompound(compound);
    }

    @GetMapping("{id}")
    public Compound getCompoundById(@PathVariable int id){
        return compoundService.getCompoundById(id);
    }
    @PutMapping("update")
    public Action updateCompound(@RequestBody Action compoundAsAction){
        return compoundService.editCompound(compoundAsAction);
    }
    @DeleteMapping("delete/{compoundId}")
    public void deleteCompound(@PathVariable int compoundId){
        compoundService.deleteCompound(compoundId);
    }

    @GetMapping("actions/{compoundId}")
    public List<ActionOfCompound> getActionsOfCompound(@PathVariable int compoundId){
        return compoundService.getActionsOfCompound(compoundId);
    }
    @PostMapping("actions/{compoundId}")
    public void postActionInCompound(@RequestBody ActionOfCompound action, @PathVariable int compoundId){
        compoundService.postActionInCompound(action, compoundId);
    }

    @DeleteMapping("actions/{compoundId}")
    public void removeActionFromCompound(@RequestBody int actionId, @PathVariable int compoundId){
        compoundService.deleteActionFromCompound(actionId, compoundId);
    }
    @PutMapping("actions/{compoundId}")
    public void changeActions(@RequestBody ActionOfCompound[] actions, @PathVariable int compoundId){
        compoundService.changeActions(actions, compoundId);
    }
}
