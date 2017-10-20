package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventUserRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DataServiceImpl implements DataService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventUserRepository eventUserRepository;

    @Override
    public UserEntity saveUser(final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findUserByVkId(final String vkId) {
        return Optional.ofNullable(userRepository.findByVkId(vkId));
    }

    @Override
    public Optional<UserEntity> findUserById(final int id) {
        return Optional.ofNullable(userRepository.findById(id));
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
    public Optional<EventEntity> findEventById(final int id) {
        return Optional.ofNullable(eventRepository.findById(id));
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
    public Optional<EventUserEntity> findEventUserById(final int id) {
        return Optional.ofNullable(eventUserRepository.findById(id));
    }

    @Override
    public Optional<List<EventUserEntity>> findEventsByUser(final UserEntity userEntity) {
        return Optional.ofNullable(eventUserRepository.findByUser(userEntity));
    }

    @Override
    public Optional<List<EventUserEntity>> findUsersByEvent(final EventEntity eventEntity) {
        return Optional.ofNullable(eventUserRepository.findByEvent(eventEntity));
    }

    @Override
    public EventUserEntity changeEventUserStatus(final EventUserEntity eventUserEntity, final String status) {
        return eventUserRepository.changeStatus(eventUserEntity, status);
    }

    @Override
    public EventUserEntity changeEventUserStage(final EventUserEntity eventUserEntity, final String stage) {
        return eventUserRepository.changeStage(eventUserEntity, stage);
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
