package com.galvanize.controller;

import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import com.galvanize.repository.JpaOfficerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class OfficerController {

    @Autowired
    JpaOfficerDao jpaOfficerDao;

    @PostMapping(path = "/officers")
    public Officer saveOfficer(@RequestBody Officer officer){
        return jpaOfficerDao.save(officer);
    }

    @GetMapping(path = "/officers")
    public List<Officer> getAllOfficers(){
        return jpaOfficerDao.findAll();
    }

    @GetMapping(path = "/officers/{rank}")
    public List<Officer> getOfficerByRank(@PathVariable Rank rank){
        return jpaOfficerDao.findByRank(rank);
    }

    @DeleteMapping(path = "/officers/{id}")
    public void deleteOfficer(@PathVariable Long id){
        jpaOfficerDao.deleteById(id);
    }

//    @PatchMapping(path = "/officers/{id}")
//    public Officer updateOfficerRankById(@PathVariable Long id,@RequestBody Rank rank) {
//        return jpaOfficerDao.updateOfficerRankById(id,rank);
//    }


}
