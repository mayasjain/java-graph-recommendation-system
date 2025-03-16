/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.kastel.communication;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.RelationType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class Communication {

    private static final String COMMAND_SEPARATOR = " ";
    private static final String MESSAGE_ERROR = "ERROR";

    private final Map<Keyword, Command> commands = new EnumMap<>(Keyword.class);
    private boolean isRunning = false;

    private Graph graph;

    /**
     * Creates a new instance.
     */
    public Communication() {
        this.graph = new Graph();
        this.commands.put(Keyword.ADD, new CommandAdd(graph));

        this.commands.put(Keyword.QUIT, new CommandQuit(this));
    }

    /**
     * Listens on stdin for commands. 
     */
    public void listen() {
        this.isRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (this.isRunning && scanner.hasNextLine()) {
                handleLine(scanner.nextLine());
            }
        }
    }

    private void handleLine(String line) {
        String[] split = line.split(COMMAND_SEPARATOR, -1);
        String command = split[0];
        String[] arguments = Arrays.copyOfRange(split, 1, split.length);
        arguments = parseArgsWithPredicate(arguments);
        handleCommand(command, arguments);
    }

    private String[] parseArgsWithPredicate(String[] args) {
        RelationType[] relationTypes = RelationType.values();

        String relationship = "";
        int index = -1;

        for (int i = 0; i < args.length; i++) {
            int finalI = i;
            if (Arrays.stream(relationTypes).anyMatch(relationType -> relationType.toString().equals(args[finalI]))) {
                relationship = args[i];
                index = i;
            }
        }

        if (index == -1) {
            return args;
        }

        String subject = String.join("", Arrays.copyOfRange(args, 0, index));
        String object = String.join("", Arrays.copyOfRange(args, index + 1, args.length));

        return new String[]{subject, relationship, object};
    }

    private boolean handleCommand(String command, String[] arguments) {
        for (Map.Entry<Keyword, Command> entry : commands.entrySet()) {
            if (entry.getKey().matches(command)) {
                Command executor = entry.getValue();
                if (!executor.matchesNumberOfArguments(arguments.length)
                        || !executor.execute(command, arguments)) {
                    System.out.println(command);
                    System.out.println(Arrays.toString(arguments));
                    System.out.println(MESSAGE_ERROR);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Stops this instance from listening.
     * @see #listen()
     */
    public void stop() {
        this.isRunning = false;
    }
}
