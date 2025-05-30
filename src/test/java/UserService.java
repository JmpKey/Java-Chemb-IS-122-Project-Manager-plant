import org.example.plant.protocol.Enigma;
import org.example.plant.realization.AppLIPC;
import org.example.plant.realization.PasswordManager;

public class UserService {
    private AppLIPC application;
    private boolean loginFlag;
    private String ePass;

    public void initUserData(String user, String pass) {
        application = (AppLIPC) AppLIPC.getInstance();
        application.setUsnameG(user);
        application.setUspassG(pass);
        application.initUDB();
        Enigma dePass = PasswordManager.getInstance();
        try {
            ePass = dePass.decryptPassword(application.getDb().getPasswById(application.getDb().getUserIdByName(user)), user, pass);
            loginFlag = true;
        } catch (Exception e) {
            loginFlag = false;
            throw new RuntimeException(e);
        }
    }

    public boolean isLoginFlag() {
        return loginFlag;
    }
}
