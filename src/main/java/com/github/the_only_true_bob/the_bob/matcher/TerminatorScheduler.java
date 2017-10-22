package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import com.github.the_only_true_bob.the_bob.matcher.criterias.CriteriaValue;
import com.github.the_only_true_bob.the_bob.vk.Message;
import com.github.the_only_true_bob.the_bob.vk.User;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TerminatorScheduler {

    @Autowired
    private DataService dataService;
    @Autowired
    private Matcher bobMatcher;
    @Autowired
    private VkService vkService;
    @Autowired
    private MessageProvider messageProvider;

    @Scheduled(cron = "${terminator.scheduling.cron}")
    public void reportCurrentTime() {
        dataService.findAllEventUsers().stream()
                .map(EventUserEntity::getEvent)
                .distinct()
                .filter(ee -> ee.getEventUserEntities().size() > 2)
                .map(bobMatcher::match)
                .forEach((final Map<User, List<UserMatch>> map) ->
                        map.entrySet().stream()
                                .flatMap(entry ->
                                        entry.getValue().isEmpty()
                                                ? Stream.empty()
                                                : Stream.of(entry))
                                .map(entry -> {
                                    final User user = entry.getKey();
                                    final List<UserMatch> matchesList = entry.getValue();

                                    final Message.Builder builder =
                                            Message.builder().setUserVkId(user.vkId());

                                    final String companions = matchesList.stream()
                                            .map(userMatch -> {
                                                final User companion = userMatch.notMe(user);

                                                final String criterias = userMatch.criteriaValues().stream()
                                                        .filter(CriteriaValue::is)
                                                        .map(CriteriaValue::toString)
                                                        .collect(Collectors.joining());

                                                return messageProvider.get(
                                                        "companion.suggestion.companion.info",
                                                        companion.firstName(),
                                                        companion.lastName(),
                                                        criterias);
                                            })
                                            .collect(Collectors.joining());

                                    return builder.setText(
                                            String.format("%s %s",
                                                    messageProvider.get("companion.suggestion.companion.intro",
                                                            matchesList.size()),
                                                    companions))
                                            .build();
                                })
                                .forEach(vkService::sendMessage));
    }
}
