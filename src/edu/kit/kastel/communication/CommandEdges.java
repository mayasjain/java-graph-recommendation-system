package edu.kit.kastel.communication;

import edu.kit.kastel.model.Edge;
import edu.kit.kastel.model.Graph;

import java.util.List;

public class CommandEdges extends Command {

    private static final int EXPECTED_ARGUMENTS = 0;
    private final Graph graph;

    public CommandEdges(Graph graph) {
        super(CommandEdges.EXPECTED_ARGUMENTS);
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

        List<Edge> sortedEdges = graph.getSortedEdges();

        if (sortedEdges.isEmpty()) {
            System.out.println();
            return true;
        }

        for (Edge edge : sortedEdges) {

            String source = edge.getSource().toString();
            String predicate = edge.getRelation().toString();
            String target = edge.getTarget().toString();

            System.out.println(source + "-[" + predicate + "]->" + target);

        }
        return true;
    }

}
