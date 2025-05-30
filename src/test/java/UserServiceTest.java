import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.plant.protocol.Enigma;
import org.example.plant.realization.AppLIPC;
import org.example.plant.realization.DataBase;
import org.example.plant.realization.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private UserService userService;
    private AppLIPC mockAppLIPC;
    private PasswordManager mockPasswordManager;
    private Enigma mockEnigma;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        mockAppLIPC = mock(AppLIPC.class);
        mockPasswordManager = mock(PasswordManager.class);
        mockEnigma = mock(Enigma.class);
    }

    @Test
    public void testInitUserData_Success() throws Exception {
        String user = "tu";
        String pass = "123";

        when(mockAppLIPC.getDb()).thenReturn(mock(DataBase.class));
        when(mockAppLIPC.getDb().getUserIdByName(user)).thenReturn(1);
        when(mockAppLIPC.getDb().getPasswById(1)).thenReturn("LpOXZbgBPIeRCtdyp3Ig3g==");

        when(mockPasswordManager.decryptPassword("LpOXZbgBPIeRCtdyp3Ig3g==", user, pass)).thenReturn("decryptedPassword");

        // Calling the method under test
        userService.initUserData(user, pass);

        // checking the status after calling the method
        assertTrue(userService.isLoginFlag());
    }
}
