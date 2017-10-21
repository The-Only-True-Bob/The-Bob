package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.matcher.UserMatch;
import com.github.the_only_true_bob.the_bob.vk.User;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface MusicCriteria extends MatchingCriteria {
    @Override
    default int calc(final UserMatch.MatchingParameterObject parameterObject) {
        final User left = parameterObject.getPair().left();
        final User right = parameterObject.getPair().right();

        final long sameMusicBandsCount = left.music().stream()
                .filter(right.music()::contains)
                .count();

        return (int) sameMusicBandsCount * points();
    }
}
