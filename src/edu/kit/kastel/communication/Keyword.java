/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.communication;

/**
 * The command keywords used to determine a command.
 * 
 * @author Programmieren-Team
 */
public enum Keyword {
    LOAD_DATABASE("load database"),

    ADD("add"),

    REMOVE("remove"),

    NODES("nodes"),

    EDGES("edges"),

    RECOMMEND("recommend"),

    EXPORT("export"),

    QUIT("quit");

    private final String regex;

    Keyword(String regex) {
        this.regex = regex;
    }

    /**
     * Returns whether the input matches this keyword.
     * @param input the provided input to check
     * @return whether the input matches this keyword
     */
    public boolean matches(String input) {
        return input.matches(regex);
    }
}
