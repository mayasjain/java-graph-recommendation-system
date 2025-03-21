package edu.kit.kastel.command;

import edu.kit.kastel.model.Category;
import edu.kit.kastel.model.Edge;
import edu.kit.kastel.model.Graph;

import java.util.List;

public class CommandExport extends Command {

    private final Graph graph;

    public CommandExport(Graph graph) {
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
        System.out.println("digraph {");

        List<Edge> sortedEdges = graph.getSortedEdges();
        for (Edge edge : sortedEdges) {
            System.out.println(edge.getStringForExport());
        }

        List<Category> sortedCategories = graph.getAllCategories();
        for (Category category : sortedCategories) {
            System.out.println(category.getExportString());
        }

        System.out.println("}");
        return true;
    }
 }

