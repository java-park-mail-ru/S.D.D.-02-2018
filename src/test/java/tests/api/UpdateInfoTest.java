package tests.api;

import com.colorit.backend.BackendApplication;
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
import org.springframework.test.web.servlet.MockMvc;
import tests.api.common.MapMatcher;
import tests.api.common.TestRequestBuilder;

import javax.transaction.Transactional;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class UpdateInfoTest {
    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static TestRequestBuilder builderUpdateNick;
    private static TestRequestBuilder builderUpdateEmail;
    private static TestRequestBuilder builderUpdatePassword;
    private static TestRequestBuilder builderUpdateAll;
    private static final String PATH_URL_API = "/api/user/";
    private static String userName;
    private static String userEmail;
    private static String userPassword;

    @BeforeClass
    public static void init() {
        faker = new Faker();
        builderUpdateNick = new TestRequestBuilder();
        builderUpdateNick.init("nickname");
        builderUpdateEmail = new TestRequestBuilder();
        builderUpdateEmail.init("email");
        builderUpdatePassword = new TestRequestBuilder();
        builderUpdatePassword.init("oldPassword", "password", "passwordCheck");
        builderUpdateAll = new TestRequestBuilder();
        builderUpdateAll.init("nickname", "email", "oldPassword", "password", "passwordCheck");
    }

    public void createUser(String uName, String uEmail, String uPassword) throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json").locale(Locale.US)
                        .content(TestRequestBuilder.getJsonRequestForSignUp(uName, uEmail,
                                uPassword, uPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));

    }

    @Before
    public void setUp() throws Exception {
        userName = faker.name().username();
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password();
        createUser(userName, userEmail, userPassword);
    }

    @Test
    public void updateNickOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", new MapMatcher(new LinkedHashMap<>())));
    }

    @Test
    public void updateNickConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname already taken!");
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(userName))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateNickIncorrect() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname field is empty!");
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateNotFoundUser() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "Forbidden, please authorize!");
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .sessionAttr("nickname", "aaa")
                        .locale(Locale.US))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateNickUnauthorized() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateEmailOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(faker.internet().emailAddress()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", new MapMatcher(new LinkedHashMap<>())));
    }

    @Test
    public void updateEmailConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("email", "Email already taken!");
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(userEmail))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateEmailIncorrect() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("email", "Email is invalid!");
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest("fff@sfj."))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateEmailUnauthorized() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(faker.internet().emailAddress()))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updatePasswordOk() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", new MapMatcher(new LinkedHashMap<>())));
    }

    @Test
    public void updatePasswordNoMatch() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("passwordCheck", "Passwords doesn't match!");
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, faker.internet().password(),
                                faker.internet().password()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updatePasswordIncorrectOld() throws Exception {
        final String newPassword = faker.internet().password();
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "Incorrect old password, please try again!");
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(faker.internet().password(), newPassword,
                                newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updatePasswordUnauthorized() throws Exception {
        final String newPassword = faker.internet().password();
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, newPassword,
                                newPassword))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateAllEmptyForm() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "Form is empty!");
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null, null, null, "", ""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateAllFieldsOk() throws Exception {
        final String newPassword = faker.internet().password();

        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", new MapMatcher(new LinkedHashMap<>())));
    }

    @Test
    public void updateAllFieldsConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        final String newPassword = faker.internet().password();
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname already taken!");
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(userName,
                                faker.internet().emailAddress(), otherUserPassword, newPassword, newPassword))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updateAllIncorrectEmialAndPassword() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("password", "Password field is empty!");
        err.put("passwordCheck", "Second password field is empty!");
        err.put("email", "Email is invalid!");
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                "sdfd@esf.", userPassword, "", ""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }


    @Test
    public void updateAllPasswordNotMach() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("passwordCheck", "Passwords doesn't match!");
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), userPassword, faker.internet().password(),
                                faker.internet().password()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void updatePasswordAndEmail() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                faker.internet().emailAddress(), userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", new MapMatcher(new LinkedHashMap<>())));
    }

    @Test
    public void updateAllUnauthrized() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                faker.internet().emailAddress(), "", "", ""))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }
}
