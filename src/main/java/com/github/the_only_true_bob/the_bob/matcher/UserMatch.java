package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.Main;
import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class UserMatch {

    private final static AnnotationConfigApplicationContext context = Main.context;
    private final static DataService dataService = context.getBean("dataService", DataService.class);
    private int value;

    private UserMatch(final int value) {
        this.value = value;
    }

    static UserMatch from(final CommunicativePair pair) {
        final Optional<UserEntity> leftEntity = getLeftUserEntityByVkId(pair);
        final Optional<UserEntity> rightEntity = getRightUserEntityByVkId(pair);

        final int value = leftEntity.flatMap(
                left -> rightEntity
                        .map(right -> new MatchingParameterObject(left, right, pair))
                        .filter(UserMatch::isPairAgeValid)
                        .filter(UserMatch::isPairSexValide)
                        .map(UserMatch::getMatchValue))
                .orElse(0);
        return new UserMatch(value);
    }

    private static int getMatchValue(final MatchingParameterObject parameterObject) {
        // TODO: 21/10/17 sum all criterias
        return 0;
    }

    private static boolean isPairSexValide(final MatchingParameterObject parameterObject) {
        // TODO: 21/10/17 impl
        return false;
    }

    private static boolean isPairAgeValid(final MatchingParameterObject parameterObject) {
        // TODO: 21/10/17 impl
        return false;
    }

    private static Optional<UserEntity> getLeftUserEntityByVkId(final CommunicativePair pair) {
        return dataService.findUserByVkId(pair.left().vkId());
    }

    private static Optional<UserEntity> getRightUserEntityByVkId(final CommunicativePair pair) {
        return dataService.findUserByVkId(pair.right().vkId());
    }

    public boolean matches() {
        return value() == 0;
    }

    public int value() {
        return value;
    }

    private static class MatchingParameterObject {
        private final UserEntity left;
        private final UserEntity right;
        private final CommunicativePair pair;

        public MatchingParameterObject(final UserEntity left, final UserEntity right, final CommunicativePair pair) {
            this.left = left;
            this.right = right;
            this.pair = pair;
        }

        public UserEntity getLeft() {
            return left;
        }

        public UserEntity getRight() {
            return right;
        }

        public CommunicativePair getPair() {
            return pair;
        }
    }

}
