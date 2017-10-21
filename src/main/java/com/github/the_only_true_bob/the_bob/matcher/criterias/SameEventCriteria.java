package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.matcher.UserMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public interface SameEventCriteria extends MatchingCriteria {
    static SameEventCriteria with(DataService dataService, int points) {
        return new SameEventCriteria() {
            @Override
            public int points() {
                return points;
            }

            @Override
            public CriteriaValue calc(final UserMatch.MatchingParameterObject parameterObject) {
                final UserEntity left = parameterObject.getLeft();
                final UserEntity right = parameterObject.getRight();

                final List<String> user1Events = getEventsNames(left);
                final List<String> user2Events = getEventsNames(right);

                user1Events.retainAll(user2Events);

                return new CriteriaValue() {
                    @Override
                    public Integer get() {
                        return user1Events.size() * points();
                    }

                    @Override
                    public String toString() {
                        final StringJoiner sj = new StringJoiner(", ", "Вы оба были на ", "");
                        user1Events.forEach(sj::add);
                        return sj.toString();
                    }
                };
            }

            private List<String> getEventsNames(final UserEntity left) {
                return new ArrayList<>(dataService.findEventsByUser(left)).stream()
                        .map(EventUserEntity::getEvent)
                        .map(EventEntity::getName)
                        .collect(Collectors.toList());
            }
        };
    }
}