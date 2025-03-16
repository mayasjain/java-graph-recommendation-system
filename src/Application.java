import edu.kit.kastel.communication.Communication;

public class Application {
    public static void main(String[] args) {
        Communication communication = new Communication();
        communication.listen();
    }
}
