package com.github.the_only_true_bob.the_bob.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Utils {
    public static String stringFromJson(final JsonObject object, final String property) {
        return Optional.ofNullable(requireNonNull(object).get(property))
                .map(JsonElement::getAsString)
                .orElse(null); // Because we make difference between no value and empty value
    }

    public static JsonArray arrayFromJson(final JsonObject attachment, final String property) {
        return Optional.ofNullable(attachment.get(property))
                .map(JsonElement::getAsJsonArray)
                .orElseGet(JsonArray::new);
    }
}
