package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.matcher.UserMatch;
import com.github.the_only_true_bob.the_bob.vk.User;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public interface FriendsCriteria extends MatchingCriteria {
    @Override
    default CriteriaValue calc(final UserMatch.MatchingParameterObject parameterObject) {
        final User left = parameterObject.getPair().left();
        final User right = parameterObject.getPair().right();

        final List<User> friends1 = new ArrayList<>(left.friends());
        final List<User> friends2 = new ArrayList<>(right.friends());

        friends1.retainAll(friends2);

        return new CriteriaValue() {
            @Override
            public Integer get() {
                return friends1.size() * points();
            }

            @Override
            public String toString() {
                final StringJoiner sj =
                        new StringJoiner(", ",
                                String.format("у вас %d общих друзей:%n", friends1.size()),
                                "");
                friends1.forEach(user -> sj.add(
                        String.format("%s %s", user.firstName().get(), user.lastName().get())));
                return sj.toString();
            }
        };
    }
}
