package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.github.the_only_true_bob.the_bob.utils.Utils.stringFromJson;
import static java.util.stream.Collectors.toList;

public enum MessageType {
    POLL("poll_vote_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            final Message.Builder builder = Message.builder().setType(this);
            return Optional.ofNullable(body.get("object"))
                           .map(JsonElement::getAsJsonObject)
                           .map(object -> builder.setUserVkId(stringFromJson(object, "user_id"))
                                                 .setPollId(stringFromJson(object, "poll_id"))
                                                 .setPollId(stringFromJson(object, "option_id"))
                                                 .build()
                           );
        }
    },

    MESSAGE("message_new") {
        @Override
        public Optional<Message> parse(JsonObject body) {
            final Message.Builder builder = Message.builder().setType(this);
            Optional.ofNullable(body.get("object"))
                    .map(JsonElement::getAsJsonObject)
                    .map(object -> {
                        builder.setUserVkId(stringFromJson(object, "user_id"))
                               .setText(stringFromJson(object, "body"));
                        return object.get("attachments");
                    })
                    .map(attachments -> StreamSupport.stream(attachments.getAsJsonArray().spliterator(), false)
                                                     .map(JsonElement::getAsJsonObject)
                                                     .flatMap(attachment ->
                                                             Optional.ofNullable(attachment.get("type"))
                                                                     .map(JsonElement::getAsString)
                                                                     .flatMap(type -> AttachmentType.of(type)
                                                                                                    .parse(attachment)
                                                                                                    .map(List::stream))
                                                                     .orElseGet(Stream::empty)
                                    )
                                    .collect(toList())
                    )
                    .ifPresent(builder::setAttachments);

            return Optional.ofNullable(builder.build());
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

    public static MessageType of(final String type) {
        return Arrays.stream(values())
                     .filter(messageType -> messageType.toString().equals(type))
                     .findFirst()
                     .orElse(UNASSIGNED);
    }

    public abstract Optional<Message> parse(JsonObject body);
}
