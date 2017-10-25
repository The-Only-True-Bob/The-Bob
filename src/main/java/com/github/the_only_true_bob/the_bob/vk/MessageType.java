package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.the_only_true_bob.the_bob.utils.Utils.stringFromJson;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public enum MessageType {
    POLL("poll_vote_new") {
        @Override
        public Message parse(JsonObject body) {
            final Message.Builder builder = Message.builder().setType(this);
            return Optional.ofNullable(body.get("object"))
                    .map(JsonElement::getAsJsonObject)
                    .map(object -> builder.setUserVkId(stringFromJson(object, "user_id"))
                            .setPollId(stringFromJson(object, "poll_id"))
                            .setOptionId(stringFromJson(object, "option_id"))
                            .build())
                    .orElseGet(Message::empty);
        }
    },

    ALLOW_MESSAGE("message_allow") {
        @Override
        public Message parse(final JsonObject body) {
            final Message.Builder builder = Message.builder().setType(this);
            return Optional.ofNullable(body.get("object"))
                    .map(JsonElement::getAsJsonObject)
                    .map(object -> builder.setUserVkId(stringFromJson(object, "user_id"))
                            .build())
                    .orElseGet(Message::empty);
        }
    },

    MESSAGE("message_new") {
        @Override
        public Message parse(JsonObject body) {
            final Message.Builder builder = Message.builder().setType(this);
            return Optional.ofNullable(body.get("object"))
                    .map(JsonElement::getAsJsonObject)
                    .map(object -> {
                        builder.setUserVkId(stringFromJson(object, "user_id"))
                                .setText(stringFromJson(object, "body"));
                        Optional.ofNullable(object.get("attachments"))
                                .ifPresent(attachments ->
                                        builder.setAttachments(
                                                stream(attachments.getAsJsonArray().spliterator(), false)
                                                        .map(JsonElement::getAsJsonObject)
                                                        .flatMap(attachment ->
                                                                Optional.ofNullable(attachment.get("type"))
                                                                        .map(JsonElement::getAsString)
                                                                        .map(type ->
                                                                                AttachmentType.of(type)
                                                                                        .parse(attachment)
                                                                                        .stream())
                                                                        .orElseGet(Stream::empty))
                                                        .collect(toList())));
                        return builder;
                    })
                    .map(Message.Builder::build)
                    .orElseGet(Message::empty);
        }
    },

    UNASSIGNED("UNASSIGNED") {
        @Override
        public Message parse(JsonObject body) {
            return Message.empty();
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

    public static MessageType of(final String type) {
        return Arrays.stream(values())
                .filter(messageType -> messageType.toString().equals(type))
                .findFirst()
                .orElse(UNASSIGNED);
    }

    public abstract Message parse(JsonObject body);
}
