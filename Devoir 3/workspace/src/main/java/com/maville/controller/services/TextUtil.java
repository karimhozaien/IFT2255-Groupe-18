package com.maville.controller.services;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Classe utilitaire pour les manipulations de texte.
 * Cette classe offre des méthodes statiques pour nettoyer et formater des chaînes de caractères.
 */
public class TextUtil {

    /**
     * Supprime les accents d'une chaîne de caractères.
     *
     * <p>Cette méthode utilise {@link java.text.Normalizer} pour décomposer les caractères accentués en leur
     * forme de base et les marqueurs d'accent. Ensuite, les accents (caractères de type {@code \p{M}}) sont
     * supprimés à l'aide d'une expression régulière.</p>
     *
     * @param input La chaîne de caractères à traiter.
     * @return Une nouvelle chaîne sans accents.
     * @throws NullPointerException si l'entrée est {@code null}.
     */
    public static String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return Pattern.compile("\\p{M}").matcher(normalized).replaceAll("");
    }
}