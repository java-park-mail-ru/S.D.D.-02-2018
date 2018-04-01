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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@Transactional
public class UserInfoTest {

    @Autowired
    private MockMvc mock;
    private static Faker faker;
    private static final String PATH_API_URL = "/api/user/";
    private static String userName;
    private static String userEmail;


    @BeforeClass
    public static void init() {
        faker = new Faker();
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
        String userPassword = faker.internet().password();
        createUser(userName, userEmail, userPassword);
    }

    @Test
    public void getInfoOk() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "info")
                        .sessionAttr("nickname", userName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.current_user.nickname", is(userName)))
                .andExpect(jsonPath("$.data.current_user.email", is(userEmail)));
    }

    @Test
    public void getInfoForbidden() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                get(PATH_API_URL + "info")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.data", new MapMatcher(new LinkedHashMap<>())))
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void getInfoAboutOtherOk() throws Exception {
        final String otherUserName = faker.name().username();
        final String otherUserPassword = faker.internet().password();
        final String otherUserEmail = faker.internet().emailAddress();

        createUser(otherUserName, otherUserEmail, otherUserPassword);

        this.mock.perform(
                get(PATH_API_URL + "info/" + userName)
                        .sessionAttr("nickname", otherUserName))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.nickname", is(userName)))
                .andExpect(jsonPath("$.data.user.email", is(userEmail)));
    }

    @Test
    public void getInfoAboutOtherUnauthorized() throws Exception {
        final LinkedHashMap<String, String> err = new LinkedHashMap<>();
        err.put("general", "You are not authorized, please do it)");
        this.mock.perform(
                get(PATH_API_URL + "info/" + userName)
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors", new MapMatcher(err)));
    }

    @Test
    public void getUserCount() throws Exception {
        this.mock.perform(
                get(PATH_API_URL + "get_users_count")
                        .locale(Locale.US))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count_users", greaterThan(0)));
    }
}
