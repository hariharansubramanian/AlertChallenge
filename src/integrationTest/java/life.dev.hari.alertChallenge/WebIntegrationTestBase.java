package life.dev.hari.alertChallenge;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by plank-hari.s on 7/23/2017.
 * Abstract base class for all REST API Controller test cases.
 * This class contains an instance of MockMvc and other resources needed for testing by all Controller subclasses.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlertChallengeApplication.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class WebIntegrationTestBase {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;
    protected JsonFactory jsonFactory;
    protected MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        this.objectMapper = new ObjectMapper();
        this.jsonFactory = new JsonFactory();
        this.mockHttpServletResponse = null;
    }

    @After
    public void reset() {
        this.mockMvc = null;
        this.objectMapper = null;
        this.jsonFactory = null;
    }

}
