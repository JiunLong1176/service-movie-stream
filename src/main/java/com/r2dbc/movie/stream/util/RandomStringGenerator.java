package com.r2dbc.movie.stream.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.SecureRandom;

public final class RandomStringGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Getter
    @AllArgsConstructor
    public enum Type {
        NUMERIC("numeric", "0123456789"),
        LOWERCASE_CHARACTER("lowercase-character","abcdefghijklmnopqrstuvwxyz"),
        UPPERCASE_CHARACTER("uppercase-character",  "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        private String name;
        private String chars;

        public static Type valueOfName(String name) {
            for (Type type: values()) {
                if (type.name.equals(name)) {
                    return type;
                }
            }
            return null;
        }
    }

    public static String get(int length, Type... types) {
        String string = "";
        for (Type type: types) {
            string += type.chars;
        }

        char[] chars = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(chars[random.nextInt(chars.length)]);
        }

        return stringBuilder.toString();
    }

}
