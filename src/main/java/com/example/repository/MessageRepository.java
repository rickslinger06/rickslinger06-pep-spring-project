package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>  {

    @Query("SELECT m FROM Message m WHERE m.message_id = :id")
    Message findMessageById(int id);

    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedBy")
    List<Message> findAllMessagesByPostedBy(@Param("postedBy") Integer postedBy);
}
