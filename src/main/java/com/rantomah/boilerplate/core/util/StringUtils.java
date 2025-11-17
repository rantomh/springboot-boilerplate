package com.rantomah.boilerplate.core.util;

import java.security.SecureRandom;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateRandomCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0, 4) + "-" + uuid.substring(4, 8);
    }

    public static String normalize(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    public static String generateActivationCode(int length) {
        int bound = (int) Math.pow(10, length);
        int value = SECURE_RANDOM.nextInt(bound);
        String template = String.format("%%0%dd", length);
        return String.format(template, value);
    }
}
