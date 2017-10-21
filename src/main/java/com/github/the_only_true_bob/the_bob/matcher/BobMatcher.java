package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.vk.User;
import com.github.the_only_true_bob.the_bob.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
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
        final List<UserEntity> usersEntities =
                dataService.findUsersByEvent(eventEntity).stream()
                        .filter(eventUserEntity -> "passive".equals(eventUserEntity.getStatus()))
                        .map(EventUserEntity::getUser)
                        .collect(toList());

        final List<String> usersVkIds = usersEntities.stream()
                .map(UserEntity::getVkId)
                .collect(toList());

        final List<User> users = vkService.getUsers(usersVkIds);

        // алгоритм: tuple - match, потом flatMap на user - match, потом collect groupingBy user

        final Set<CommunicativePair> pairs = users.stream()
                .filter(this::vkIdIsNotNull)
                .flatMap(userA -> users.stream()
                        .filter(this::vkIdIsNotNull)
                        .map(userB -> CommunicativePair.of(userA, userB)))
                .collect(toSet());

        final Map<CommunicativePair, UserMatch> pairsMatches = pairs.stream()
                .collect(toMap(Function.identity(), UserMatch::from));

        final Stream<Map.Entry<User, UserMatch>> entryStream = pairsMatches.entrySet().stream()
                .flatMap(entry -> Stream.of(
                        new AbstractMap.SimpleEntry<>(entry.getKey().left(), entry.getValue()),
                        new AbstractMap.SimpleEntry<>(entry.getKey().right(), entry.getValue())));

        final Map<User, List<Map.Entry<User, UserMatch>>> usersMatches = entryStream
                .collect(groupingBy(Map.Entry::getKey, toList()));

        return usersMatches
                .entrySet().stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(Map.Entry::getValue)
                                .filter(UserMatch::matches)
                                .sorted(Comparator.comparing(UserMatch::value))
                                .collect(toList())));
    }

    private boolean vkIdIsNotNull(final User user) {
        return user.vkId() != null;
    }
}
