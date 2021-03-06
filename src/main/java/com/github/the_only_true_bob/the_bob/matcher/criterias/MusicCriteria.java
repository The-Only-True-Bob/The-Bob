package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.matcher.UserMatch;
import com.github.the_only_true_bob.the_bob.vk.User;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public interface MusicCriteria extends MatchingCriteria {
    @Override
    default CriteriaValue calc(final UserMatch.MatchingParameterObject parameterObject) {
        final User left = parameterObject.getPair().left();
        final User right = parameterObject.getPair().right();

        final List<String> musics1 = new ArrayList<>(left.music());
        final List<String> musics2 = new ArrayList<>(right.music());

        musics1.retainAll(musics2);

        return new CriteriaValue() {
            @Override
            public Integer get() {
                return musics1.size() * points();
            }

            @Override
            public String toString() {
                final StringJoiner sj = new StringJoiner(", ","Вы вместе слушаете ","");
                musics1.forEach(sj::add);
                return sj.toString();
            }
        };
    }
}
