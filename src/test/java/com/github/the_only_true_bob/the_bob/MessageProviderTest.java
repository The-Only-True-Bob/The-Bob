package com.github.the_only_true_bob.the_bob;

import com.github.the_only_true_bob.the_bob.handler.MessageProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MessageProviderTest {

    @Autowired
    private MessageProvider messageProvider;

    @Test
    public void test1() throws Exception {
        final String actual = messageProvider.get("error.answer");
        Assert.assertEquals("Я не знаю, как на это реагировать", actual);
    }
}
