/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.communication;


/**
 * The base class of a command.
 * 
 * @author Programmieren-Team
 */
public abstract class Command {

    private final int expectedArguments;

    /**
     * Creates a new command with expected number of arguments.
     * @param expectedArguments the expected number of arguments of this command
     */
    protected Command(int expectedArguments) {
        this.expectedArguments = expectedArguments;
    }

    /**
     * Creates a new command which doesn't expect arguments.
     */
    protected Command() {
        this.expectedArguments = 0;
    }

    /**
     * Returns whether the provided number of arguments matches the expected number.
     * @param numberOfArguments the number of arguments to check
     * @return whether the number of arguments is valid
     */
    public boolean matchesNumberOfArguments(int numberOfArguments) {
        return numberOfArguments == expectedArguments;
    }

    /**
     * Executes the command and returns whether the execution was successful.
     * @param command the command provided by the user
     * @param arguments the arguments of the command provided by the user
     * @return {@code true} if the execution was successful, {@code false} otherwise
     */
    public abstract boolean execute(String command, String[] arguments);
}
