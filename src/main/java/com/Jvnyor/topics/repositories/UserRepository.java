package com.Jvnyor.topics.repositories;

import com.Jvnyor.topics.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT EXISTS(SELECT 1 FROM tb_user WHERE topic_id = :topicId AND id = :associateId)", nativeQuery = true)
    boolean existsByTopicAndAssociateId(@Param("topicId") Long topicId, @Param("associateId") Long associateId);

    @Query(value = "SELECT COUNT(*) FROM tb_user WHERE topic_id = :topicId AND vote = :vote", nativeQuery = true)
    long countByTopicAndVote(@Param("topicId") Long topicId, @Param("vote") String vote);
}