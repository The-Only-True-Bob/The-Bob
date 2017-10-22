package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventUserRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("dataService")
public class DataServiceImpl implements DataService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventUserRepository eventUserRepository;

    @Override
    public UserEntity saveUser(final UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public Optional<UserEntity> findUserByVkId(final String vkId) {
        return Optional.ofNullable(userRepository.findByVkId(vkId));
    }

    @Override
    public List<UserEntity> findUsersByAcceptableSex(final String acceptableSex) {
        return userRepository.findByAcceptableSex(acceptableSex);
    }

    @Override
    public List<UserEntity> findUsersByAcceptableAgeDiff(final int acceptableAgeDiff) {
        return userRepository.findByAcceptableAgeDiff(acceptableAgeDiff);
    }

    @Override
    public List<UserEntity> findUsersByAcceptableSexAndAcceptableAgeDiff(final String acceptableSex, final int acceptableAgeDiff) {
        return userRepository.findByAcceptableSexAndAcceptableAgeDiff(acceptableSex, acceptableAgeDiff);
    }

    @Override
    public Optional<UserEntity> findUserById(final Long id) {
        return userRepository.findById(id);
    }


    @Override
    public void delete(final UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public EventEntity saveEvent(final EventEntity eventEntity) {
        return eventRepository.save(eventEntity);
    }

    @Override
    public Optional<EventEntity> findEventById(final Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<EventEntity> findEventsByTypeAndDate(final String type, final String date) {
        return eventRepository.findByTypeAndDate(type, date);
    }

    @Override
    public List<EventEntity> findEventsByTypeAndName(final String type, final String name) {
        return eventRepository.findByTypeAndName(type, name);
    }

    @Override
    public List<EventEntity> findEventsByName(final String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public List<EventEntity> findEventEntities() {
        return eventRepository.findAll();
    }

    @Override
    public void delete(final EventEntity eventEntity) {
        eventRepository.delete(eventEntity);
    }

    @Override
    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    @Override
    public EventUserEntity saveEventUser(final EventUserEntity eventUserEntity) {
        return eventUserRepository.save(eventUserEntity);
    }

    @Override
    public Optional<EventUserEntity> findEventUserById(final Long id) {
        return eventUserRepository.findById(id);
    }

    @Override
    public List<EventUserEntity> findEventsByUser(final UserEntity userEntity) {
        return eventUserRepository.findByUser(userEntity);
    }

    @Override
    public List<EventUserEntity> findUsersByEvent(final EventEntity eventEntity) {
        return eventUserRepository.findByEvent(eventEntity);
    }

    @Override
    public List<EventUserEntity> findAllEventUsers() {
        return eventUserRepository.findAll();
    }

    public Optional<EventUserEntity> findEventUserByEventAndUser(EventEntity eventEntity, UserEntity userEntity) {
        return eventUserRepository.findByEventAndUser(eventEntity, userEntity);
    }

    @Override
    public void delete(final EventUserEntity eventUserEntity) {
        eventUserRepository.delete(eventUserEntity);
    }

    @Override
    public void deleteAllEventUsers() {
        eventUserRepository.deleteAll();
    }
}
