package com.galvanize;

import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import com.galvanize.repository.JdbcOfficerDao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional // Use this to pub the db back way you started with after each test
public class JdbcOfficerDaoTest {

    @Autowired
    JdbcOfficerDao jdbcOfficerDao;

    @Test
    public void countOfficersTest(){
        long totalOfficer = jdbcOfficerDao.count();
        assertEquals(totalOfficer,5);
    }

    @Test
    public void findAllOfficersTest(){
        // rlw - Although this works, you should take the result of find all, and perform your tests on that.
//        assertNotNull(jdbcOfficerDao.findAll());
//        assertEquals(jdbcOfficerDao.findAll().size(),5);

        // rlw - like this...
        List<Officer> officers = jdbcOfficerDao.findAll();
        assertEquals(5, officers.size());
    }

    @Test
    public void officerExistsByIdTest(){
        // rlw - findById should return an Optional<Officer>, then check isPresent() method
        assertNull(jdbcOfficerDao.findById(100L));
        assertNotNull(jdbcOfficerDao.findById(5L));
    }

    @Test
    public void createNewOfficerTest(){
        // rlw - this is good
        Officer officer = jdbcOfficerDao.save(new Officer(Rank.CAPTAIN,"Michael", "Johnson"));
        assertEquals(officer.getFirstName(),"Michael");
        assertEquals(officer.getLastName(),"Johnson");
        assertEquals(officer.getRank(),Rank.CAPTAIN);
    }

    @Test
    public void deleteOfficerTest(){
//        assertEquals(jdbcOfficerDao.delete(5L), true);
//        assertEquals(jdbcOfficerDao.delete(100L), false);
        // rlw - Better
        assertTrue(jdbcOfficerDao.delete(5L));
        assertFalse(jdbcOfficerDao.delete(100L));
    }


}
