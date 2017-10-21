package com.github.the_only_true_bob.the_bob.handler.command;

import com.github.the_only_true_bob.the_bob.vk.Message;

public interface BobCommand {
    Message handleMessage(Message message);
}
