package com.github.the_only_true_bob.the_bob.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("vkService")
public class VkServiceImpl implements VkService {
    @Autowired
    private VkApiClient vkApiClient;
    @Autowired
    private GroupActor groupActor;
    @Autowired
    private UserActor userActor;
    @Autowired
    private List<UserField> allFields;

    @Override
    public VkService sendMessage(final Message message) {
        message.userId().ifPresent(
                userId ->
                        message.text().ifPresent(
                                text ->
                                        sendMessage(userId, text)));
        return this;
    }

    @Override
    public User getUser(final String userVkId) {
        return getUserWithAllFields(userVkId)
                .map(userXtrCounters ->
                        User.builder()
                                .setSex(userXtrCounters.getSex().getValue().toString())
                                .setAbout(userXtrCounters.getAbout())
                                .setBirthday(userXtrCounters.getBdate())
                                .setCity(userXtrCounters.getCity().getTitle())
                                .setHomeTown(userXtrCounters.getHomeTown())
                                .setMusic(userXtrCounters.getMusic())
                                .build())
                .orElse(User.empty());
    }

    private void sendMessage(String userId, String text) {
        try {
            vkApiClient
                    .messages()
                    .send(groupActor)
                    .userId(Integer.parseInt(userId))
                    .message(text)
                    .execute();
        } catch (ApiException | ClientException e) {
            // TODO: 21/10/17 log!
            e.printStackTrace();
        }
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
}
