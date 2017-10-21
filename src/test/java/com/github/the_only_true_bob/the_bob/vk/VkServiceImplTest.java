package com.github.the_only_true_bob.the_bob.vk;

import com.github.the_only_true_bob.the_bob.ApplicationConfiguration;
import com.vk.api.sdk.objects.base.Sex;
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

    @Test
    public void test() throws Exception {
        final User user = vkService.getUser(String.valueOf(24276156));
        assertEquals(Sex.MALE.getValue().toString(), user.sex().get());
    }
}