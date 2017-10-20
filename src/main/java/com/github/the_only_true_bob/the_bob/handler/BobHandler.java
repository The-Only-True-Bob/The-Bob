package com.github.the_only_true_bob.the_bob.handler;

import com.github.the_only_true_bob.the_bob.vk.Message;

class BobHandler implements Handler {
    @Override
    public void accept(Message message) {
        System.err.println("That's the way the cookie crumbles!");
        switch (message.type()) {
            case MESSAGE:
                // TODO: 20.10.17 Figure out what does he talking about and does his message have an attachments
                System.out.println("processing message");
                break;
            case POLL:
                // TODO: 20.10.17 Update user preferences in database
                System.out.println("processing poll");
                break;
            default:
                // TODO: 20.10.17 Send response message to the conversation
                System.out.println("processing unknown message");
        }
    }
}
