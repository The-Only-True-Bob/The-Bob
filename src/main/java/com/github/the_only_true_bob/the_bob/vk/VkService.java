package com.github.the_only_true_bob.the_bob.vk;

import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;

public interface VkService {
    VkService sendMessage(Message message);

    User getUser(String userVkId);
    default User getUser(UserEntity userEntity) {
        return getUser(userEntity.getVkId());
    }
}
