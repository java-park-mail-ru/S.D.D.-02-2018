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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class AuthenticateTest {
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


    @BeforeClass
    public static void init() {
        faker = new Faker();
        builderSignup = new TestRequestBuilder();
        builderSignup.init("nickname", "email", "password", "passwordCheck");
        builderSignin = new TestRequestBuilder();
        builderSignin.init("nickname", "password");
    }


    public void createUser() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        this.mock.perform(
                post("/api/user/signup")
                        .contentType("application/json").locale(Locale.US)
                        .content(TestRequestBuilder.getJsonRequestForSignUp(userName, userEmail,
                                userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.errors", new MapMatcher(err)));//assertThat("$.size()", )).

    }

    @Before
    public void setUp() throws Exception {
        userName = faker.name().username();
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password();
        createUser();
    }

    @Test
    public void signUpOk() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        final String password = faker.internet().password();

        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(faker.name().username(),
                                faker.internet().emailAddress(), password, password)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)))
                .andExpect(jsonPath("$.data.sessionId", not(nullValue())));
    }

    @Test
    public void signUpConflictUserName() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname already taken!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail,
                                userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }


    @Test
    public void signUpConflictUserEmail() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("email", "Email already taken!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(faker.name().username(),
                                userEmail, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signUpNullUserName() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(null, userEmail, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signUpNullUserEmail() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("email", "Email field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, null, userPassword, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }


    @Test
    public void signUpNullUserPassword() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("password", "Password field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, null, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signUpNullUserPasswordCheck() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("passwordCheck", "Second password field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, userPassword, null)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signUpNullUserPasswordsNotMatch() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("passwordCheck", "Passwords doesn't match!");
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignup.getJsonRequest(userName, userEmail, userPassword,
                                faker.internet().password())))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signUpIncorrectDocumentType() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNUP)
                        .contentType("text/html"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void signInOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sessionId", not(nullValue())));
    }

    @Test
    public void signInNameInvalid() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "Invalid authorize data, try again!");
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(faker.name().username(), userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }


    @Test
    public void signInPasswordInvalid() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "Invalid authorize data, try again!");
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, faker.internet().password())))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signInNameIncorrect() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("nickname", "Nickname field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest("", userPassword)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signInPaswordIncorrect() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("password", "Password field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(userName, "")))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void signInPaswordAndNameIncorrect() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("password", "Password field is empty!");
        err.put("nickname", "Nickname field is empty!");
        this.mock.perform(
                post(PATH_URL_SIGNIN)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .content(builderSignin.getJsonRequest(null, null)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }


    @Test
    public void signOutOk() throws Exception {
        this.mock.perform(
                post(PATH_URL_SIGNOUT)
                        .contentType("application/json")
                        .locale(Locale.US)
                        .sessionAttr("nickname", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void signOutError() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                post(PATH_URL_SIGNOUT)
                        .contentType("application/json")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }
}
