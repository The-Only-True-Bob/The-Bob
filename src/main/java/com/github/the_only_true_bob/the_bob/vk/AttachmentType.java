package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonObject;

import java.util.Optional;

public enum AttachmentType {
    AUDIO("audio") {
        @Override
        public Optional<Attachment> parse(JsonObject attachment) {
            // TODO: 20/10/17 implement
            return Optional.empty();
        }
    },

    UNASSIGNED("UNASSIGNED") {
        @Override
        public Optional<Attachment> parse(JsonObject attachment) {
            return Optional.empty();
        }
    };

    private final String string;

    AttachmentType(String string) {
        this.string = string;
    }

    public static AttachmentType of(String type) {
        if (type.equals(AUDIO.asString())) {
            return AUDIO;
        }
        return UNASSIGNED;
    }

    private String asString() {
        return string;
    }

    abstract public Optional<Attachment> parse(JsonObject attachment);
}
