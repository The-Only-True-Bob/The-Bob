package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.matcher.UserMatch;
import com.github.the_only_true_bob.the_bob.vk.User;

public interface PlacesCriteria extends MatchingCriteria {
    @Override
    default CriteriaValue calc(final UserMatch.MatchingParameterObject parameterObject) {
        final User left = parameterObject.getPair().left();
        final User right = parameterObject.getPair().right();

        final Integer value = left.homeTown()
                .flatMap(hometown1 -> right.homeTown()
                        .filter(hometown1::equalsIgnoreCase))
                .map(s -> points())
                .orElse(0);

        return new CriteriaValue() {
            @Override
            public Integer get() {
                return value;
            }

            @Override
            public String toString() {
                return String.format("Вас связывает город %s", left.homeTown());
            }
        };
    }
}
