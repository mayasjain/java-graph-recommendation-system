import edu.kit.kastel.command.Communication;

public class Application {
    public static void main(String[] args) {
        Communication communication = new Communication();
        communication.listen();
    }
}
