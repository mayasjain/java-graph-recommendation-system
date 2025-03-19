package edu.kit.kastel.communication;
import edu.kit.kastel.model.Graph;

public class CommandLoadDatabase extends Command {
    private static final int EXPECTED_ARGUMENTS = 1;
    private final Graph graph;

    public CommandLoadDatabase(Graph graph) {
        super(EXPECTED_ARGUMENTS);
        this.graph = graph;
    }

    @Override
    public boolean execute(String command, String[] arguments) {
        if (arguments.length != EXPECTED_ARGUMENTS) {
            return false;
        }

        String filePath = arguments[0].trim();
        return loadDatabase(filePath);
    }

    private boolean loadDatabase(String filePath) {
        // 1. Read the file
        // 2. For each line, parse and add to graph
        // 3. Return success/failure
        return false;
    }

    private boolean processLine(String line) {
        // Parse the line according to grammar
        // Extract subject, predicate, object
        // Create/get nodes
        // Validate relationship
        // Add edge to graph
        return false;
    }
}