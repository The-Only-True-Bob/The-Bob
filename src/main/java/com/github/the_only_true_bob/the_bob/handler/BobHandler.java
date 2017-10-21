package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.vk.Message;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import com.github.the_only_true_bob.the_bob.vk.polls.Poll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

class BobHandler implements Handler {

    @Autowired
    private VkService vkService;
    @Autowired
    private DataService dataService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private Stream<Poll> polls;

    @Override
    public void accept(Message message) {
        // TODO: 20.10.17 Accept message and process
        System.err.println("That's the way the cookie crumbles!");
        switch (message.type()) {
            case ALLOW_MESSAGE:
                message.userId().ifPresent(userId -> {
                    dataService.saveUser(new UserEntity(userId));
                    vkService.sendMessage(Message.builder()
                            .setUserVkId(userId)
                            .setText(messageProvider.get("introduction"))
                            .build());
                });
                break;
            case MESSAGE:
                // TODO: 21/10/17 add more sence
                message.userId().ifPresent(userId ->
                        dataService.findUserByVkId(userId).ifPresent(userEntity ->
                                vkService.sendMessage(Message.builder()
                                        .setUserVkId(userId)
                                        .setText("incoming message")
                                        .build())
                        ));
                break;
            case POLL:
                // TODO: 21/10/17 add more sence
                message.userId().ifPresent(userId -> {
                    message.pollId()
                            .flatMap(pollId -> polls
                                    .filter(poll -> pollId.equals(poll.id()))
                                    .findFirst())
                            .ifPresent(poll -> poll.handle(message));
                    vkService.sendMessage(Message.builder()
                            .setUserVkId(userId)
                            .setText("poll vote")
                            .build());
                });
                break;
            default:
                message.userId()
                        .ifPresent(userId ->
                                vkService.sendMessage(Message.builder()
                                        .setText(messageProvider.get("error.answer"))
                                        .setUserVkId(userId)
                                        .build()));
        }
    }
}
