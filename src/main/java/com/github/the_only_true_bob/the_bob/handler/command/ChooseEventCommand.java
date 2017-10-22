package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ChooseEventCommand implements BobCommand {

    @Autowired
    private DataService dataService;
    @Autowired
    private MessageProvider messageProvider;

    @Override
    public Message handleMessage(final Message message) {
        final UserEntity user = dataService.findUserByVkId(message.userId().get()).get();
        final List<EventUserEntity> events = dataService.findEventsByUser(user);
        final String text = message.text().orElse("");
        final Optional<EventUserEntity> eventUserEntity = events.stream()
                .filter(eue -> CommandStatus.LISTED.equals(eue.getStatus()))
                .peek(eue -> {
                    eue.setStatus(CommandStatus.NONE);
                    dataService.saveEventUser(eue);
                })
                .filter(eue -> text.equals(String.valueOf(eue.getNumber())))
                .findFirst();

        final String responseMessage = eventUserEntity
                .map(eue -> {
                    user.setStatus(CommandStatus.IS_NEED_IN_COMPANION);
                    eue.setStatus(CommandStatus.IS_NEED_IN_COMPANION);
                    dataService.saveUser(user);
                    dataService.saveEventUser(eue);
                    return messageProvider.get("companion.suggestion.intro");
                })
                .orElseGet(() -> {
                            user.setStatus(CommandStatus.NONE);
                            return messageProvider.get("companion.suggestion.strange.deny_companion");
                        }
                );

        dataService.saveUser(user);
        return Message
                .builder()
                .setUserVkId(user.getVkId())
                .setText(responseMessage)
                .build();
    }
}
