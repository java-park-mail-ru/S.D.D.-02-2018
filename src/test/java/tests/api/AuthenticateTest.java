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
class AuthenticateTest {
    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static TestRequestBuilder builderSignup;
    private static TestRequestBuilder builderSignin;
    private static final String PATH_URL_SIGNUP = "/api/user/signup";
    private static final String PATH_URL_SIGNIN = "/api/user/signin";
    private static final String PATH_URL_SIGNOUT = "/api/user/signout";
    private static String userName;
    private static String userEmail;
    private static String userPassword;


    @BeforeAll
    static void init() {
        faker = new Faker();
        builderSignup = new TestRequestBuilder();
        builderSignup.init("nickname", "email", "password", "passwordCheck");
        builderSignin = new TestRequestBuilder();
        builderSignin.init("nickname", "password");
    }


    private void createUser() throws Exception {
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json").locale(Locale.US)
                        .content(TestRequestBuilder.getJsonRequestForSignUp(userName, userEmail,
                                userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty());

    }

    @BeforeEach
    void setUp() throws Exception {
        userName = faker.name().username();
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password();
        createUser();
    }

    @Test
    void signUpOk() throws Exception {
        final String password = faker.internet().password();

        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), password, password)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(jsonPath("$.data.sessionId").isNotEmpty());
    }

    @Test
    void signUpConflictUserName() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail,
                                userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.nickname")
                        .value("Nickname already taken!"));
    }


    @Test
    void signUpConflictUserEmail() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(faker.name().username(),
                                userEmail, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.email").value("Email already taken!"));
    }

    @Test
    void signUpNullUserName() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(null, userEmail, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.nickname").value("Nickname field is empty!"));
    }

    @Test
    void signUpNullUserEmail() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, null, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email").value("Email field is empty!"));
    }


    @Test
    void signUpNullUserPassword() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, null, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").value("Password field is empty!"));
    }

    @Test
    void signUpNullUserPasswordCheck() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, userPassword, null)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.passwordCheck")
                        .value("Second password field is empty!"));
    }

    @Test
    void signUpNullUserPasswordsNotMatch() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, userPassword,
                                faker.internet().password())))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.passwordCheck").value("Passwords doesn't match!"));
    }

    @Test
    void signUpIncorrectDocumentType() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("text/html"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void signInOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sessionId").isNotEmpty());
    }

    @Test
    void signInNameInvalid() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(faker.name().username(), userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.general")
                        .value("Invalid authorize data, try again!"));
    }


    @Test
    void signInPasswordInvalid() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, faker.internet().password())))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors.general")
                        .value("Invalid authorize data, try again!"));
    }

    @Test
    void signInNameIncorrect() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest("", userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.nickname").value("Nickname field is empty!"));
    }

    @Test
    void signInPaswordIncorrect() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, "")))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").value("Password field is empty!"));
    }

    @Test
    void signInPaswordAndNameIncorrect() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(null, null)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password").value("Password field is empty!"))
                .andExpect(jsonPath("$.errors.nickname").value("Nickname field is empty!"));
    }


    @Test
    void signOutOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNOUT)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .sessionAttr("nickname", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    void signOutError() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNOUT)
                        .contentType("application/json")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }
}
