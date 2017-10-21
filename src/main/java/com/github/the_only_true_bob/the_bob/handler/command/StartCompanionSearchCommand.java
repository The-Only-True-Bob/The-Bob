package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class StartCompanionSearchCommand implements BobCommand {

    @Autowired
    private DataService dataService;
    @Autowired
    private MessageProvider messageProvider;

    @Override
    public Message handleMessage(final Message message) {
        final UserEntity user = dataService.findUserByVkId(message.userId().get()).get();
        final String text = message.text().orElse("");
        final Optional<EventUserEntity> eventUserEntity =
                dataService.findEventsByUser(user).stream()
                        .filter(eue -> CommandStatus.IS_NEED_IN_COMPANION.equals(eue.getStatus()))
                        .findFirst();

        return eventUserEntity
                .map(eue -> {
                    if ("1".equals(text)) {
                        user.setStatus(CommandStatus.CHOOSE_SEARCH_CRITERIA);
                        eue.setStatus(CommandStatus.CHOOSE_SEARCH_CRITERIA);
                        return Message.builder()
                                .setUserVkId(user.getVkId())
                                .setText(messageProvider.get("companion.suggestion.choose_criterions"))
                                .build();
                    } else if ("2".equals(text)) {
                        user.setStatus(CommandStatus.NONE);
                        eue.setStatus(CommandStatus.WAS_INTERESTED_IN);
                        return Message.builder()
                                .setUserVkId(user.getVkId())
                                .setText(messageProvider.get("companion.suggestion.deny_companion"))
                                .build();
                    }
                    user.setStatus(CommandStatus.NONE);
                    eue.setStatus(CommandStatus.WAS_INTERESTED_IN);
                    return null;
                })
                .orElse(
                        Message.builder()
                                .setUserVkId(user.getVkId())
                                .setText(messageProvider.get("companion.suggestion.strange.deny_companion"))
                                .build());
    }
}
