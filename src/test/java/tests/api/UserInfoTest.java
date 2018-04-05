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

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
class UserInfoTest {

    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static final String PATH_API_URL = "/api/user/";
    private static String userName;
    private static String userEmail;


    @BeforeAll
    static void init() {
        faker = new Faker();
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
        final String userPassword = faker.internet().password();
        createUser(userName, userEmail, userPassword);
    }

    @Test
    void getInfoOk() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "info")
                        .sessionAttr("nickname", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.current_user.nickname").value(userName))
                .andExpect(jsonPath("$.data.current_user.email").value(userEmail));
    }

    @Test
    void getInfoForbidden() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "info")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }

    @Test
    void getInfoAboutOtherOk() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        this.mock.perform(
                get(PATH_API_URL + "info/" + userName)
                        .sessionAttr("nickname", otherUserName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.nickname").value(userName))
                .andExpect(jsonPath("$.data.user.email").value(userEmail));
    }

    @Test
    void getInfoAboutOtherUnauthorized() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "info/" + userName)
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors.general").value("You are not authorized, please do it)"));
    }

    @Test
    void getUserCount() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "get_users_count")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count_users", greaterThan(0)));
    }
}
