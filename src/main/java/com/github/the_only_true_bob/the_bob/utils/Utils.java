package com.github.the_only_true_bob.the_bob.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Utils {
    public static final String stringFromJson(final JsonObject object, final String property) {
        return Optional.ofNullable(requireNonNull(object).get(property))
                       .map(JsonElement::getAsString)
                       .orElse("");
    }
}
