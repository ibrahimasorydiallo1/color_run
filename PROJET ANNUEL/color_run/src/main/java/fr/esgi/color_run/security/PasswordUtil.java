package fr.esgi.color_run.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Utilitaires pour hachage / vérification de mots de passe.
 *
 * Dépendance Maven :
 * <dependency>
 *   <groupId>at.favre.lib</groupId>
 *   <artifactId>bcrypt</artifactId>
 *   <version>0.10.2</version>
 * </dependency>
 */
public final class PasswordUtil {

    // coût (work factor) : 12 = bon compromis temps / sécurité en 2025
    private static final int COST = 12;

    private PasswordUtil() { /* utilitaire */ }

    /** Renvoie un hash BCrypt (format $2a$COST$SALT+HASH). */
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults()
                .hashToString(COST, plainPassword.toCharArray());
    }

    /** Vérifie qu’un mot de passe correspond au hash stocké. */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        return BCrypt.verifyer()
                .verify(plainPassword.toCharArray(), storedHash)
                .verified;
    }
}
