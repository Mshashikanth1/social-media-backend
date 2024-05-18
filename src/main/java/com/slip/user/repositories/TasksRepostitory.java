package com.slip.user.repositories;

import com.slip.user.Models.Tasks;
import com.slip.user.Models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepostitory extends Neo4jRepository<Tasks,Long> {


    @Query("match (n:Tasks) where n.userRef =~ $userRef return n")
    List<Tasks> getUserByUserId(@Param("userRef") String userRef);
    @Query("match (n:Tasks) where n.userRef =~ $userRef return count(n)")
    Integer getUserTaskCount(@Param("userRef") String userRef);

    @Query("MATCH (user:User {email: $email}) " +
            "MATCH (task:Tasks{ref:  $ref}) " +
            "CREATE (user)-[hasPlanned:HAS_PLANNED{createdAt:date()}]->(task)  " +
            "RETURN  type(hasPlanned)")
    String createPlannedRelation(@Param("ref") String ref, @Param("email") String email);


    @Query("MATCH (user:User {email: $email}) " +
            "MATCH (task:Tasks{ref:  $ref}) " +
            "MATCH (user)-[hasPlanned:HAS_PLANNED{createdAt:date()}]->(task)  " +
            "DELETE  hasPlanned ")
    String DeletePlannedRelation(@Param("ref") String ref, @Param("email") String email);
}
