package edu.kit.kastel.model.recursiveParser;

import edu.kit.kastel.model.Graph;
import edu.kit.kastel.model.Product;
import edu.kit.kastel.model.Recommendation;
import edu.kit.kastel.model.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a final term in the recommendation grammar (strategy + productId).
 */
public class FinalTerm implements Term {
    private final Strategy strategy;
    private final int productId;
    private final Graph graph;
    private final Recommendation recommender;

    /**
     * Creates a new final term.
     *
     * @param strategy  the recommendation strategy
     * @param productId the product ID
     * @param graph     the product graph
     */
    public FinalTerm(Strategy strategy, int productId, Graph graph) {
        this.strategy = strategy;
        this.productId = productId;
        this.graph = graph;
        this.recommender = new Recommendation(graph);
    }

    @Override
    public List<Product> evaluate() {
        Product product = graph.getProductById(productId);
        if (product == null) {
            return new ArrayList<>();
        }
        return recommender.getRecommendation(strategy, product);
    }

    @Override
    public String toString() {
        return strategy + " " + productId;
    }
}