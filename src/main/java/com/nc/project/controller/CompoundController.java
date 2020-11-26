package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.Compound;
import com.nc.project.service.library.CompoundService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compound")
public class CompoundController {
    private final CompoundService compoundService;

    public CompoundController(CompoundService compoundService) {
        this.compoundService = compoundService;
    }

//    @GetMapping
//    public List<Action> getCompounds(){
//        return compoundService.getAllCompounds();
//    }
    @GetMapping
    public Page<Action> getCompounds(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        return compoundService.getCompoundsByPage(page, size);
    }

    @PostMapping
    public Action postCompound(@RequestBody Action compoundAsAction){
        return compoundService.createCompound(compoundAsAction);
    }
    @GetMapping("{id}")
    public Action getCompoundById(@PathVariable int id){
        return compoundService.getCompoundById(id);
    }
    @PutMapping("update")
    public Action updateCompound(@RequestBody Action compoundAsAction){
        return compoundService.editCompound(compoundAsAction);
    }
    @DeleteMapping("update")
    public void deleteCompound(@RequestBody Action compoundAsAction){
        compoundService.deleteCompound(compoundAsAction);
    }

    @GetMapping("action/{id}")
    public List<Action> getActionsOfCompound(@PathVariable int id){
        return compoundService.getActionsOfCompound(id);
    }
    @PostMapping("action")
    public void postActionInCompound(@RequestBody Compound compound){
        compoundService.postActionInCompound(compound);
    }
    @DeleteMapping("action")
    public void removeActionFromCompound(@RequestBody Compound compound){
        compoundService.deleteActionFromCompound(compound);
    }
}
