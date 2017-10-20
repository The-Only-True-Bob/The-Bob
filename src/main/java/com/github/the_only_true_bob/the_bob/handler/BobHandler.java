package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.vk.Message;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

class BobHandler implements Handler {

    @Autowired
    private VkService vkService;
    @Autowired
    private MessageProvider messageProvider;

    @Override
    public void accept(Message inMessage) {
        // TODO: 20.10.17 Accept message and process
        System.err.println("That's the way the cookie crumbles!");
        switch (inMessage.type()) {
            case MESSAGE:
                // TODO: 20.10.17 Figure out what does he talking about and does his message have an attachments
                System.out.println("processing message");
                break;
            case POLL:
                // TODO: 20.10.17 Update user preferences in database
                System.out.println("processing poll");
                break;
            default:
                inMessage.userId()
                        .ifPresent(userId ->
                                vkService.sendMessage(
                                        Message.builder()
                                                .setText(messageProvider.get("error.answer"))
                                                .setUserVkId(userId)
                                                .build()));
        }
    }
}
