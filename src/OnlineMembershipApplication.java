import controller.OnlineMembershipController;
import utils.ThreadedDataObjectServer;

public class OnlineMembershipApplication {

    public static void main(String[] args) {
        OnlineMembershipController.main(args);
        ThreadedDataObjectServer.main(args);
    }

}
