import org.example.plant.protocol.*;
import org.example.plant.realization.AddUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.plant.realization.DataBase;
import org.example.plant.realization.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddUserTest {
    private AddUser addUser;
    private DbCall dbMock;
    private Enigma enigmaMock;

    @BeforeEach
    public void setUp() {
        addUser = (AddUser) AddUser.getInstance();
        dbMock = mock(DbCall.class);
        enigmaMock = mock(Enigma.class);

        DataBase.setInstance(dbMock);
        PasswordManager.setInstance(enigmaMock);
    }

    @Test
    public void testCreateNewUser() throws Exception {
        String userName = "testUser";
        String pass = "password";
        String eMail = "test@example.com";
        String ePass = "securePassword";

        when(enigmaMock.encryptPassword(ePass, userName, pass)).thenReturn("encryptedPassword");

        addUser.createNewUser(userName, pass, eMail, ePass);

        verify(dbMock).systemDB(false);
        verify(dbMock).createNewUser(userName, pass, eMail, "encryptedPassword");
    }

    @Test
    public void testCreateNewUserThrowsException() {
        String userName = "testUser";
        String pass = "password";
        String eMail = "test@example.com";
        String ePass = "securePassword";

        // Configuring mock behavior to throw an exception
        try {
            when(enigmaMock.encryptPassword(ePass, userName, pass)).thenThrow(new RuntimeException("Encryption error"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Checking for throwing an exception
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            addUser.createNewUser(userName, pass, eMail, ePass);
        });

        assertEquals("Encryption error", thrown.getCause().getMessage());
    }
}
