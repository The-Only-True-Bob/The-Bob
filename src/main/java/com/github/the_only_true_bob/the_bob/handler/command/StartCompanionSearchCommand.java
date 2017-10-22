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
        System.out.println("=================================");
        System.out.println(getClass().getName());
        System.out.println("=================================");
        final UserEntity user = dataService.findUserByVkId(message.userId().get()).get();
        final String text = message.text().orElse("");
        final Optional<EventUserEntity> eventUserEntity =
                dataService.findEventsByUser(user).stream()
                        .filter(eue -> CommandStatus.IS_NEED_IN_COMPANION.equals(eue.getStatus()))
                        .findFirst();

        final String responseMessage = eventUserEntity
                .map(eue -> {
                    if (text.equals("1")) {
                        user.setStatus(CommandStatus.CHOOSE_SEARCH_CRITERIA);
                        eue.setStatus(CommandStatus.CHOOSE_SEARCH_CRITERIA);
                        dataService.saveUser(user);
                        dataService.saveEventUser(eue);
                        System.out.println("========================================");
                        System.out.println("User's answer is 1");
                        System.out.println("========================================");
                        return messageProvider.get("companion.suggestion.choose_criterions");
                    } else if (text.equals("2")) {
                        user.setStatus(CommandStatus.NONE);
                        eue.setStatus(CommandStatus.WAS_INTERESTED_IN);
                        dataService.saveUser(user);
                        dataService.saveEventUser(eue);
                        System.out.println("========================================");
                        System.out.println("User's answer is 2");
                        System.out.println("========================================");
                        return messageProvider.get("companion.suggestion.deny_companion");
                    }
                    System.out.println("========================================");
                    System.out.println("User's answer is something");
                    System.out.println("========================================");
                    user.setStatus(CommandStatus.NONE);
                    eue.setStatus(CommandStatus.WAS_INTERESTED_IN);
                    dataService.saveUser(user);
                    dataService.saveEventUser(eue);
                    return null;
                })
                .orElse(messageProvider.get("companion.suggestion.strange.deny_companion"));
        return Message
                .builder()
                .setUserVkId(user.getVkId())
                .setText(responseMessage)
                .build();
    }
}
