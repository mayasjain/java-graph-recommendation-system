package edu.kit.kastel.command;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.Product;
import edu.kit.kastel.model.Recommendation;
import edu.kit.kastel.model.Strategy;

import java.util.List;

public class CommandRecommend extends Command {
    private static final int MIN_EXPECTED_ARGUMENTS = 1;
    private final Graph graph;


    public CommandRecommend(Graph graph) {
        super(MIN_EXPECTED_ARGUMENTS);
        this.graph = graph;

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
        if (arguments.length == 2) {
            String strategy = arguments[0];
            int productId = Integer.parseInt(arguments[1]);

            Product product = this.graph.getProductById(productId);
            Recommendation recommender = new Recommendation(this.graph);

            List<Product> recommendations = recommender.getRecommendation(Strategy.valueOf(strategy), product);
            String recommondationsString = "";
            for (Product recommendation : recommendations) {
                recommondationsString += recommendation.toString() + " ";
            }
            System.out.println(recommondationsString.trim());
        }
        return true;
    }
}
