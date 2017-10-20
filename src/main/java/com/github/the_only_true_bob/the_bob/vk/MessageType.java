package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public enum MessageType {
    POLL("poll_vote_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            final JsonObject objectJson = body.get("object").getAsJsonObject();
            return Optional.ofNullable(
                    Message.builder()
                            .setType(this)
                            .setUserVkId(objectJson.get("user_id").getAsString())
                            .setPollId(objectJson.get("poll_id").getAsString())
                            .setOptionId(objectJson.get("option_id").getAsString())
                            .build()
            );
        }
    },

    MESSAGE("message_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            Message.Builder builder = Message.builder().setType(this);
            Optional.ofNullable(body.get("object"))
                    .map(JsonElement::getAsJsonObject)
                    .map(object -> {
                        final String user_id = Optional.ofNullable(object.get("user_id"))
                                                       .map(JsonElement::getAsString)
                                                       .orElse("");
                        final String text = Optional.ofNullable(object.get("body"))
                                                    .map(JsonElement::getAsString)
                                                    .orElse("");
                        builder.setUserVkId(user_id)
                               .setText(text);
                        return object.get("attachments");
                    })
                    .map(attachments -> StreamSupport.stream(attachments.getAsJsonArray().spliterator(), false)
                                    .map(JsonElement::getAsJsonObject)
                                    .flatMap(attachment -> Optional.ofNullable(attachment.get("type"))
                                            .map(JsonElement::getAsString)
                                            .flatMap(type -> AttachmentType.of(type).parse(attachment).map(List::stream))
                                            .orElseGet(Stream::empty)
                                    )
                                    .collect(toList())
                    )
                    .ifPresent(builder::setAttachments);

            return Optional.ofNullable(Message.builder().build());
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
