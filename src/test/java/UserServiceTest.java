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

        // Настройка статических методов через PowerMockito или аналогичный инструмент
        // Например, если используете PowerMockito:
        // PowerMockito.mockStatic(AppLIPC.class);
        // when(AppLIPC.getInstance()).thenReturn(mockAppLIPC);
        // PowerMockito.mockStatic(PasswordManager.class);
        // when(PasswordManager.getInstance()).thenReturn(mockPasswordManager);
    }

    @Test
    public void testInitUserData_Success() throws Exception {
        String user = "tu";
        String pass = "123";

        // Настройка поведения моков
        when(mockAppLIPC.getDb()).thenReturn(mock(DataBase.class));
        when(mockAppLIPC.getDb().getUserIdByName(user)).thenReturn(1);
        when(mockAppLIPC.getDb().getPasswById(1)).thenReturn("LpOXZbgBPIeRCtdyp3Ig3g==");

        when(mockPasswordManager.decryptPassword("LpOXZbgBPIeRCtdyp3Ig3g==", user, pass)).thenReturn("decryptedPassword");

        // Вызов тестируемого метода
        userService.initUserData(user, pass);

        // Проверка состояния после вызова метода
        assertTrue(userService.isLoginFlag()); // Предполагается, что у вас есть метод isLoginFlag()
    }
}
