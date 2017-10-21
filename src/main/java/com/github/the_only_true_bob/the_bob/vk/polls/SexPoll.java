package com.github.the_only_true_bob.the_bob.vk.polls;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SexPoll implements Poll {

    @Autowired
    private DataService dataService;
    @Value("${poll.sex.id}")
    private String id;
    @Value("${poll.sex.2.id}")
    private String optionManId;
    @Value("${poll.sex.2.id}")
    private String optionWomanId;
    @Value("${poll.sex.3.id}")
    private String optionWhateverId;

    private final Map<String, PollOption> optionsMap = options();

    @Override
    public String id() {
        return id;
    }

    @Override
    public Map<String, PollOption> options() {
        final Map<String, PollOption> map = new HashMap<>();
        map.put(optionManId, () -> "2");
        map.put(optionWomanId, () -> "1");
        map.put(optionWhateverId, () -> "3");
        return map;
    }

    @Override
    public void handle(Message message) {
        message.optionId()
                .map(optionsMap::get)
                .ifPresent(pollOption -> {
                    final Optional<UserEntity> user = dataService.findUserByVkId(message.userId().get());
                    user.ifPresent(userEntity -> {
                        userEntity.setAcceptableSex(pollOption.value());
                        dataService.saveUser(userEntity);
                    });
                });
    }
}
