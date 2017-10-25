package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.handler.command.BobCommand;
import com.github.the_only_true_bob.the_bob.vk.Message;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import com.github.the_only_true_bob.the_bob.vk.polls.Poll;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

class BobHandler implements Handler {

    @Autowired
    private VkService vkService;
    @Autowired
    private DataService dataService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private List<Poll> polls;
    @Resource
    private Map<String, BobCommand> commandsMap;

    @Override
    public void accept(Message message) {
        switch (message.type()) {
            case ALLOW_MESSAGE:
                message.userId()
                        .ifPresent(userId -> {
                            if (dataService.findUserByVkId(userId).isPresent()) {
                                vkService.sendMessage(Message.builder()
                                        .setUserVkId(userId)
                                        .setText(messageProvider.get("allow_back"))
                                        .build());
                            } else {
                                dataService.saveUser(new UserEntity(userId));
                                vkService.sendMessage(Message.builder()
                                        .setUserVkId(userId)
                                        .setText(messageProvider.get("introduction"))
                                        .build());
                            }
                        });
                break;
            case MESSAGE:
                message.userId().ifPresent(
                        userId -> {
                            final UserEntity userEntity =
                                    dataService
                                            .findUserByVkId(userId)
                                            .orElse(new UserEntity(userId));
                            vkService.sendMessage(
                                    commandsMap
                                            .get(userEntity.getStatus())
                                            .handleMessage(message)
                            );
                        });
                break;
            case POLL:
                message.userId().ifPresent(userId ->
                        message.pollId()
                                .flatMap(pollId ->
                                        polls.stream()
                                                .filter(poll -> pollId.equals(poll.id()))
                                                .findFirst())
                                .ifPresent(poll -> poll.handle(message)));
                break;
            case UNASSIGNED:
                message.userId().ifPresent(userId ->
                        vkService.sendMessage(Message.builder()
                                .setText(messageProvider.get("error.answer"))
                                .setUserVkId(userId)
                                .build()));
        }
    }
}
