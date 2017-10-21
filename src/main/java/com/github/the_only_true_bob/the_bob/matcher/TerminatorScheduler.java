package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TerminatorScheduler {

    @Autowired
    private DataService dataService;

    @Scheduled(fixedRate = 40000)
    public void reportCurrentTime() {
        // TODO: 22/10/17 Проверяем все EventUserEntity и вытаскиваем все,
        // на которые записалось уже больше 2-ух человек
    }
}
