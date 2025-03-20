package edu.kit.kastel.communication;

import edu.kit.kastel.model.Graph;

public class CommandNodes extends Command {
    private static final int EXPECTED_ARGUMENTS = 0;
    private final Graph graph;

    public CommandNodes(Graph graph) {
        this.graph = graph;
    }


    /**
     * Executes the command and returns whether the execution was successful.
     *
     * @param command   the command provided by the user
     * @param arguments the arguments of the command provided by the user
     * @return {@code true} if the execution was successful, {@code false} otherwise
     */
    @Override
    public boolean execute(String command, String[] arguments) {
        return false;
    }
}
