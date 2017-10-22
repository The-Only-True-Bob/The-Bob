package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.github.the_only_true_bob.the_bob.utils.Utils.arrayFromJson;
import static com.github.the_only_true_bob.the_bob.utils.Utils.stringFromJson;
import static java.util.stream.Collectors.toList;

public enum AttachmentType {
    AUDIO("audio") {
        @Override
        public List<Attachment> parse(JsonObject attachment) {
            return StreamSupport.stream(arrayFromJson(attachment, "audio").spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .map(object ->
                            Attachment.builder()
                                    .setType(this)
                                    .setAudioArtist(stringFromJson(object, "artist"))
                                    .build())
                    .collect(toList());
        }
    },

    WALL("wall") {
        @Override
        public List<Attachment> parse(JsonObject attachment) {
            return StreamSupport.stream(arrayFromJson(attachment, "wall").spliterator(), false)
                    .map(JsonElement::getAsJsonObject)
                    .flatMap(this::getAllSubattachments)
                    .collect(toList());
        }

        private Stream<Attachment> getAllSubattachments(final JsonObject object) {
            final Optional<Attachment> textAttachment =
                    getTextAttachment(stringFromJson(object, "text"));

            final Stream<Attachment> subStream =
                    StreamSupport.stream(arrayFromJson(object, "attachment").spliterator(), false)
                            .map(JsonElement::getAsJsonObject)
                            .flatMap(attachment ->
                                    AttachmentType.of(stringFromJson(attachment, "type"))
                                            .parse(attachment).stream());

            return textAttachment
                    .map(attach -> Stream.concat(Stream.of(attach), subStream))
                    .orElse(subStream);
        }

        private Optional<Attachment> getTextAttachment(final String text) {
            return Optional.ofNullable(text)
                    .map(text1 ->
                            Attachment.builder()
                                    .setType(this)
                                    .setText(text1)
                                    .build());
        }

    },

    UNASSIGNED("UNASSIGNED") {
        @Override
        public List<Attachment> parse(JsonObject attachment) {
            return Collections.emptyList();
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

    abstract public List<Attachment> parse(JsonObject attachment);
}
