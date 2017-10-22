package com.github.the_only_true_bob.the_bob.matcher;

import com.github.the_only_true_bob.the_bob.Main;
import com.github.the_only_true_bob.the_bob.dao.DataService;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.EventUserEntity;
import com.github.the_only_true_bob.the_bob.dao.entitites.UserEntity;
import com.github.the_only_true_bob.the_bob.matcher.criterias.*;
import com.github.the_only_true_bob.the_bob.vk.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserMatch {
    private final static AnnotationConfigApplicationContext context = Main.context;
    private final static DataService dataService = context.getBean("dataService", DataService.class);
    private final static MusicCriteria musicCriteria = () -> 10;
    private final static PlacesCriteria placesCriteria = () -> 100;
    private final static FriendsCriteria friendsCriteria = () -> 1000;
    private final static SameEventCriteria sameEventCriteria = SameEventCriteria.with(dataService, 500);
    private final CommunicativePair pair;
    private final List<CriteriaValue> criteriaValues;

    private UserMatch(final CommunicativePair pair, final List<CriteriaValue> criteriaValues) {
        this.pair = pair;
        this.criteriaValues = criteriaValues;
    }

    public List<CriteriaValue> criteriaValues() {
        return criteriaValues;
    }

    static UserMatch from(final CommunicativePair pair) {
        final Optional<UserEntity> leftEntity = getLeftUserEntityByVkId(pair);
        final Optional<UserEntity> rightEntity = getRightUserEntityByVkId(pair);

        final List<CriteriaValue> criteriaValues =
                leftEntity.flatMap(
                        left -> rightEntity
                                .map(right -> new MatchingParameterObject(left, right, pair))
                                .filter(UserMatch::isPairAgeValid)
                                .filter(UserMatch::isPairSexValid)
                                .map(UserMatch::getMatchCriteriaValues))
                        .orElseGet(Collections::emptyList);
        return new UserMatch(pair, criteriaValues);
    }

    private static List<CriteriaValue> getMatchCriteriaValues(final MatchingParameterObject parameterObject) {
        System.out.println("===========================================");
        System.out.println(" Match has return size: ");
        System.out.println("===========================================");
        return Arrays.asList(
                friendsCriteria.calc(parameterObject),
                musicCriteria.calc(parameterObject),
                placesCriteria.calc(parameterObject),
                sameEventCriteria.calc(parameterObject));
    }

    private static boolean isPairSexValid(final MatchingParameterObject parameterObject) {
        final User left = parameterObject.pair.left();
        final User right = parameterObject.pair.right();
        final UserEntity leftEntity = parameterObject.left;
        final UserEntity rightEntity = parameterObject.right;

        return left.sex()
                .flatMap(leftSex ->
                        right.sex()
                                .filter(rightSex -> isSexCapable(leftEntity, rightSex))
                                .filter(rightSex -> isSexCapable(rightEntity, leftSex)))
                .isPresent();
    }

    private static boolean isSexCapable(final UserEntity entity1, final String user2sex) {
        return entity1.getAcceptableSex().equals("3")
                || entity1.getAcceptableSex().equals(user2sex);
    }

    private static boolean isPairAgeValid(final MatchingParameterObject parameterObject) {
        final User left = parameterObject.pair.left();
        final User right = parameterObject.pair.right();
        final UserEntity leftEntity = parameterObject.left;
        final UserEntity rightEntity = parameterObject.right;

        return left.birthday()
                .flatMap(leftBirthday ->
                        right.birthday()
                                .filter(rightBirthday -> isBirthdayCapable(leftEntity, leftBirthday, rightBirthday))
                                .filter(rightBirthday -> isBirthdayCapable(rightEntity, rightBirthday, leftBirthday)))
                .isPresent();
    }

    private static boolean isBirthdayCapable(final UserEntity entity1, final String user1Birthday, final String user2Birthday) {
        final Optional<Integer> user1Year = parseYear(user1Birthday);
        final Optional<Integer> user2Year = parseYear(user2Birthday);
        return entity1.getAcceptableAgeDiff() == 0 ||
                user1Year
                        .flatMap(year1 -> user2Year
                                .filter(year2 ->
                                        year2 >= year1 - entity1.getAcceptableAgeDiff()
                                                && year2 <= year1 + entity1.getAcceptableAgeDiff()))
                        .isPresent();
    }

    private static Optional<Integer> parseYear(final String bday) {
        return parseBirthday(bday).map(LocalDate::getYear);
    }

    private static Optional<LocalDate> parseBirthday(final String bday) {
        try {
            return Optional.of(LocalDate.parse(bday, DateTimeFormatter.ofPattern("D.M.YYYY")));
        } catch (Exception e) {
            //ignore
            return Optional.empty();
        }
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
        return criteriaValues.stream()
                .mapToInt(Supplier::get)
                .sum();
    }

    public User notMe(final User user) {
        return user.vkId().equals(pair.left().vkId()) ? pair.right() : pair.left();
    }

    public static class MatchingParameterObject {
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
