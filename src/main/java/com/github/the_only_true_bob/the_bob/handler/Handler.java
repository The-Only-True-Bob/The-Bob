package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.vk.Message;

import java.util.function.Consumer;

public interface Handler extends Consumer<Message> {
    default void accept(final Message message) {
        // TODO: 20.10.17 Accept message and process
    }
}
