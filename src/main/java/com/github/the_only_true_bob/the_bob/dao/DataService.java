package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DataService {

    //UserEntity
    UserEntity saveUser(UserEntity userEntity);
    Optional<UserEntity> findUserByVkId(String vkId);
    Optional<UserEntity> findUserById(Long id);
    void delete(UserEntity userEntity);
    void deleteAllUsers();

    //EventEntity
    EventEntity saveEvent(EventEntity eventEntity);
    Optional<EventEntity> findEventById(Long id);

    void delete(EventEntity eventEntity);
    void deleteAllEvents();

    //EventUserEntity
    EventUserEntity saveEventUser(EventUserEntity eventUserEntity);
    Optional<EventUserEntity> findEventUserById(Long id);
    List<EventUserEntity> findEventsByUser(UserEntity userEntity);
    List<EventUserEntity> findUsersByEvent(EventEntity eventEntity);
    void delete(EventUserEntity eventUserEntity);
    void deleteAllEventUsers();

}
