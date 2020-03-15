package com.galvanize;

import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import com.galvanize.repository.JpaOfficerDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JpaOfficerDaoTest {

    @Autowired
    JpaOfficerDao jpaOfficerDao;

    @BeforeEach
    public void setup(){

        List<Officer> officerList = new ArrayList<>();

        officerList.add(new Officer(Rank.COMMANDER,"Krishna", "Karki"));
        officerList.add(new Officer(Rank.ADMIRAL,"Mike", "Johnson"));
        officerList.add(new Officer(Rank.CAPTAIN,"Mark", "Dailey"));
        officerList.add(new Officer(Rank.COMMANDER,"Michael", "Clark"));
        officerList.add(new Officer(Rank.ENSIGN,"Sammy", "Colmen"));

        officerList.forEach(officer->jpaOfficerDao.save(officer));
    }

    @Test
    public void countTest(){
        assertEquals(jpaOfficerDao.count(),5L);
    }

    @Test
    public void createOfficerTest(){
        Officer officer = new Officer(Rank.COMMANDER, "Krishna", "Karki");
        jpaOfficerDao.save(officer);
        System.out.println(officer.getId());
        assertEquals(jpaOfficerDao.count(),6L);
    }

    @Test
    public void findAllOfficersTest(){
        assertEquals(jpaOfficerDao.count(),5L);
        assertEquals(jpaOfficerDao.findAll().size(),5L);
    }

    @Test
    public void findByIdTest(){
        assertNotNull(jpaOfficerDao.findById(1L));
        assertNotNull(jpaOfficerDao.findById(2L));
        assertEquals(Optional.empty(),jpaOfficerDao.findById(100L));
    }

    @Test
    public void findByRankTest(){
        assertEquals(jpaOfficerDao.findByRank(Rank.COMMANDER).size(),2);
        assertEquals(jpaOfficerDao.findByRank(Rank.CAPTAIN).size(),1);
        assertEquals(jpaOfficerDao.findByRank(Rank.ENSIGN).size(),1);
    }

    @Test
    public void findByFirstNameAndLastNameTest(){
        assertEquals(jpaOfficerDao.findByFirstNameAndLastName("Krishna","Karki").size(),1);
        assertEquals(jpaOfficerDao.findByFirstNameAndLastName("Sammy","Colmen").size(),1);
    }

    @Test
    public void deleteByIdTest(){
        jpaOfficerDao.findAll().forEach(officer->jpaOfficerDao.deleteById(officer.getId()));
        assertEquals(jpaOfficerDao.findAll().size(),0);
    }

}
