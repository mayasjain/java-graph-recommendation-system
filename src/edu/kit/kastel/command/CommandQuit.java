/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.command;

/**
 * Represents the {@code quit} command to terminate the application as specified.
 * 
 * @author Programmieren-Team
 */
public class CommandQuit extends Command {
    
    private final Communication communication;

    /**
     * Creates a new instance.
     * @param communication the communication to terminate eventually
     */
    public CommandQuit(Communication communication) {
        this.communication = communication;
    }

    @Override
    public boolean execute(String command, String[] arguments) {
        communication.stop();
        return true;
    }
}
