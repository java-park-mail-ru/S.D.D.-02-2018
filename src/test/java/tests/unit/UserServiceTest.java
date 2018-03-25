//package tests.unit;
//
//
//import com.colorit.backend.BackendApplication;
//import com.colorit.backend.entities.db.UserEntity;
//import com.colorit.backend.services.UserService;
//import com.colorit.backend.services.responses.UserServiceResponse;
//import com.colorit.backend.services.statuses.UserServiceStatus;
//import com.github.javafaker.Faker;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.validation.constraints.NotNull;
//import java.util.Locale;
//
//import static org.junit.Assert.assertSame;
//
//@SpringBootTest(classes = BackendApplication.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
//@Transactional
//public class aUserServiceTest {
//    @Autowired
//    private UserService userService;
//    private static Faker faker;
//    private static String userEmail;
//    private static String userName;
//    private static String userPassword;
//
//    @BeforeClass
//    public static void setUpFaker() {
//        faker = new Faker(new Locale("en-US"));
//    }
//
//    public void signUpUserOk(@NotNull String uName, @NotNull String uPassword, @NotNull String uEmail){
//        final UserEntity signUpModel = new UserEntity(uName, uEmail, uPassword);
//        final UserServiceResponse response = userService.createUser(signUpModel);
//        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
//    }
//
//    @Before
//    public void setUp() {
//        userEmail = faker.internet().emailAddress();
//        userName = faker.name().username();
//        userPassword = faker.internet().password();
//        signUpUserOk(userName, userPassword, userEmail);
//    }
//
//    @Test
//    public void signInUserOk() {
//        final UserEntity existingEntity = new UserEntity(userName, userPassword);
//        final UserServiceResponse response = userService.authenticateUser(existingEntity);
//        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
//    }
//
//    @Test
//    public void signInUserWithIncorrectPassword() {
//        final UserEntity existingEntity = new UserEntity(userName, faker.internet().password());
//        final UserServiceResponse response = userService.authenticateUser(existingEntity);
//        assertSame(response.getStatus(), UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
//    }
//
//    @Test
//    public void signInUserThatNotExist() {
//        final UserEntity userEntity = new UserEntity(faker.name().username(), userPassword);
//        final UserServiceResponse response = userService.authenticateUser(userEntity);
//        assertSame(response.getStatus(), UserServiceStatus.NAME_MATCH_ERROR_STATE);
//    }
//
//    @Test
//    public void signUpOk() {
//        final UserEntity userEntity  = new UserEntity(faker.name().username(), faker.internet().password(),
//                faker.internet().emailAddress());
//        final UserServiceResponse response = userService.createUser(userEntity);
//        assertSame(response.getStatus(), UserServiceStatus.CREATED_STATE);
//    }
//
//    @Test
//    public void signUpUserConflictUserName() {
//        final UserEntity userEntity = new UserEntity(userName, userPassword, userEmail);
//        final UserServiceResponse response = userService.createUser(userEntity);
//        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_NAME_STATE);
//    }
//
//    @Test
//    public void signUpUserConflictUserEmail() {
//        final UserEntity userEntity = new UserEntity(faker.name().username(), userPassword, userEmail);
//        final UserServiceResponse response = userService.createUser(userEntity);
//        assertSame(response.getStatus(), UserServiceStatus.CONFLICT_EMAIL_STATE);
//    }
//
//    @Test
//    public void checkUserThatExist() {
//        final UserServiceResponse response = userService.userExists(userName);
//        assertSame(response.getStatus(), UserServiceStatus.OK_STATE);
//    }
//
//    @Test
//    public void checkThatNotExist() {
//        final UserServiceResponse response = userService.userExists(faker.name().username());
//        assertSame(response.getStatus(), UserServiceStatus.NOT_FOUND_STATE);
//    }
//
////    @Test
////    public void changeUserEmailOk() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserEmail(faker.internet().emailAddress(),
////                userName);
////        assertSame(responseCode, UserManager.ResponseCode.OK);
////    }
////
////    @Test
////    public void changeUserEmailWithExistingEmail() {
////        final String otherUserName = faker.name().username();
////        final String otherUserPassword = faker.internet().password();
////        final String otherUseEmail = faker.internet().emailAddress();
////        signUpUserOk(otherUserName,otherUserPassword,otherUseEmail);
////
////        final UserManager.ResponseCode responseCode = userManager.changeUserEmail(userEmail,
////                otherUserName);
////        assertSame(responseCode, UserManager.ResponseCode.EMAIL_IS_TAKEN);
////    }
////
////    @SuppressWarnings("InstanceMethodNamingConvention")
////    @Test
////    public void changeUserEmailWithNotExistingUser() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserEmail(faker.internet().emailAddress(),
////                faker.name().username());
////        assertSame(responseCode, UserManager.ResponseCode.INCORRECT_LOGIN);
////    }
////
////    @Test
////    public void changeUserPasswordOk() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserEmail(faker.internet().password(),
////                userName);
////        assertSame(responseCode, UserManager.ResponseCode.OK);
////    }
////
////    @SuppressWarnings("InstanceMethodNamingConvention")
////    @Test
////    public void changeUserPasswordWithNotExistingUser() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserEmail(faker.internet().password(),
////                faker.name().username());
////        assertSame(responseCode, UserManager.ResponseCode.INCORRECT_LOGIN);
////    }
////
////    @Test
////    public void getUserByNameOk() {
////        final UserModel userModel = new UserModel();
////        final UserManager.ResponseCode responseCode = userManager.getUserByName(userName, userModel);
////        assertSame(responseCode, UserManager.ResponseCode.OK);
////        assertTrue(userModel.getUserName().equals(userName));
////        assertTrue(userModel.getUserEmail().equals(userEmail));
////    }
////
////    @Test
////    public void getUserByNameError() {
////        final UserModel userModel = new UserModel();
////        final UserManager.ResponseCode responseCode = userManager.getUserByName(faker.internet().emailAddress(),
////                userModel);
////        assertSame(responseCode, UserManager.ResponseCode.INCORRECT_LOGIN);
////    }
////
////    @Test
////    public void changeUserHighScoreOk() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserHighScore(userName, 100);
////        assertSame(responseCode, UserManager.ResponseCode.OK);
////    }
////
////    @Test
////    public void changeUserHighScoreError() {
////        final UserManager.ResponseCode responseCode = userManager.changeUserHighScore(faker.name().username(), 100);
////        assertSame(responseCode, UserManager.ResponseCode.INCORRECT_LOGIN);
////    }
//}
