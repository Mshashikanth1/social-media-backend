package com.slip.user.repositories;

import com.slip.user.Models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User,Long>  {


 @Query("match (n:User) where n.handle =~ $handle return n")
 User getUserByHandle(@Param("handle") String handle);

 @Query("match (n:User) where n.email =~ $email return n")
 User getUserByEmail(@Param("email") String email);

 @Query("MATCH (a:User {email : $targetUserEmail}) " +
         "MATCH (b:User {email : $loggedInUserEmail})  " +
         "CREATE (a)<-[r:Follows {createdAt:date()} ]-(b)  " +
         " RETURN type(r)" )
 String followUserWitEmail(@Param("loggedInUserEmail") String loggedInUserEmail, @Param("targetUserEmail") String targetUserEmail);

 @Query("MATCH  (a:User {email : $targetUserEmail})<-[r:Follows]-(b:User {email : $loggedInUserEmail}) DELETE r")
 String unFollowUserWitEmail(@Param("loggedInUserEmail") String loggedInUserEmail, @Param("targetUserEmail") String targetUserEmail);

 @Query("MATCH  (a:User {email : $targetUserEmail })-[r:Follows]->(b:User) return b")
 List<User> getFollowings( @Param("targetUserEmail") String targetUserEmail);

 @Query("MATCH  (a:User {email : $targetUserEmail })<-[r:Follows]-(b:User) return b")
 List<User> getFollowers( @Param("targetUserEmail") String targetUserEmail);

}
