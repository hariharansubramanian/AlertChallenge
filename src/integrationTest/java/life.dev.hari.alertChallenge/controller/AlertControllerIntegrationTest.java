package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import life.dev.hari.alertChallenge.utilities.JsonDeserializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public class AlertControllerIntegrationTest extends WebIntegrationTestBase {

    public static final String ALERTS_URL_PATH = "/alerts";
    Logger logger = LoggerFactory.getLogger(AlertControllerIntegrationTest.class);

    @Autowired
    AlertRepository alertRepository;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @After
    public void clearData() {
        alertRepository.deleteAll();
    }

    /**
     * POST /alerts test cases
     */
    @Test
    public void postAlertMustReturnBadRequestForNullReferenceId() throws Exception {
        byte[] alertJsonWithNullReferenceId = getJsonData("testData/postAlert/postAlertMustReturnBadRequestForNullReferenceId.json");

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullReferenceId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDelay() throws Exception {
        byte[] alertJsonWithNullDelay = getJsonData("testData/postAlert/postAlertMustReturnBadRequestForNullDelay.json");

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullDelay))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDescription() throws Exception {
        byte[] alertJsonWithNullDescription = getJsonData("testData/postAlert/postAlertMustReturnBadRequestForNullDescription.json");

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullDescription))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestIfAlertAlreadyExists() throws Exception {
        byte[] validAlertJson = getJsonData("testData/postAlert/postAlertMustReturnCreatedForValidAlert.json");

        //Post a valid alert should succeed
        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validAlertJson))
                .andExpect(status().isCreated())
                .andReturn();

        //Re-posting same alert again should fail
        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validAlertJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnCreatedForAValidAlert() throws Exception {
        byte[] validAlertJson = getJsonData("testData/postAlert/postAlertMustReturnCreatedForValidAlert.json");

        //Post a valid alert should succeed
        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validAlertJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    /**
     * GET /alerts test cases
     */
    @Test
    public void getAlertsMustReturnEmptyArrayWhenNoAlertsPosted() throws Exception {
        //Get alerts should succeed and return
        mockHttpServletResponse = this.mockMvc
                .perform(get(ALERTS_URL_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        logger.debug("Returned list of alerts: " + mockHttpServletResponse.getContentAsString());

        List<Alert> alerts = deserializeAlertsJson(mockHttpServletResponse.getContentAsString());
        Assert.isTrue(alerts.size() == 0);
    }

    @Test
    public void getAlertsMustReturnOnlyAlertsWhichHaveCrossedTheDelayThreshold() throws Exception {
        //Get alerts should succeed and return
        //* List of alerts which have crossed the delay threshold

        //TODO: Register various alerts with different delays
        mockHttpServletResponse = this.mockMvc
                .perform(get(ALERTS_URL_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        logger.debug("Returned list of alerts: " + mockHttpServletResponse.getContentAsString());

        //TODO: Assert only alerts crossing the delay threshold are returned
    }


    /**
     * Utility method to convert Json file contents into bytes
     * @param filePath - Json file path to be used in test case
     * @return - Byte stream of json raw body
     * @throws IOException
     */
    private byte[] getJsonData(String filePath) throws IOException {
        ClassPathResource postJsonResourceFile = new ClassPathResource(filePath);
        return Files.readAllBytes(postJsonResourceFile.getFile().toPath());
    }

    /**
     * Utility method to Convert response stream into an array of custom objects
     * In this case converting response body to an array of Alert Objects
     * @param responseJson - Response body contents as string
     * @return - Array of Alert Objects
     * @throws IOException
     */
    private List<Alert> deserializeAlertsJson(String responseJson) throws IOException {
        JsonDeserializer<Alert> deserializedAlerts = new JsonDeserializer<>(jsonFactory, objectMapper, Alert.class);
        return deserializedAlerts.getObjectList(responseJson);
    }
}
