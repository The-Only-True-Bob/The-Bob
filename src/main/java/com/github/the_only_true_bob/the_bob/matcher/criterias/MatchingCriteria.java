package com.github.the_only_true_bob.the_bob.matcher.criterias;

import com.github.the_only_true_bob.the_bob.matcher.UserMatch;

public interface MatchingCriteria {
    int points();
    CriteriaValue calc(final UserMatch.MatchingParameterObject parameterObject);
}
