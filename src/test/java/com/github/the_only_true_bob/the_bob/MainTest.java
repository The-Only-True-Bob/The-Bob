package com.github.the_only_true_bob.the_bob;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.EventUserRepository;
import com.github.the_only_true_bob.the_bob.dao.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MainTest {

    @Autowired
    private DataService dataService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventUserRepository eventUserRepository;

    public MainTest() {
    }

    @Test
    public void dataBaseTest() throws Exception{
        UserEntity userEntity = new UserEntity("123456");
        EventEntity eventEntity = new EventEntity("afisha_id");
        EventUserEntity eventUserEntity = new EventUserEntity();

        eventUserEntity.setUser(userEntity);
        eventUserEntity.setEvent(eventEntity);
        eventUserEntity.setStage("stage");
        eventUserEntity.setStatus("status");

        eventUserRepository.save(eventUserEntity);

        UserEntity user1 = dataService.findUserById(1).get();

        assertEquals("123456", userEntity.getVkId());
    }

}