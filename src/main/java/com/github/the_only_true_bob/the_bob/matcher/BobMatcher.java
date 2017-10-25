package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.handler.CommandStatus;
import com.github.the_only_true_bob.the_bob.vk.User;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class BobMatcher implements Matcher {

    @Autowired
    private DataService dataService;
    @Autowired
    private VkService vkService;

    @Override
    public Map<User, List<UserMatch>> match(final EventEntity eventEntity) {
        final List<String> usersVkIds =
                dataService.findUsersByEvent(eventEntity).stream()
                        .filter(eventUserEntity ->
                                CommandStatus.SEARCH_FOR_COMPANION.equals(eventUserEntity.getStatus()))
                        .map(EventUserEntity::getUser)
                        .map(UserEntity::getVkId)
                        .collect(toList());

        final List<User> users = vkService.getUsers(usersVkIds);

        final Map<CommunicativePair, UserMatch> pairsMatches =
                users.stream()
                        .flatMap(userA ->
                                users.stream()
                                        .map(userB -> CommunicativePair.of(userA, userB)))
                        .distinct()
                        .collect(toMap(Function.identity(), UserMatch::from));

        final Map<User, List<Entry<User, UserMatch>>> usersMatches =
                pairsMatches.entrySet().stream()
                        .flatMap(entry1 -> Stream.of(leftUserMatch(entry1), rightUserMatch(entry1)))
                        .collect(groupingBy(Entry::getKey, toList()));

        return usersMatches
                .entrySet().stream()
                .collect(toMap(
                        Entry::getKey,
                        entry ->
                                entry.getValue().stream()
                                        .map(Entry::getValue)
                                        .filter(UserMatch::matches)
                                        .sorted(Comparator.comparing(UserMatch::value))
                                        .collect(toList())));
    }

    private Entry<User, UserMatch> leftUserMatch(final Entry<CommunicativePair, UserMatch> entry) {
        return entry(entry.getKey().left(), entry.getValue());
    }

    private Entry<User, UserMatch> rightUserMatch(final Entry<CommunicativePair, UserMatch> entry) {
        return entry(entry.getKey().right(), entry.getValue());
    }

    private Entry<User, UserMatch> entry(final User user, final UserMatch match) {
        return new AbstractMap.SimpleEntry<>(user, match);
    }
}
