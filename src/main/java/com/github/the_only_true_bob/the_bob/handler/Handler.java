package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.vk.Message;

import java.util.function.Consumer;

public interface Handler extends Consumer<Message> {
    void accept(final Message message);

    static Handler bob() {
        return new BobHandler() {};
    }
}
