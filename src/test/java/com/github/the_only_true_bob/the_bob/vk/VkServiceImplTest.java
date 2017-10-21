package com.github.the_only_true_bob.the_bob.vk;

import com.github.the_only_true_bob.the_bob.ApplicationConfiguration;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.base.Sex;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class VkServiceImplTest {

    @Autowired
    private VkService vkService;
    @Autowired
    private VkApiClient vkApiClient;

    @Test
    public void testService() throws Exception {
        final User user = vkService.getUser(String.valueOf(24276156));
        assertEquals(Sex.MALE.getValue().toString(), user.sex().get());
    }

    // HARDCODE USER TOKEN - JUST FOR HACKATON ONLY
//    @Test
//    public void testGetGroup() throws Exception {
//        final int size =
//                vkApiClient
//                        .groups()
//                        .get(new UserActor(serviceActor.getId(), serviceActor.getAccessToken()))
//                        .userId(24276156)
//                        .execute()
//                        .getItems()
//                        .size();
//        System.out.println(size);
//    }
}