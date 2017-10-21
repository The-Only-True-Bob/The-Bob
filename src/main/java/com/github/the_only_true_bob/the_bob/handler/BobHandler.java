package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.vk.Message;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import com.github.the_only_true_bob.the_bob.vk.polls.Poll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

class BobHandler implements Handler {

    @Autowired
    private VkService vkService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private Stream<Poll> polls;

    @Override
    public void accept(Message message) {
        // TODO: 20.10.17 Accept message and process
        System.err.println("That's the way the cookie crumbles!");
        switch (message.type()) {
            case MESSAGE:
                // TODO: 20.10.17 Figure out what does he talking about and does his message have an attachments
                System.out.println("processing message");
                break;
            case POLL:
                message.userId().ifPresent(userId ->
                        message.pollId()
                                .flatMap(pollId -> polls
                                        .filter(poll -> pollId.equals(poll.id()))
                                        .findFirst())
                                .ifPresent(poll -> poll.handle(message)));
                break;
            default:
                message.userId()
                        .ifPresent(userId -> vkService.sendMessage(
                                Message.builder()
                                        .setText(messageProvider.get("error.answer"))
                                        .setUserVkId(userId)
                                        .build()));
        }
    }
}
