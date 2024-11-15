package com.maville.controller.services;

import java.text.Normalizer; // Important pour retirer les accents
import java.util.regex.Pattern;

public class TextUtil {
    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        return Pattern.compile("\\p{M}").matcher(normalized).replaceAll("");
    }
}
