package org.github.volzock.utils;

public class Validators {
    public static boolean isNumber(String word) {
        return word.chars().allMatch(Character::isDigit);
    }

    public static boolean isHttpUrl(String url) {
        return url.matches("^http:\\/\\/(\\S|\\.)+(\\/\\S*)*$");
    }
}
