package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

import java.util.List;
import java.util.Optional;

public interface DataService {

    //UserEntity
    UserEntity saveUser(UserEntity userEntity);
    Optional<UserEntity> findUserById(Long id);
    Optional<UserEntity> findUserByVkId(String vkId);
    List<UserEntity> findUsersByAcceptableSex(String acceptableSex);
    List<UserEntity> findUsersByAcceptableAgeDiff(int acceptableAgeDiff);
    List<UserEntity> findUsersByAcceptableSexAndAcceptableAgeDiff(String acceptableSex, int acceptableAgeDiff);
    void delete(UserEntity userEntity);
    void deleteAllUsers();

    //EventEntity
    EventEntity saveEvent(EventEntity eventEntity);
    Optional<EventEntity> findEventById(Long id);
    List<EventEntity> findEventsByTypeAndDate(String type, String date);
    List<EventEntity> findEventsByTypeAndName(String type, String name);
    List<EventEntity> findEventsByName(String name);
    List<EventEntity> findEventEntities();
    void delete(EventEntity eventEntity);
    void deleteAllEvents();

    //EventUserEntity
    EventUserEntity saveEventUser(EventUserEntity eventUserEntity);
    Optional<EventUserEntity> findEventUserById(Long id);
    List<EventUserEntity> findEventsByUser(UserEntity userEntity);
    List<EventUserEntity> findUsersByEvent(EventEntity eventEntity);
    void delete(EventUserEntity eventUserEntity);
    void deleteAllEventUsers();

    //find usersByAcceptableAgeAndAcceptableDiff and eventTypeAndName
}
