package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonObject;

public interface Message {
    // TODO: 20.10.17 Figure out how it should work and what it must consist

    Type type();

    String text();

    // TODO: 20.10.17 It must somewhat
    void attachments();

    String userId();

    static Message from(final JsonObject body) {
        return null;
    }
}
