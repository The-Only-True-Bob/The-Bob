package com.github.the_only_true_bob.the_bob.dao.repositories;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventUserRepository extends JpaRepository<EventUserEntity, Long> {

    EventUserEntity findById(int id);
    List<EventUserEntity> findByUser(UserEntity userEntity);
    List<EventUserEntity> findByEvent(EventEntity eventEntity);
    EventUserEntity changeStatus(EventUserEntity eventUserEntity, String status);
    EventUserEntity changeStage(EventUserEntity eventUserEntity, String stage);

}