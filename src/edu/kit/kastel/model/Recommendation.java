package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Recommendation {
    private final Graph graph;

    public Recommendation(Graph graph) {
        this.graph = graph;
    }

    public List<Product> getRecommendation(Strategy strategy, Product product) {
        List<Product> recommendations = new ArrayList<>();
        switch (strategy) {
            case S1:
                recommendations.addAll(this.getS1Recommendation(product));
                break;
            case S2:
                recommendations.addAll(this.getS2Recommendation(product));
                break;
            case S3:
                recommendations.addAll(this.getS3Recommendation(product));
                break;
            default:
        }
        return recommendations;
    }

    private List<Product> getS1Recommendation(Product product) {
        List<Product> recommendations = new ArrayList<>();
        List<Node> parentCategories = this.graph.getNeighbors(product, RelationType.CONTAINED_IN);

        for (Node node : parentCategories) {
            if (node.isProduct()) {
                continue;
            }
            List<Node> nodeRecommendations = this.graph.getNeighbors(node, RelationType.CONTAINS);
            nodeRecommendations.forEach(curNode -> {
                if (curNode.isProduct() && !curNode.equals(product)) {
                    recommendations.add((Product) curNode);
                }
            });
        }
        recommendations.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        return recommendations;
    }

    private List<Product> getS2Recommendation(Product product) {
        List<Product> recommendations = getAllNeighbors(product, RelationType.PREDECESSOR_OF);
        recommendations.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        return recommendations;
    }

    private List<Product> getS3Recommendation(Product product) {
        List<Product> recommendations = getAllNeighbors(product, RelationType.SUCCESSOR_OF);
        recommendations.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        return recommendations;
    }

    private List<Product> getAllNeighbors(Node node, RelationType type) {
        if (!(type.equals(RelationType.SUCCESSOR_OF) || type.equals(RelationType.PREDECESSOR_OF))) {
            return new ArrayList<>();
        }

        Set<Node> visited = new HashSet<>();

        List<Product> recommendations = new ArrayList<>();

        getRecursiveNeighbors(node, visited, recommendations, type);

        // Remove the reference node itself if it's in the results
        if (node.isProduct()) {
            recommendations.remove(node);
        }

        return recommendations;
    }

    private void getRecursiveNeighbors(Node currentNode, Set<Node> visited, List<Product> recommendations, RelationType type) {
        visited.add(currentNode);

        List<Node> neighbors = this.graph.getNeighbors(currentNode, type);

        for (Node neighbor : neighbors) {
            if (visited.contains(neighbor)) {
                continue;
            }

            if (neighbor.isProduct()) {
                recommendations.add((Product) neighbor);
            }

            getRecursiveNeighbors(neighbor, visited, recommendations, type);
        }
    }

}
