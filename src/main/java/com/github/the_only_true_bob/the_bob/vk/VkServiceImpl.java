package com.github.the_only_true_bob.the_bob.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component("vkService")
public class VkServiceImpl implements VkService {
    @Autowired
    private VkApiClient vkApiClient;
    @Autowired
    private GroupActor groupActor;
    @Autowired
    private UserActor userActor;
    @Autowired
    private ServiceActor serviceActor;
    @Autowired
    private List<UserField> allFields;
    @Autowired
    private String groupId;

    @Override
    public VkService sendMessage(final Message message) {
        message.userId().ifPresent(
                userId -> message.text().ifPresent(
                        text -> {
                            if (message.criteriaPollIds().isPresent()) {
                                sendMessageWithAttachment(userId, text, message.criteriaPollIds().get());
                            } else {
                                sendMessage(userId, text);
                            }
                        }));
        return this;
    }

    @Override
    public User getUser(final String userVkId) {
        return getUserWithAllFields(userVkId)
                .map(this::toUser)
                .orElse(User.empty());
    }

    @Override
    public List<User> getUsers(List<String> userIds) {
        return getUsersWithAllFields(userIds).stream()
                .map(this::toUser)
                .collect(toList());
    }

    private void sendMessage(String userId, String text) {
        try {
            sendingTextQuery(userId, text)
                    .execute();
        } catch (ApiException | ClientException e) {
            // TODO: 21/10/17 log!
            e.printStackTrace();
        }
    }

    private void sendMessageWithAttachment(final String userId, final String text, final String[] polls) {
        try {
            sendingTextQuery(userId, text)
                    .attachment(
                            Arrays.stream(polls)
                                    .map(pollId -> String.format("wall%s_%s", groupId, pollId))
                                    .collect(toList()))
                    .execute();
        } catch (ApiException | ClientException e) {
            // TODO: 22/10/17 log!
            e.printStackTrace();
        }
    }

    private MessagesSendQuery sendingTextQuery(final String userId, final String text) {
        return vkApiClient
                .messages()
                .send(groupActor)
                .userId(Integer.parseInt(userId))
                .message(text);
    }

    private List<UserXtrCounters> getUsersWithAllFields(List<String> userIds) {
        try {
            return
                    Optional.ofNullable(
                            vkApiClient
                                    .users()
                                    .get(userActor)
                                    .userIds(userIds)
                                    .fields(allFields)
                                    .execute())
                            .orElseGet(Collections::emptyList);
        } catch (ApiException | ClientException e) {
            // TODO: 21/10/17 log!
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Optional<UserXtrCounters> getUserWithAllFields(String userVkId) {
        try {
            return Optional.ofNullable(
                    vkApiClient
                            .users()
                            .get(userActor)
                            .userIds(userVkId)
                            .fields(allFields)
                            .execute()
                            .get(0));
        } catch (ApiException | ClientException e) {
            // TODO: 21/10/17 log!
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User toUser(UserXtrCounters userXtrCounters) {
        final List<User> friends = getFriends(userXtrCounters);
        final List<Group> groups = getGroups(userXtrCounters);

        return User.builder()
                .setVkId(String.valueOf(userXtrCounters.getId()))
                .setFirstName(userXtrCounters.getFirstName())
                .setLastName(userXtrCounters.getLastName())
                .setFriends(friends)
                .setGroups(groups)
                .setSex(userXtrCounters.getSex().getValue().toString())
                .setAbout(userXtrCounters.getAbout())
                .setBirthday(userXtrCounters.getBdate())
                .setCity(userXtrCounters.getCity().getTitle())
                .setHomeTown(userXtrCounters.getHomeTown())
                .setMusic(userXtrCounters.getMusic())
                .build();
    }

    private List<Group> getGroups(final UserXtrCounters userXtrCounters) {
//        try {
//            final List<Integer> friendsIds =
//                    vkApiClient
//                            .groups()
//                            .get(serviceActor)
//                            .userId(userXtrCounters.getId())
//                            .execute()
//                            .getItems();
//
//            return vkApiClient
//                    .users()
//                    .get(userActor)
//                    .userIds(friendsIds.stream()
//                            .map(String::valueOf)
//                            .collect(toList()))
//                    .execute().stream()
//                    .map(friendXtrCounters ->
//                            User.builder()
//                                    .setVkId(String.valueOf(friendXtrCounters.getId()))
//                                    .setFirstName(friendXtrCounters.getFirstName())
//                                    .setLastName(friendXtrCounters.getLastName())
//                                    .build())
//                    .collect(toList());
//        } catch (ApiException | ClientException e) {
//            // TODO: 21/10/17 log!
//            e.printStackTrace();
//        }
        return Collections.emptyList();
    }

    private List<User> getFriends(final UserXtrCounters userXtrCounters) {
        try {
            final List<Integer> friendsIds =
                    vkApiClient
                            .friends()
                            .get(serviceActor)
                            .userId(userXtrCounters.getId())
                            .execute()
                            .getItems();

            return vkApiClient
                    .users()
                    .get(userActor)
                    .userIds(friendsIds.stream()
                            .map(String::valueOf)
                            .collect(toList()))
                    .execute().stream()
                    .map(friendXtrCounters ->
                            User.builder()
                                    .setVkId(String.valueOf(friendXtrCounters.getId()))
                                    .setFirstName(friendXtrCounters.getFirstName())
                                    .setLastName(friendXtrCounters.getLastName())
                                    .build())
                    .collect(toList());
        } catch (ApiException | ClientException e) {
            // TODO: 21/10/17 log!
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
