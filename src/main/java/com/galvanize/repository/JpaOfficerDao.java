package com.galvanize.repository;

import com.galvanize.entities.Officer;
import com.galvanize.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOfficerDao extends JpaRepository<Officer, Long> {

    List<Officer> findByRank(Rank rank);

    List<Officer> findByFirstNameAndLastName(String firstName, String lastName);

//    @Query("UPDATE officers O SET O.officer_rank = :officer_rank WHERE O.id = :id")
//    Officer updateOfficerRankById(@Param("id") Long id, @Param("officer_rank") Rank rank);
}
