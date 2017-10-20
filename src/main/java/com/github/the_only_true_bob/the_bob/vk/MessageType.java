package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public enum MessageType {
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
            final JsonArray attachmentsJson = objectJson.get("attachments").getAsJsonArray();
            final List<Attachment> attachments = StreamSupport.stream(attachmentsJson.spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .flatMap(attachment -> {
                        final String type = attachment.get("type").getAsString();
                        return AttachmentType.of(type).parse(attachment).orElseGet(Collections::emptyList).stream();
                    })
                    .collect(toList());
            return Optional.ofNullable(
                    getBuilder(this, objectJson)
                            .setText(objectJson.get("body").getAsString())
                            .setAttachments(attachments)
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

    MessageType(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    private static Message.Builder getBuilder(final MessageType type, final JsonObject objectJson) {
        return Message.builder()
                .setType(type)
                .setUserVkId(objectJson.get("user_id").getAsString());
    }

    public static MessageType of(String type) {
        if (type.equals(POLL.toString())) {
            return POLL;
        } else if (type.equals(MESSAGE.toString())) {
            return MESSAGE;
        }
        return UNASSIGNED;
    }

    public abstract Optional<Message> parse(JsonObject body);
}
