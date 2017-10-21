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

        //add Users
        UserEntity userEntity0 = new UserEntity("111111");
        userEntity0.setAcceptableAgeDiff(1);
        userEntity0.setAcceptableSex("M");

        UserEntity userEntity1 = new UserEntity("222222");
        userEntity1.setAcceptableAgeDiff(1);
        userEntity1.setAcceptableSex("M");

        UserEntity userEntity2 = new UserEntity("333333");
        userEntity2.setAcceptableAgeDiff(1);
        userEntity2.setAcceptableSex("F");

        dataService.saveUser(userEntity0);
        dataService.saveUser(userEntity1);
        dataService.saveUser(userEntity2);


        //add Events
        EventEntity eventEntity0 = new EventEntity("afishaId1","AwesomePlaceName","city, street, house, note","Concert","Muse");
        EventEntity eventEntity1 = new EventEntity("afishaId2","SuperCoolPlaceName","city, street, house","Cinema","Matrix 99");
        EventEntity eventEntity2 = new EventEntity("afishaId3","SuperCoolPlaceName","city, street, house","Cinema","Matrix 99");

        dataService.saveEvent(eventEntity0);
        dataService.saveEvent(eventEntity1);
        dataService.saveEvent(eventEntity2);


        //Add eventUser
        EventUserEntity eventUserEntity0 = new EventUserEntity();
        EventUserEntity eventUserEntity1 = new EventUserEntity();
        EventUserEntity eventUserEntity2 = new EventUserEntity();
        EventUserEntity eventUserEntity3 = new EventUserEntity();

        eventUserEntity0.setUser(userEntity0);
        eventUserEntity0.setEvent(eventEntity0);
        eventUserEntity0.setStage("finish");
        eventUserEntity0.setStatus("active");

        eventUserEntity1.setUser(userEntity1);
        eventUserEntity1.setEvent(eventEntity1);
        eventUserEntity1.setStage("findPartner");
        eventUserEntity1.setStatus("passive");

        eventUserEntity2.setUser(userEntity2);
        eventUserEntity2.setEvent(eventEntity2);
        eventUserEntity2.setStage("findPartner");
        eventUserEntity2.setStatus("passive");

        eventUserEntity3.setUser(userEntity2);
        eventUserEntity3.setEvent(eventEntity0);
        eventUserEntity3.setStage("findPartner");
        eventUserEntity3.setStatus("active");


        dataService.saveEventUser(eventUserEntity0);
        dataService.saveEventUser(eventUserEntity1);
        dataService.saveEventUser(eventUserEntity2);
        dataService.saveEventUser(eventUserEntity3);


        //show
        List<UserEntity> listUsersByAcceptableAgeAndAcceptableAgeDiff = dataService.findUsersByAcceptableSexAndAcceptableAgeDiff("M",1);
        listUsersByAcceptableAgeAndAcceptableAgeDiff.stream()
                .map(UserEntity::getVkId)
                .forEach(System.out::println);

//        final EventUserEntity savedEventUser = eventUserRepository.save(eventUserEntity);
//        System.out.println(savedEventUser.getId());
//        UserEntity user1 = dataService.findUserById(1L).get();
//        EventEntity event1 = dataService.findEventById(2L).get();

//        assertEquals("123456", user1.getVkId());
//        assertEquals("afisha_id",event1.getAfishaId());

    }

}