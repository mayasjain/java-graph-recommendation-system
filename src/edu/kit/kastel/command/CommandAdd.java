package edu.kit.kastel.command;
import edu.kit.kastel.model.*;

/**
 * Represents the {@code add} command to add node to graph.
 *
 */
public class CommandAdd extends Command {
    private static final int EXPECTED_ARGUMENTS = 3;
    private final Graph graph;

    /**
     * Creates a new instance.
     *
     * @param graph the graph to add to
     */
    public CommandAdd(Graph graph) {
        super(EXPECTED_ARGUMENTS);
        this.graph = graph;
    }

    @Override
    public boolean execute(String command, String[] arguments) {
        String subject = arguments[0].trim();
        String predicate = arguments[1].trim();
        String object = arguments[2].trim();

        ValidateUserInput validator = new ValidateUserInput(graph);
        Edge edge = validator.validateRelationship(subject, predicate, object);

        System.out.println(edge);


        if (edge == null) {
            return false;
        }

        this.graph.addEdge(edge);
        return true;
    }
}



