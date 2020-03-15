package com.galvanize;

import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import com.galvanize.repository.JdbcOfficerDao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
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
        assertNotNull(jdbcOfficerDao.findAll());
        assertEquals(jdbcOfficerDao.findAll().size(),5);
    }

    @Test
    public void officerExistsByIdTest(){
        assertNull(jdbcOfficerDao.findById(100L));
        assertNotNull(jdbcOfficerDao.findById(5L));
    }

    @Test
    public void createNewOfficerTest(){
        Officer officer = jdbcOfficerDao.save(new Officer(Rank.CAPTAIN,"Michael", "Johnson"));
        assertEquals(officer.getFirstName(),"Michael");
        assertEquals(officer.getLastName(),"Johnson");
        assertEquals(officer.getRank(),Rank.CAPTAIN);
    }

    @Test
    public void deleteOfficerTest(){
        assertEquals(jdbcOfficerDao.delete(5L), true);
        assertEquals(jdbcOfficerDao.delete(100L), false);
    }


}
