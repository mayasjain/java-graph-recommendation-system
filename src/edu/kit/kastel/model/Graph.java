package edu.kit.kastel.model;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public boolean hasEdge(Edge edge) {
        final String relationString = edge.getRelation().toString().trim();
        return edges.stream().anyMatch(curEdge ->
                curEdge.getSource().equals(edge.getSource())
                && curEdge.getTarget().equals(edge.getTarget())
                && curEdge.getRelation().toString().equalsIgnoreCase(relationString));
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
        this.removeSingleEdge(edge.getSource(), edge.getTarget(), edge.getRelation());

        RelationType inverseRelation = edge.getRelation().getInverse();

        this.removeSingleEdge(edge.getTarget(), edge.getSource(), inverseRelation);

        removeNodeWithNoEdges(edge.getSource());
        removeNodeWithNoEdges(edge.getTarget());


    }

    private void removeSingleEdge(Node source, Node target, RelationType relation) {
        final String relationString = relation.toString();

        edges.removeIf(edge -> edge.getSource().equals(source)
                && edge.getTarget().equals(target)
                && edge.getRelation().toString().equalsIgnoreCase(relationString));
    }


    private boolean hasAnyEdges(Node node) {
        return edges.stream().anyMatch(edge -> edge.getSource().equals(node) || edge.getTarget().equals(node));
    }

    public void removeNodeWithNoEdges(Node node) {
        if (!this.hasAnyEdges(node)) {
            nodes.remove(node);
        }
    }

    //new method
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

    //new method
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

    private List<Product> getAllProducts() {
        List<Product> products = nodes.stream()
                .filter(node -> node.isProduct())
                .map(node -> (Product) node)
                .toList();
        products.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
        return products;
    }

    private List<Category> getAllCategories() {
        List<Category> categories = nodes.stream()
                .filter(node -> !node.isProduct())
                .map(node -> (Category) node)
                .toList();
        categories.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return categories;
    }

    public void sortNodes() {

    }

    public void sortEdges() {

    }

}
