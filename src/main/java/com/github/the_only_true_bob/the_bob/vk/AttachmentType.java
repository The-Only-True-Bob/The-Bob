package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public enum AttachmentType {
    AUDIO("audio") {
        @Override
        public Optional<List<Attachment>> parse(JsonObject attachment) {
            return Optional.ofNullable(
                    StreamSupport.stream(attachment.get("audio").getAsJsonArray().spliterator(), false)
                            .map(JsonElement::getAsJsonObject)
                            .map(object ->
                                    Attachment.builder()
                                            .setType(this)
                                            .setAudioArtist(object.get("artist").getAsString())
                                            .build())
                            .collect(toList()));
        }
    },

    WALL("wall") {
        @Override
        public Optional<List<Attachment>> parse(JsonObject attachment) {
            // TODO: 22.10.2017 Implement wall processing
            return Optional.empty();
        }
    },

    UNASSIGNED("UNASSIGNED") {
        @Override
        public Optional<List<Attachment>> parse(JsonObject attachment) {
            return Optional.empty();
        }
    };

    private final String string;

    AttachmentType(String string) {
        this.string = string;
    }

    public static AttachmentType of(final String type) {
        return Arrays.stream(values())
                     .filter(attachmentType -> attachmentType.toString().equals(type))
                     .findFirst()
                     .orElse(UNASSIGNED);
    }

    @Override
    public String toString() {
        return string;
    }

    abstract public Optional<List<Attachment>> parse(JsonObject attachment);
}
