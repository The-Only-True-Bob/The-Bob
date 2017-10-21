package com.github.the_only_true_bob.the_bob.finder;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.vk.Message;

import java.util.stream.Stream;

public interface Finder {
    Stream<EventEntity> suggestEventsFor(Message message);
}