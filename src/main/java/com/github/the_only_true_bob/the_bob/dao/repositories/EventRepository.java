package com.github.the_only_true_bob.the_bob.dao.repositories;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByName(String name);

    List<EventEntity> findByTypeAndDate(String type, String date);

    List<EventEntity> findByTypeAndName(String type, String name);
}
