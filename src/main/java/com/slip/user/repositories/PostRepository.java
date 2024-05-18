package com.slip.user.repositories;

import com.mongodb.internal.connection.QueryResult;
import com.slip.user.Models.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends Neo4jRepository<Post,Long> {


    @Query("Match (user:User {email: $email}) " +
            "MATCH (post:Post {postRef: $postRef}) " +
            "CREATE (user)-[hasPosted:HAS_POSTED{createdAt:date(),ownerEmail:user.email}]->(post) return type(hasPosted) ")
    String createRelation(@Param("email") String email ,@Param("postRef") String  postRef);

    @Query("MATCH (user:User {email:  $email})-[hasPosted:HAS_POSTED]->(post:Post {postRef:  $postRef}) return hasPosted is not null")
    Boolean isRelationExist(@Param("email") String email  ,@Param("postRef") String  postRef);

    @Query("Match (user:User {email: $email}) " +
            "MATCH (post:Post {postRef: $postRef}) " +
            "CREATE (user)-[hasLiked:HAS_LIKED{createdAt:date()}]->(post)" +
            "SET post.likes = post.likes+1" +
            " return type(hasLiked) ")
    String createLikeRelation(@Param("postRef")String postRef, @Param("email") String userEmail);

    @Query("MATCH (user:User {email: $email}) " +
            "MATCH (post:Post {postRef:  $postRef}) " +
            "Match (user)-[hasLiked:HAS_LIKED]->(post)  " +
            "Delete  hasLiked")
    String DeleteLikeRelation(@Param("postRef")String postRef, @Param("email") String userEmail);

    @Query("Match (user:User {email: $email}) " +
            "MATCH (post:Post {postRef: $postRef}) " +
            "CREATE (user)-[hasShared:HAS_SHARED{createdAt:date()}]->(post) " +
            "SET post.shares = post.shares+1 " +
            "return type(hasShared) ")
    String createShareRelation(@Param("postRef")String postRef, @Param("email") String userEmail);

    @Query("Match (user:User {email: $email}) " +
            "MATCH (post:Post {postRef: $postRef}) " +
            "CREATE (user)-[ hasCommented: HAS_COMMENTED { createdAt:date() , comment:$commentText }]->(post) " +
            "SET post.comments =post.comments+ [$commentText] " +
            " return type(hasCommented) ")
    String createCommentRelation(@Param("postRef")String postRef, @Param("email") String userEmail, @Param("commentText")String commentText);


    @Query("Match (user:User {email: $email}) " +
            "MATCH (post:Post {postRef: $postRef}) " +
            "MATCH (user)-[hasCommented:HAS_COMMENTED]->(post)" +
            " DELETE hasCommented ")
    String DeleteCommentRelation(@Param("postRef")String postRef, @Param("email") String userEmail);

    @Query("MATCH (post:Post)<-[r:HAS_POSTED]-(user:User)\n" +
            "RETURN \n" +
            "  apoc.map.fromLists(\n" +
            "    ['postRef','imageUrl','writeUp', 'AuthorEmail', 'AuthorProfileImgUrl', 'AuthorName', 'AuthorHandle'],\n" +
            "    [post.postRef,post.imageUrl,post.writeUp, user.email, user.profileImgUrl, user.name, user.handle]\n" +
            "  ) AS postDetails SKIP $offset LIMIT  $limit \n")
    List<Map<String, Object>> getPosts(@Param("offset") int offset , @Param("limit") int limit);
}
