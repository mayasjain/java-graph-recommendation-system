package edu.kit.kastel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private final Set<Node> nodes;
    private final List<Edge> edges;

    public Graph() {
        this.nodes = new HashSet<>();
        this.edges = new ArrayList<>();
    }


    public List<Node> getSortedNodes() {
        List<Node> sortedNodes = new ArrayList<>(getAllProducts());
        sortedNodes.addAll(getAllCategories());

        return sortedNodes;
    }

    public List<Edge> getSortedEdges() {
        List<Edge> sortedEdges = new ArrayList<>(edges);

        sortedEdges.sort((e1, e2) -> {
            // First, compare source node names
            int sourceNameComparison = compareNodeNames(e1.getSource(), e2.getSource());
            if (sourceNameComparison != 0) {
                return sourceNameComparison;
            }

            // If source names are the same, compare target node names
            int targetNameComparison = compareNodeNames(e1.getTarget(), e2.getTarget());
            if (targetNameComparison != 0) {
                return targetNameComparison;
            }

            // If target names are the same, compare predicate
            return e1.getRelation().toString().compareTo(e2.getRelation().toString());
        });

        return sortedEdges;
    }

    public boolean hasEdge(Edge edge) {
        final String relationString = edge.getRelation().toString().trim();
        return edges.stream().anyMatch(currentEdge ->
                currentEdge.getSource().equals(edge.getSource())
                        && currentEdge.getTarget().equals(edge.getTarget())
                        && currentEdge.getRelation().toString().equalsIgnoreCase(relationString));
    }

    public void addEdge(Edge edge) {
        this.nodes.add(edge.getSource());
        this.nodes.add(edge.getTarget());


        if (!hasEdge(edge)) {
            edges.add(edge);
        }

        RelationType inverseRelation = edge.getRelation().getInverse();

        Edge inverseEdge = new Edge(edge.getTarget(), edge.getSource(), inverseRelation);

        if (!hasEdge(inverseEdge)) {
            edges.add(inverseEdge);
        }
    }

    public void removeEdge(Edge edge) {
        this.removeSingleEdge(edge);

        RelationType inverseRelation = edge.getRelation().getInverse();

        Edge inverseEdge = new Edge(edge.getTarget(), edge.getSource(), inverseRelation);
        this.removeSingleEdge(inverseEdge);


        removeNodeWithNoEdges(edge.getSource());
        removeNodeWithNoEdges(edge.getTarget());


    }

    public Node getNodeByName(String name) {
        if (name == null) {
            return null;
        }
        String lowercaseName = name.toLowerCase();
        for (Node node : nodes) {
            if (node.getName().equals(lowercaseName)) {
                return node;
            }
        }
        return null;
    }

    public Product getProductByNameAndId(String name, int id) {
        if (name == null) {
            return null;
        }
        String lowercaseName = name.toLowerCase();
        for (Node node : nodes) {
            if (node.isProduct() && node.getName().equals(lowercaseName)) {
                Product product = (Product) node;
                if (product.getId() == id) {
                    return product;
                }
            }
        }
        return null;
    }

    public Product getProductById(int id) {
        if (id < 0) {
            return null;
        }
        for (Node node : this.nodes) {
            if (node.isProduct()) {
                Product product = (Product) node;
                if (product.getId() == id) {
                    return product;
                }
            }
        }
        return null;
    }

    public List<Node> getNeighbors(Node node, RelationType relation) {
        if (node == null || relation == null) {
            return new ArrayList<>();
        }

        List<Node> neighbors = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.isConnectingFrom(node, relation)) {
                neighbors.add(edge.getTarget());
            }
        }

        return neighbors;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        for (Node node : nodes) {
            if (!node.isProduct()) {
                categories.add((Category) node);
            }
        }
        categories.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return categories;
    }

    public List<Product> getRecommendation(Strategy strategy, Product product) {
        Recommendation recommender = new Recommendation(this);
        return recommender.getRecommendation(strategy, product);
    }

    private List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isProduct()) {
                products.add((Product) node);
            }
        }
        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        return products;
    }



    private void removeSingleEdge(Edge edge) {
        final String relationString = edge.getRelation().toString();

        edges.removeIf(e -> e.getSource().equals(edge.getSource())
                && e.getTarget().equals(edge.getTarget())
                && e.getRelation().toString().equalsIgnoreCase(relationString));
    }

    private boolean hasAnyEdges(Node node) {
        return edges.stream().anyMatch(edge -> edge.getSource().equals(node) || edge.getTarget().equals(node));
    }


    private int compareNodeNames(Node node1, Node node2) {
        String name1 = getNodeSortName(node1);
        String name2 = getNodeSortName(node2);
        return name1.compareToIgnoreCase(name2);
    }

    private String getNodeSortName(Node node) {
        if (node.isProduct()) {
            return ((Product) node).toString();
        }
        return node.getName();
    }

    private void removeNodeWithNoEdges(Node node) {
        if (!this.hasAnyEdges(node)) {
            nodes.remove(node);
        }
    }

}
