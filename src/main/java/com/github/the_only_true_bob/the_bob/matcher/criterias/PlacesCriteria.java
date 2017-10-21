package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.matcher.UserMatch;
import com.github.the_only_true_bob.the_bob.vk.User;

public interface PlacesCriteria extends MatchingCriteria {
    @Override
    default int calc(final UserMatch.MatchingParameterObject parameterObject) {
        final User left = parameterObject.getPair().left();
        final User right = parameterObject.getPair().right();

        return left.homeTown()
                .flatMap(hometown1 -> right.homeTown()
                        .filter(hometown1::equalsIgnoreCase))
                .map(s -> points())
                .orElse(0);
    }
}
