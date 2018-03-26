package tests.unit;

import com.colorit.backend.BackendApplication;
import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.services.UserServiceJpa;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.entity.representations.UserEntityRepresentation;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Locale;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = BackendApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserServiceJpa userService;
    private static Faker faker;
    private static String userEmail;
    private static String userName;
    private static String userPassword;

    @BeforeClass
    public static void setUpFaker() {
        faker = new Faker(new Locale("en-US"));
    }

    public void signUpUserOk(@NotNull String uName, @NotNull String uPassword, @NotNull String uEmail) {
        final UserEntity signUpModel = new UserEntity(uName, uEmail, uPassword);
        final UserServiceResponse response = userService.createUser(signUpModel);
        assertSame(response.getStatus(), UserServiceStatus.CREATED_STATE);
    }

    @Before
    public void setUp() {
        userEmail = faker.internet().emailAddress();
        userName = faker.name().username();
        userPassword = faker.internet().password();
        signUpUserOk(userName, userPassword, userEmail);
    }

    @Test
    public void signInUserOk() {
        final UserEntity existingEntity = new UserEntity(userName, userPassword);
        final UserServiceResponse response = userService.authenticateUser(existingEntity);
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
    }

    @Test
    public void signInUserWithIncorrectPassword() {
        final UserEntity existingEntity = new UserEntity(userName, faker.internet().password());
        final UserServiceResponse response = userService.authenticateUser(existingEntity);
        assertSame(response.getStatus(), UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
    }

    @Test
    public void signInUserThatNotExist() {
        final UserEntity userEntity = new UserEntity(faker.name().username(), userPassword);
        final UserServiceResponse response = userService.authenticateUser(userEntity);
        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
    }

    @Test
    public void signUpOk() {
        final UserEntity userEntity = new UserEntity(faker.name().username(), faker.internet().emailAddress(),
                faker.internet().password());
        final UserServiceResponse response = userService.createUser(userEntity);
        assertSame(response.getStatus(), UserServiceStatus.CREATED_STATE);
    }

    @Test
    public void signUpUserConflictUserName() {
        final UserEntity userEntity = new UserEntity(userName, userEmail, userPassword);
        final UserServiceResponse response = userService.createUser(userEntity);
        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_NAME_STATE);
    }

    @Test
    public void signUpUserConflictUserEmail() {
        final UserEntity userEntity = new UserEntity(faker.name().username(), userEmail, userPassword);
        final UserServiceResponse response = userService.createUser(userEntity);
        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_EMAIL_STATE);
    }

    @Test
    public void checkUserThatExist() {
        final UserServiceResponse response = userService.userExists(userName);
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
    }

    @Test
    public void checkThatNotExist() {
        final UserServiceResponse response = userService.userExists(faker.name().username());
        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
    }

    @Test
    public void changeUserNicknameOk() {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUseEmail = faker.internet().emailAddress();
        signUpUserOk(otherUserName, otherUserPassword, otherUseEmail);

        final UserServiceResponse response = userService.updateNickname(otherUserName, faker.name().username());
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
    }

    @Test
    public void changeUserNicknameCoflict() {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUseEmail = faker.internet().emailAddress();
        signUpUserOk(otherUserName, otherUserPassword, otherUseEmail);

        final UserServiceResponse response = userService.updateNickname(otherUserName, userName);
        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_NAME_STATE);
    }

    @Test
    public void changeUserNicknameWithNotExistingUser() {
        final UserServiceResponse response = userService.updateEmail(faker.name().username(),
                faker.internet().emailAddress());
        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
    }

    @Test
    public void changeUserEmailConflict() {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUseEmail = faker.internet().emailAddress();
        signUpUserOk(otherUserName, otherUserPassword, otherUseEmail);

        Object ob = userService.getUser(userName).getData();
        final UserServiceResponse response = userService.updateEmail(otherUserName, userEmail);
        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_EMAIL_STATE);
    }

    @Test
    public void changeUserEmailWithNotExistingUser() {
        final UserServiceResponse response = userService.updateEmail(faker.name().username(),
                faker.internet().emailAddress());
        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
    }

    @Test
    public void changeUserEmailOk() {
        final UserServiceResponse response = userService.updateEmail(userName, faker.internet().emailAddress());
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
    }


    @SuppressWarnings("InstanceMethodNamingConvention")
    @Test
    public void changeUserPasswordWithNotExistingUser() {
        final UserServiceResponse response = userService.updatePassword(faker.name().username(),
                userPassword, faker.internet().password());
        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
    }

    @Test
    public void changeUserPasswordInvalid() {
        final UserServiceResponse response = userService.updatePassword(userName, faker.internet().password(),
                faker.internet().password());
        assertSame(response.getStatus(), UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
    }

    @Test
    public void getUserOk() {
        final UserServiceResponse response = userService.getUser(userName);
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
        UserEntityRepresentation ur = (UserEntityRepresentation) response.getData();
        assertEquals(ur.getNickname(), userName);
    }

    @Test
    public void getUserByNameError() {
        final UserServiceResponse response = userService.getUser(faker.name().username());
        assertSame(response.getStatus(), UserServiceStatus.NOT_FOUND_STATE);
    }

    @Test
    public void changeUserPasswordOk() {
        final UserServiceResponse response = userService.updatePassword(userName, userPassword,
                faker.internet().password());
        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
    }
}
