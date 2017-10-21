package com.github.the_only_true_bob.the_bob.dao.repositories;

import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByVkId(String vkId);

    List<UserEntity> findByAcceptableSex(String acceptableSex);

    List<UserEntity> findByAcceptableAgeDiff(int acceptableAgeDiff);

    List<UserEntity> findByAcceptableSexAndAcceptableAgeDiff(String acceptableSex, int acceptableAgeDiff);
}
