package com.github.the_only_true_bob.the_bob.vk;

import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

import java.util.List;

public interface VkService {
    VkService sendMessage(Message message);

    User getUser(String userVkId);
    List<User> getUsers(List<String> userIds);
}
