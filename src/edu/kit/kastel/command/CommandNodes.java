package edu.kit.kastel.command;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.Node;

import java.util.List;

public class CommandNodes extends Command {
    private static final int EXPECTED_ARGUMENTS = 0;
    private final Graph graph;

    public CommandNodes(Graph graph) {
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

    // print out all the nodes in the alphabetical order in format productname : productid ␣ categoryname
    @Override
    public boolean execute(String command, String[] arguments) {

        List<Node> nodes = graph.getSortedNodes();

        if (nodes.isEmpty()) {
            System.out.println();
            return true;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            output.append(nodes.get(i).toString());
            if (i < nodes.size() - 1) {
                output.append(" ");
            }
        }
        System.out.println(output);
        return true;
    }
}