package com.github.the_only_true_bob.the_bob.dao.repositories;

import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
