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
        // rlw - this is how test data should be set up!  Nice!
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
        // rlw - remove println's before checking in code
        System.out.println(officer.getId());
        assertEquals(jpaOfficerDao.count(),6L);
    }

    @Test
    public void findAllOfficersTest(){
        // rlw - Should be two tests
        assertEquals(jpaOfficerDao.count(),5L);
        assertEquals(jpaOfficerDao.findAll().size(),5L);
    }

    @Test
    public void findByIdTest(){
        // rlw - this should be in three tests.  Also, findById should check a few more details too.
        assertNotNull(jpaOfficerDao.findById(1L));
        assertNotNull(jpaOfficerDao.findById(2L));
        assertEquals(Optional.empty(),jpaOfficerDao.findById(100L));
    }

    @Test
    public void findByRankTest(){
        // rlw - Nice addition!
        assertEquals(jpaOfficerDao.findByRank(Rank.COMMANDER).size(),2);
        assertEquals(jpaOfficerDao.findByRank(Rank.CAPTAIN).size(),1);
        assertEquals(jpaOfficerDao.findByRank(Rank.ENSIGN).size(),1);
    }

    @Test
    public void findByFirstNameAndLastNameTest(){
        // rlw - nice addition
        assertEquals(jpaOfficerDao.findByFirstNameAndLastName("Krishna","Karki").size(),1);
        assertEquals(jpaOfficerDao.findByFirstNameAndLastName("Sammy","Colmen").size(),1);
    }

    @Test
    public void deleteByIdTest(){
        // rlw - probably overkill here, but good job
        jpaOfficerDao.findAll().forEach(officer->jpaOfficerDao.deleteById(officer.getId()));
        assertEquals(jpaOfficerDao.findAll().size(),0);
    }

}
