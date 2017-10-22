package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

public class ChooseSearchCriteriaCommand implements BobCommand {

    @Autowired
    private DataService dataService;
    @Autowired
    private MessageProvider messageProvider;

    @Value("${poll.age.id}")
    private String pollAgeId;
    @Value("${poll.sex.id}")
    private String pollSexId;
    @Value("${group.id}")
    private int groupId;

    @Override
    public Message handleMessage(final Message message) {
        final UserEntity user = dataService.findUserByVkId(message.userId().get()).get();
        final String text = message.text().orElse("");

        final Optional<EventUserEntity> eventUserEntity = dataService.findEventsByUser(user).stream()
                .filter(eue -> CommandStatus.CHOOSE_SEARCH_CRITERIA.equals(eue.getStatus()))
                .findFirst();
        final String responseMessage = eventUserEntity
                .map(eue -> {
                    if ("1".equals(text)) {
                        user.setStatus(CommandStatus.NONE);
                        eue.setStatus(CommandStatus.SEARCH_FOR_COMPANION);
                        dataService.saveUser(user);
                        dataService.saveEventUser(eue);
                        return messageProvider.get("companion.suggestion.pools");
                    } else if ("2".equals(text)) {
                        user.setStatus(CommandStatus.NONE);
                        eue.setStatus(CommandStatus.SEARCH_FOR_COMPANION);
                        dataService.saveUser(user);
                        dataService.saveEventUser(eue);
                        return messageProvider.get("companion.suggestion.finish");
                    }
                    user.setStatus(CommandStatus.NONE);
                    eue.setStatus(CommandStatus.WAS_INTERESTED_IN);
                    dataService.saveUser(user);
                    dataService.saveEventUser(eue);
                    return null;
                })
                .orElse(messageProvider.get("companion.suggestion.strange.deny_companion"));

        return Message.builder()
                .setUserVkId(user.getVkId())
                .setText(responseMessage)
                .setCriteriaPollsId(pollAgeId, pollSexId)
                .build();
    }
}
