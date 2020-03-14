package com.galvanize.repository;


import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOfficerDao {

    JdbcTemplate jdbcTemplate;

    SimpleJdbcInsert insertOfficer;

    public JdbcOfficerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertOfficer = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    public Long count(){
        Long totalCount;
        try {
            String query = "SELECT COUNT(*) FROM officers";
            totalCount = jdbcTemplate.queryForObject(query, Long.class);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
        return totalCount;
    }

    public List<Officer> findAll(){
        String query = "SELECT * FROM officers;";
        return jdbcTemplate.query(
                query,
                (rs, rowNum) ->
                        new Officer(
                                rs.getLong("id"),
                                Rank.valueOf(rs.getString("officer_rank")),
                                rs.getString("first_name"),
                                rs.getString("last_name")
                        )
        );
    }

    public Officer findById(Long id){
        String query = "SELECT * FROM officers WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject( query, (rs, rowNum) -> new Officer(rs.getLong("id"),
                    Rank.valueOf(rs.getString("officer_rank")),
                    rs.getString("first_name"),
                    rs.getString("last_name")), id);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public Officer save(Officer officer) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("officer_rank", officer.getRank());
        parameters.put("first_name", officer.getFirstName());
        parameters.put("last_name", officer.getLastName());
        long newId = insertOfficer.executeAndReturnKey(parameters).longValue();
        officer.setId(newId);
        return officer;
    }

    public boolean delete(Long id){
        String query = "DELETE FROM officers WHERE id = ?";
        Object[] args = new Object[] {id};
        return jdbcTemplate.update(query, args) == 1;
    }

}
