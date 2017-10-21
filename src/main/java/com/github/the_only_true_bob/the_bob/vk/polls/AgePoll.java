package com.github.the_only_true_bob.the_bob.vk.polls;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.vk.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AgePoll implements Poll {

    @Autowired
    private DataService dataService;
    @Value("${poll.age.id}")
    private String id;
    @Value("${poll.age.1.id}")
    private String option1id;
    @Value("${poll.age.2.id}")
    private String option2id;
    @Value("${poll.age.3.id}")
    private String option3id;
    @Value("${poll.age.4.id}")
    private String option4id;
    @Value("${poll.age.5.id}")
    private String option5id;
    @Value("${poll.age.whatever.id}")
    private String optionWhateverId;

    private final Map<String, PollOption> optionsMap = options();

    @Override
    public String id() {
        return id;
    }

    @Override
    public Map<String, PollOption> options() {
        final Map<String, PollOption> map = new HashMap<>();
        map.put(option1id, () -> "1");
        map.put(option2id, () -> "2");
        map.put(option3id, () -> "3");
        map.put(option4id, () -> "4");
        map.put(option5id, () -> "5");
        map.put(optionWhateverId, () -> "100");
        return map;
    }

    @Override
    public void handle(Message message) {
        message.optionId()
                .map(optionsMap::get)
                .ifPresent(pollOption -> {
                    final Optional<UserEntity> user = dataService.findUserByVkId(message.userId().get());
                    user.ifPresent(userEntity -> {
                        userEntity.setAcceptableAgeDiff(Integer.parseInt(pollOption.value()));
                        dataService.saveUser(userEntity);
                    });
                });
    }
}
