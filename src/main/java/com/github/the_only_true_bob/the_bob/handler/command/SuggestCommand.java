package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.finder.Finder;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SuggestCommand implements BobCommand {

    @Autowired
    private Finder finder;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private DataService dataService;

    @Override
    public Message handleMessage(final Message message) {
        final List<EventEntity> eventEntities = finder.suggestEventsFor(message);
        if (eventEntities.isEmpty()) {
            return Message.builder()
                    .setUserVkId(message.userId().get())
                    .setText(messageProvider.get("event.not_found"))
                    .build();
        } else {
            final int[] num = {1};
            eventEntities.stream()
                    .map(eventEntity -> {
                        final EventUserEntity eventUserEntity = new EventUserEntity();
                        eventUserEntity.setEvent(eventEntity);
                        eventUserEntity.setUser(
                                dataService
                                        .findUserByVkId(message.userId().get()).get());
                        eventUserEntity.setNumber(num[0]);
                        eventUserEntity.setStatus("listed");
                        num[0]++;
                        return eventUserEntity;
                    })
                    .forEach(dataService::saveEventUser);

            final StringBuilder sb =
                    new StringBuilder()
                            .append(messageProvider.get("event.proposal.intro", eventEntities.size()));

            for (int i = 0; i < eventEntities.size(); i++) {
                final EventEntity eventEntity = eventEntities.get(i);
                sb.append(messageProvider.get("event.proposal.event",
                        i,
                        eventEntity.getType(),
                        eventEntity.getName(),
                        eventEntity.getDate(),
                        eventEntity.getPlaceName(),
                        eventEntity.getPlaceAddress(),
                        eventEntity.getAfishaUrl(),
                        eventEntity.getAfishaImgUrl()));
            }

            return Message.builder()
                    .setUserVkId(message.userId().get())
                    .setText(sb.toString())
                    .build();
        }
    }
}
