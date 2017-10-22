package com.github.the_only_true_bob.the_bob.dao.repositories;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface EventUserRepository extends JpaRepository<EventUserEntity, Long> {

    List<EventUserEntity> findByUser(UserEntity userEntity);
    List<EventUserEntity> findByEvent(EventEntity eventEntity);
    Optional<EventUserEntity> findByEventAndUser(EventEntity eventEntity, UserEntity userEntity);
}
