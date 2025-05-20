package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Message m WHERE m.id = ?1")
    Integer deleteAllById(Integer messageId);
    
    @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
    List<Message> findAllByPosted(Integer id);


}
