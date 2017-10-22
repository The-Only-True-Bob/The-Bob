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
        System.out.println("=================================");
        System.out.println(getClass().getName());
        System.out.println("=================================");
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
                    updateEntities(user, eue, CommandStatus.IS_NEED_IN_COMPANION, CommandStatus.IS_NEED_IN_COMPANION);
                    System.out.println("==========================================================");
                    System.out.println("====== Changing both status to is_need_in_companion ======");
                    System.out.println(dataService.findUserByVkId(message.userId().get()).get().getStatus());
                    System.out.println(dataService.findEventUserById(eue.getId()).get().getStatus());
                    System.out.println("==========================================================");
                    return messageProvider.get("companion.suggestion.intro");
                })
                .orElseGet(() -> {
                            user.setStatus(CommandStatus.NONE);
                            dataService.saveUser(user);
                            System.out.println("==========================================================");
                            System.out.println("====== Changing user status to none ======");
                            System.out.println(dataService.findUserByVkId(message.userId().get()).get().getStatus());
                            System.out.println("==========================================================");
                            return messageProvider.get("companion.suggestion.strange.deny_companion");
                        }
                );

        System.out.println("==========================================================");
        System.out.println("====== Statuses ======");
        System.out.println(dataService.findUserByVkId(message.userId().get()).get().getStatus());
        dataService.findEventsByUser(user).forEach(bla -> System.out.println(bla.getStatus()));
        System.out.println("==========================================================");
        return Message
                .builder()
                .setUserVkId(user.getVkId())
                .setText(responseMessage)
                .build();
    }

    private void updateEntities(UserEntity user, EventUserEntity eue, String userStatus, String eueStatus) {
        user.setStatus(userStatus);
        eue.setStatus(eueStatus);
        dataService.saveUser(user);
        dataService.saveEventUser(eue);
    }
}
