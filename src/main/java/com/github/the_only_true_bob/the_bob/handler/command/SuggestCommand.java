package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.finder.Finder;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SuggestCommand implements BobCommand {

    @Autowired
    private Finder finder;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private DataService dataService;

    @Override
    public Message handleMessage(final Message message) {
        final Stream<EventEntity> eventEntities = finder.suggestEventsFor(message);
        String userId = message.userId().get();
        final Message.Builder builder = Message.builder().setUserVkId(userId);
        final int[] num = {1};
        final List<String> suggestions = eventEntities
                .map(eventEntity -> {
                    final UserEntity userEntity = dataService.findUserByVkId(userId).get();
                    final EventUserEntity eventUserEntity = dataService.findEventUserByEventAndUser(eventEntity, userEntity)
                            .orElseGet(EventUserEntity::new);
                    eventUserEntity.setNumber(num[0]);
                    eventUserEntity.setStatus(CommandStatus.LISTED);
                    userEntity.setStatus(CommandStatus.LISTED);
                    dataService.saveEventUser(eventUserEntity);
                    dataService.saveUser(userEntity);
                    return messageProvider.get("event.proposal.event",
                            num[0]++,
                            eventEntity.getType(),
                            eventEntity.getName(),
                            eventEntity.getDate(),
                            eventEntity.getPlaceName(),
                            eventEntity.getPlaceAddress(),
                            eventEntity.getAfishaUrl(),
                            eventEntity.getAfishaImgUrl());
                })
                .collect(Collectors.toList());
        final String response = suggestions.isEmpty()
                ? messageProvider.get("event.not_found")
                : messageProvider.get("event.proposal.intro", suggestions.size())
                                 .concat(String.join("", suggestions))
                                 .concat(messageProvider.get("event.choose"));
        return builder.setText(response).build();
    }
}
