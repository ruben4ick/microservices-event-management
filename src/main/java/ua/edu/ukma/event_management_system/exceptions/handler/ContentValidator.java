package ua.edu.ukma.event_management_system.exceptions.handler;

import ua.edu.ukma.event_management_system.exceptions.IllegalNameException;

public class ContentValidator {

    private ContentValidator(){}

    private static final String FORBIDDEN_CHARACTERS = "[ыэёъ]";

    public static void validateContent(String content) throws IllegalNameException {
        if (content.matches(".*" + FORBIDDEN_CHARACTERS + ".*"))
            throw new IllegalNameException("Name contains forbidden characters: ы, э, ё, ъ.");
    }
}