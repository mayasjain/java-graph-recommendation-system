package edu.kit.kastel.command;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.Product;
import edu.kit.kastel.model.Strategy;
import edu.kit.kastel.model.recursiveParser.RecommendationParser;
import edu.kit.kastel.model.recursiveParser.Term;

import java.util.List;

public class CommandRecommend extends Command {
    private static final int MIN_EXPECTED_ARGUMENTS = 1;
    private final Graph graph;
    private final RecommendationParser parser;

    public CommandRecommend(Graph graph) {
        super(MIN_EXPECTED_ARGUMENTS);
        this.graph = graph;
        this.parser = new RecommendationParser(graph);
    }

    @Override
    public boolean matchesNumberOfArguments(int numberOfArguments) {
        return numberOfArguments >= MIN_EXPECTED_ARGUMENTS;
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
        try {
            // Handle both simple and complex recommendation commands
            if (arguments.length >= 2 && !arguments[0].contains("UNION") && !arguments[0].contains("INTERSECTION")) {
                // Simple case: "recommend S1 105"
                String strategy = arguments[0];
                int productId = Integer.parseInt(arguments[1]);

                // Use existing logic for simple recommendations
                Product product = this.graph.getProductById(productId);
                if (product != null) {
                    List<Product> recommendations = graph.getRecommendation(Strategy.valueOf(strategy), product);
                    printRecommendations(recommendations);
                } else {
                    System.out.println("Error: Product with ID " + productId + " not found");
                    return false;
                }
            } else {
                // Complex case: handle expressions with UNION and INTERSECTION
                // Reconstruct the full command string
                StringBuilder fullCommand = new StringBuilder("recommend");
                for (String arg : arguments) {
                    fullCommand.append(" ").append(arg);
                }

                // Parse and evaluate the expression
                Term term = parser.parse(fullCommand.toString());
                List<Product> recommendations = term.evaluate();
                printRecommendations(recommendations);
            }

            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error: Invalid recommendation command");
            return false;
        }
    }

    /**
     * Prints the recommendations in the required format.
     *
     * @param recommendations the list of recommended products
     */
    private void printRecommendations(List<Product> recommendations) {
        if (recommendations.isEmpty()) {
            System.out.println();
            return;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < recommendations.size(); i++) {
            output.append(recommendations.get(i).toString());
            if (i < recommendations.size() - 1) {
                output.append(" ");
            }
        }
        System.out.println(output);
    }
}
