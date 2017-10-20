package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

import java.util.List;
import java.util.Optional;

public interface DataService {

    //UserEntity
    UserEntity saveUser(UserEntity userEntity);
    Optional<UserEntity> findUserByVkId(int vkId);
    Optional<UserEntity> findUserById(int id);
    void delete(UserEntity userEntity);
    void deleteAllUsers();

    //EventEntity
    EventEntity saveEvent(EventEntity eventEntity);
    Optional<EventEntity> findEventById(int id);
    void delete(EventEntity eventEntity);
    void deleteAllEvents();

    //EventUserEntity
    EventUserEntity saveEventUser(EventUserEntity eventUserEntity);
    Optional<EventUserEntity> findEventUserById(int id);
    Optional<List<EventUserEntity>> findEventsByUser(UserEntity userEntity);
    Optional<List<EventUserEntity>> findUsersByEvent(EventEntity eventEntity);
    EventUserEntity changeEventUserStatus(EventUserEntity eventUserEntity, String status);
    EventUserEntity changeEventUserStage(EventUserEntity eventUserEntity, String stage);
    EventUserEntity delete(EventUserEntity eventUserEntity);
    void deleteAllEventUsers();

}
