package fr.esgi.color_run.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public final class PasswordUtil {

    private static final int COST = 12;     // 10-12 = bon compromis 2025

    private PasswordUtil() {}

    /** Hash BCrypt ($2a$…) */
    public static String hash(String plain) {
        return BCrypt.withDefaults()
                .hashToString(COST, plain.toCharArray());
    }

    /** Vérifie plain versus hash stocké */
    public static boolean verify(String plain, String hash) {
        return BCrypt.verifyer()
                .verify(plain.toCharArray(), hash)
                .verified;
    }
}
