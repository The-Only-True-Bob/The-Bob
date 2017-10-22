package com.github.the_only_true_bob.the_bob.matcher.criterias;

import java.util.function.Supplier;

public interface CriteriaValue extends Supplier<Integer> {
    default boolean is() {
        return get() != 0;
    }
}
