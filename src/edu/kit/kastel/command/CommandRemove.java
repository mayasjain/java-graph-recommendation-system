package edu.kit.kastel.command;

import edu.kit.kastel.model.Edge;
import edu.kit.kastel.model.Graph;

public class CommandRemove extends Command {
    private static final int EXPECTED_ARGUMENTS = 3;
    private final Graph graph;

    public CommandRemove(Graph graph) {
        super(EXPECTED_ARGUMENTS);
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

        if (arguments.length != EXPECTED_ARGUMENTS) {
            return false;
        }
        String subject = arguments[0].trim();
        String predicate = arguments[1].trim();
        String object = arguments[2].trim();

        ValidateUserInput validator = new ValidateUserInput(graph);
        Edge edge = validator.validateRelationship(subject, predicate, object);

        if (edge == null) {
            return false;
        }
        this.graph.removeEdge(edge);
        return true;
    }
}
