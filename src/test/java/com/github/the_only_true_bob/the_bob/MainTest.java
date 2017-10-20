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

import javax.transaction.Transactional;

import java.util.List;

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
    @Transactional
    public void dataBaseTest() throws Exception{
        UserEntity userEntity = new UserEntity("123456");
        EventEntity eventEntity = new EventEntity("afishaId","url","img","date","placeName","placeAddress","type","name");
        EventUserEntity eventUserEntity = new EventUserEntity();

        userRepository.save(userEntity);
        eventRepository.save(eventEntity);


        eventUserEntity.setUser(userEntity);
        eventUserEntity.setEvent(eventEntity);
        eventUserEntity.setStage("stage");
        eventUserEntity.setStatus("status");

        eventUserRepository.save(eventUserEntity);


     //   final EventUserEntity savedEventUser = eventUserRepository.save(eventUserEntity);
     //   System.out.println(savedEventUser.getId());
//
//      //  UserEntity user1 = dataService.findUserById(1L).get();
//        EventEntity event1 = dataService.findEventById(2L).get();
//
        List<EventUserEntity> eventUserEntity1 = dataService.findUsersByEvent(eventEntity);
        System.out.println(eventUserEntity1);

//        //assertEquals("123456", user1.getVkId());
//        assertEquals("afisha_id",event1.getAfishaId());
    }

}