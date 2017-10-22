package com.github.the_only_true_bob.the_bob.finder;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.vk.Attachment;
import com.github.the_only_true_bob.the_bob.vk.AttachmentType;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Stream;

public class FinderImpl implements Finder {

    @Autowired
    private DataService dataService;

    @Override
    public Stream<EventEntity> suggestEventsFor(final Message message) {
        final Stream<String> attachmentAudioBasedSuggestion = message.attachments().stream()
                .filter(this::isAudio)
                .map(Attachment::audioArtist)
                .filter(Optional::isPresent)
                .map(Optional::get);

        final Stream<String> attachmentTextBasedSuggestion = message.attachments().stream()
                .filter(attachment -> !isAudio(attachment))
                .map(Attachment::text)
                .filter(Optional::isPresent)
                .map(Optional::get);

        final Stream<String> messageTextBasedSuggestion = message.text()
                .map(Stream::of)
                .orElseGet(Stream::empty);

        return
                Stream.concat(attachmentAudioBasedSuggestion,
                        Stream.concat(
                                attachmentTextBasedSuggestion,
                                messageTextBasedSuggestion))
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
