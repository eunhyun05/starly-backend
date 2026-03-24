package kr.starly.backend.common.util;

import kr.starly.backend.common.annotation.Id;

import java.security.SecureRandom;
import java.util.Arrays;

public class IdGenerator {

    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(Id annotation) {
        String random = RANDOM.ints(annotation.length(), 0, CHARS.length())
                .mapToObj(i -> String.valueOf(CHARS.charAt(i)))
                .collect(java.util.stream.Collectors.joining());

        return annotation.prefix().isEmpty() ? random : annotation.prefix() + "_" + random;
    }

    public static void fill(Object entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.get(entity) == null) {
                            field.set(entity, generate(field.getAnnotation(Id.class)));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}