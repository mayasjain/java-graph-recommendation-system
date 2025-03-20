package edu.kit.kastel.command;

import edu.kit.kastel.model.Category;
import edu.kit.kastel.model.Edge;
import edu.kit.kastel.model.Graph;

import java.util.List;

public class CommandExport extends Command {
    private static final int EXPECTED_ARGUMENTS = 0;

    private final Graph graph;

    public CommandExport(Graph graph) {
        super(CommandExport.EXPECTED_ARGUMENTS);
        this.graph = graph;
    }

    /**
     * Executes the command and returns whether the execution was successful.
     *
     * @param command   the command provided by the user
     * @param arguments the arguments of the command provided by the user
     * @return {@code true} if the execution was successful, {@code false} otherwise
     */

//    source -> target [label=predicate]
//    category [shape=box]

    @Override
    public boolean execute(String command, String[] arguments) {
        System.out.println("digraph {");

        List<Edge> sortedEdges = graph.getSortedEdges();
        for (Edge edge : sortedEdges) {

            String source = graph.getNodeNameWithoutId(edge.getSource());
            String target = graph.getNodeNameWithoutId(edge.getTarget());
            String predicate = edge.getRelation().toString().replace("-", "");
            System.out.println(source + " -> " + target + " [label=" + predicate + "]");
        }

        List<Category> sortedCategories = graph.getAllCategories();
        for (Category category : sortedCategories) {
            System.out.println(category.getName() + " [shape=box]");
        }

        System.out.println("}");
        return true;
    }
 }

