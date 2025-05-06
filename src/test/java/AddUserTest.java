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

        // Замените реальные зависимости на моки
        DataBase.setInstance(dbMock); // Предположим, что у вас есть метод для установки экземпляра базы данных
        PasswordManager.setInstance(enigmaMock); // Предположим, что у вас есть метод для установки менеджера паролей
    }

    @Test
    public void testCreateNewUser() throws Exception {
        String userName = "testUser";
        String pass = "password";
        String eMail = "test@example.com";
        String ePass = "securePassword";

        // Настройка поведения мока
        when(enigmaMock.encryptPassword(ePass, userName, pass)).thenReturn("encryptedPassword");

        // Вызов метода
        addUser.createNewUser(userName, pass, eMail, ePass);

        // Проверка взаимодействия с моками
        verify(dbMock).systemDB(false);
        verify(dbMock).createNewUser(userName, pass, eMail, "encryptedPassword");
    }

    @Test
    public void testCreateNewUserThrowsException() {
        String userName = "testUser";
        String pass = "password";
        String eMail = "test@example.com";
        String ePass = "securePassword";

        // Настройка поведения мока для выбрасывания исключения
        try {
            when(enigmaMock.encryptPassword(ePass, userName, pass)).thenThrow(new RuntimeException("Encryption error"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Проверка на выброс исключения
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            addUser.createNewUser(userName, pass, eMail, ePass);
        });

        // Проверка сообщения об ошибке
        assertEquals("Encryption error", thrown.getCause().getMessage());
    }
}
