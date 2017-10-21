package com.github.the_only_true_bob.the_bob.vk.polls;

import com.github.the_only_true_bob.the_bob.vk.Message;

import java.util.Map;

public interface Poll {
    String id();
    Map<String, PollOption> options();
    void handle(Message message);
}
