package com.github.the_only_true_bob.the_bob.finder;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.vk.Attachment;
import com.github.the_only_true_bob.the_bob.vk.AttachmentType;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public class FinderImpl implements Finder {

    @Autowired
    private DataService dataService;

    @Override
    public Stream<EventEntity> suggestEventsFor(final Message message) {
        final Stream<String> attachmentBasedSuggestion = message.attachments()
                .stream()
                .filter(this::isAudio)
                .map(audio -> audio.audioArtist().get());

        final Stream<String> textBasedSuggestion = message.text()
                .map(Stream::of)
                .orElseGet(Stream::empty);

        return Stream
                .concat(attachmentBasedSuggestion, textBasedSuggestion)
                .flatMap(this::suggest);
    }

    private boolean isAudio(Attachment attachment) {
        return AttachmentType.AUDIO.equals(attachment.type())
                && attachment.audioArtist().isPresent();
    }

    private Stream<EventEntity> suggest(final String needle) {
        final String textMessage = needle.toLowerCase();
        return dataService
                .findEventEntities()
                .stream()
                .filter(event -> textMessage.contains(event.getName().toLowerCase()));
    }
}
