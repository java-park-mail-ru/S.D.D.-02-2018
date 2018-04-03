package tests.api;

import com.colorit.backend.BackendApplication;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tests.api.common.TestRequestBuilder;

import javax.transaction.Transactional;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
class UpdateInfoTest {
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

    @BeforeAll
    static void init() {
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

    private void createUser(String uName, String uEmail, String uPassword) throws Exception {
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json").locale(Locale.US)
                        .content(TestRequestBuilder.getJsonRequestForSignUp(uName, uEmail,
                                uPassword, uPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @BeforeEach
    void setUp() throws Exception {
        userName = faker.name().username();
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password();
        createUser(userName, userEmail, userPassword);
    }

    @Test
    void updateNickOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void updateNickConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(userName))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.nickname").value("Nickname already taken!"));
    }

    @Test
    void updateNickIncorrect() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.nickname").value("Nickname field is empty!"));
    }

    @Test
    void updateNotFoundUser() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .sessionAttr("nickname", "aaa")
                        .locale(Locale.US))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.general").value("Forbidden, please authorize!"));
    }

    @Test
    void updateNickUnauthorized() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_nickname")
                        .contentType("application/json")
                        .content(builderUpdateNick.getJsonRequest(faker.name().username()))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }

    @Test
    void updateEmailOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(faker.internet().emailAddress()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void updateEmailConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(userEmail))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.email").value("Email already taken!"));
    }

    @Test
    void updateEmailIncorrect() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest("fff@sfj."))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").value("Email is invalid!"));
    }

    @Test
    void updateEmailUnauthorized() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_email")
                        .contentType("application/json")
                        .content(builderUpdateEmail.getJsonRequest(faker.internet().emailAddress()))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }

    @Test
    void updatePasswordOk() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void updatePasswordNoMatch() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, faker.internet().password(),
                                faker.internet().password()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.passwordCheck").value("Passwords doesn't match!"));
    }

    @Test
    void updatePasswordIncorrectOld() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(faker.internet().password(), newPassword,
                                newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.general").value("Incorrect old password, please try again!"));
    }

    @Test
    void updatePasswordUnauthorized() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update_password")
                        .contentType("application/json")
                        .content(builderUpdatePassword.getJsonRequest(userPassword, newPassword,
                                newPassword))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }

    @Test
    void updateAllEmptyForm() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null, null, null, "", ""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.general").value("Form is empty!"));
    }

    @Test
    void updateAllFieldsOk() throws Exception {
        final String newPassword = faker.internet().password();

        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void updateAllFieldsConflict() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(userName,
                                faker.internet().emailAddress(), otherUserPassword, newPassword, newPassword))
                        .sessionAttr("nickname", otherUserName)
                        .locale(Locale.US))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.nickname").value("Nickname already taken!"));
    }

    @Test
    void updateAllIncorrectEmialAndPassword() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                "sdfd@esf.", userPassword, "", ""))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").value("Password field is empty!"))
                .andExpect(jsonPath("$.errors.passwordCheck").value("Second password field is empty!"))
                .andExpect(jsonPath("$.errors.email").value("Email is invalid!"));

    }


    @Test
    void updateAllPasswordNotMach() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), userPassword, faker.internet().password(),
                                faker.internet().password()))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.passwordCheck").value("Passwords doesn't match!"));
    }

    @Test
    void updatePasswordAndEmail() throws Exception {
        final String newPassword = faker.internet().password();
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                faker.internet().emailAddress(), userPassword, newPassword, newPassword))
                        .sessionAttr("nickname", userName)
                        .locale(Locale.US))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    void updateAllUnauthrized() throws Exception {
        this.mock.perform(
                post(PATH_URL_API + "update")
                        .contentType("application/json")
                        .content(builderUpdateAll.getJsonRequest(null,
                                faker.internet().emailAddress(), "", "", ""))
                        .locale(Locale.US))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }
}
