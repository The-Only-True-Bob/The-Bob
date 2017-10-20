package com.github.the_only_true_bob.the_bob.dao;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

import java.util.List;
import java.util.Optional;

public class DataServiceImpl implements DataService{
    @Override
    public UserEntity saveUser(final UserEntity userEntity) {
        return null;
    }

    @Override
    public Optional<UserEntity> findUserByVkId(final int vkId) {
        return null;
    }

    @Override
    public Optional<UserEntity> findUserById(final int id) {
        return null;
    }

    @Override
    public void delete(final UserEntity userEntity) {

    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public EventEntity saveEvent(final EventEntity eventEntity) {
        return null;
    }

    @Override
    public Optional<EventEntity> findEventById(final int id) {
        return null;
    }

    @Override
    public void delete(final EventEntity eventEntity) {

    }

    @Override
    public void deleteAllEvents() {

    }

    @Override
    public EventUserEntity saveEventUser(final EventUserEntity eventUserEntity) {
        return null;
    }

    @Override
    public Optional<EventUserEntity> findEventUserById(final int id) {
        return null;
    }

    @Override
    public Optional<List<EventUserEntity>> findEventsByUser(final UserEntity userEntity) {
        return null;
    }

    @Override
    public Optional<List<EventUserEntity>> findUsersByEvent(final EventEntity eventEntity) {
        return null;
    }

    @Override
    public EventUserEntity changeEventUserStatus(final EventUserEntity eventUserEntity, final String status) {
        return null;
    }

    @Override
    public EventUserEntity changeEventUserStage(final EventUserEntity eventUserEntity, final String stage) {
        return null;
    }

    @Override
    public EventUserEntity delete(final EventUserEntity eventUserEntity) {
        return null;
    }

    @Override
    public void deleteAllEventUsers() {

    }
}
