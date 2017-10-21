package com.github.the_only_true_bob.the_bob.afisha;

import com.github.the_only_true_bob.the_bob.ApplicationConfiguration;
import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class Main {

    public static final DataService dataService =
            new AnnotationConfigApplicationContext(ApplicationConfiguration.class).
                    getBean("dataService", DataService.class);

    public static void main(String[] args) {

        AfishaParser afishaParser = new AfishaParser();
        ArrayList<EventEntity> eventsEntities = afishaParser.getInfoFromAfisha();
        System.out.println("eventsEntities is received");
        eventsEntities.forEach(dataService::saveEvent);
    }
}




