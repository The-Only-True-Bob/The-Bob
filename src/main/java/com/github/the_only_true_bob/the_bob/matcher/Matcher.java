package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.vk.User;

import java.util.List;
import java.util.Map;

public interface Matcher {
    Map<User, List<UserMatch>> match(EventEntity eventEntity);
}
