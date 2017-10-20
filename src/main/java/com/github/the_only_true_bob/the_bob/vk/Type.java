package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonObject;

import java.util.Optional;

public enum Type {
    POLL("poll_vote_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            final JsonObject objectJson = body.get("object").getAsJsonObject();
            return Optional.ofNullable(
                    getBuilder(this, objectJson)
                            .setPollId(objectJson.get("poll_id").getAsString())
                            .setOptionId(objectJson.get("option_id").getAsString())
                            .build());
        }
    },

    MESSAGE("message_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            final JsonObject objectJson = body.get("object").getAsJsonObject();
            return Optional.ofNullable(
                    getBuilder(this, objectJson)
                            .setText(objectJson.get("body").getAsString())
                            .build());
        }
    },

    UNASSIGNED("UNASSIGNED") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            return Optional.empty();
        }
    };

    private final String string;

    Type(final String string) {
        this.string = string;
    }

    public String asString() {
        return string;
    }

    public static Type of(String type) {
        if (type.equals(POLL.asString())) {
            return POLL;
        } else if (type.equals(MESSAGE.asString())) {
            return MESSAGE;
        }
        return UNASSIGNED;
    }

    private static Message.Builder getBuilder(final Type type, final JsonObject objectJson) {
        return Message.builder()
                .setType(type)
                .setUserVkId(objectJson.get("user_id").getAsString());
    }

    public abstract Optional<Message> parse(JsonObject body);
}
