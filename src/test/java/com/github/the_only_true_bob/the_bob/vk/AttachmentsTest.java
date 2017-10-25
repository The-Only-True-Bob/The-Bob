package com.github.the_only_true_bob.the_bob.vk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

public class AttachmentsTest {

    @Test
    public void audioAttachment() throws Exception {
        final String s =
                "{\"type\": \"message_new\", " +
                        "\"object\": " +
                        "{\"user_id\": 1, " +
                        "attachments: [{\"type\": \"audio\", \"audio\": { \"artist\": \"Kasabian\" }}]" +
                        "}, " +
                        "\"group_id\": 1}";

        final JsonParser jsonParser = new JsonParser();
        final JsonObject callbackMessage = jsonParser.parse(s).getAsJsonObject();

        final Set<String> actual = MessageType.of("message_new")
                .parse(callbackMessage)
                .attachments().stream()
                .map(Attachment::audioArtist)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSet());

        final Set<String> expected = Stream.of("Kasabian")
                .collect(toSet());

        assertEquals(expected, actual);
    }

    @Test
    public void wallAttachment() throws Exception {
        final String s = "{\"type\": \"message_new\", \"object\": {\"user_id\": 1, attachments: [{\"type\": \"wall\", \"wall\": { \"text\": \"Wall post Text\", attachments: [{\"type\": \"audio\", \"audio\": { \"artist\": \"Kasabian\" }},{\"type\": \"wall\", \"wall\": { \"text\": \"Wall inner post Text\" }}]}}]}, \"group_id\": 1}";

        final JsonParser jsonParser = new JsonParser();
        final JsonObject callbackMessage = jsonParser.parse(s).getAsJsonObject();

        final Stream<String> audios = MessageType.of("message_new")
                .parse(callbackMessage)
                .attachments().stream()
                .map(Attachment::audioArtist)
                .filter(Optional::isPresent)
                .map(Optional::get);

        final Stream<String> texts = MessageType.of("message_new")
                .parse(callbackMessage)
                .attachments().stream()
                .map(Attachment::text)
                .filter(Optional::isPresent)
                .map(Optional::get);

        final Set<String> actual = Stream.concat(audios, texts)
                .collect(toSet());

        final Set<String> expected = Stream.of("Wall post Text", "Kasabian", "Wall inner post Text")
                .collect(toSet());

        assertEquals(expected, actual);
    }
}
