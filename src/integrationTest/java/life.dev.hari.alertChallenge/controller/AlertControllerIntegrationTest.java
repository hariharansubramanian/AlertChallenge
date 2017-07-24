package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import life.dev.hari.alertChallenge.builder.AlertBuilder;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import life.dev.hari.alertChallenge.utilities.JsonDeserializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AlertControllerIntegrationTest extends WebIntegrationTestBase {

    private static final String ALERTS_URL_PATH = "/alerts";
    private Logger logger = LoggerFactory.getLogger(AlertControllerIntegrationTest.class);

    @Autowired
    private
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

        //Validation
        List<Alert> alerts = deserializeAlertsJson(mockHttpServletResponse.getContentAsString());
        Assert.isTrue(alerts.size() == 0);
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void getAlertsMustReturnOnlyAlertsWhichHaveCrossedTheDelayThreshold() throws Exception {
        //Create alerts with different delay periods.
        Alert alertNotCrossingDelayThreshold = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(100000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_03").getAlert();

        Alert alertNotCrossingDelayThreshold2 = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(10000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_04").getAlert();

        Alert alertNotCrossingDelayThreshold3 = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(100000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_05").getAlert();

        Alert validListingAlert = AlertBuilder
                .createAlert()
                .setDelay(0)
                .setDate(new Date())
                .setDescription("High priority alert, list this immediately, list this later.")
                .setReferenceId("reference_id_02")
                .getAlert();

        //Save alerts into database
        alertRepository.save(alertNotCrossingDelayThreshold);
        alertRepository.save(alertNotCrossingDelayThreshold2);
        alertRepository.save(alertNotCrossingDelayThreshold3);
        alertRepository.save(validListingAlert);

        mockHttpServletResponse = this.mockMvc
                .perform(get(ALERTS_URL_PATH))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        logger.debug("Returned list of alerts: " + mockHttpServletResponse.getContentAsString());

        //Validation
        List<Alert> alerts = deserializeAlertsJson(mockHttpServletResponse.getContentAsString());
        Assert.isTrue(!alerts.contains(alertNotCrossingDelayThreshold));
        Assert.isTrue(!alerts.contains(alertNotCrossingDelayThreshold2));
        Assert.isTrue(!alerts.contains(alertNotCrossingDelayThreshold3));
        Assert.isTrue(alerts.contains(validListingAlert));
    }

    /**
     * DELETE /alert/{referenceId}
     */
    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deleteAlertMustReturnNotFoundForInvalidAlertReferenceId() throws Exception {
        //Create an alert
        Alert validListingAlert = AlertBuilder
                .createAlert()
                .setDelay(0)
                .setDate(new Date())
                .setDescription("High priority alert, list this immediately, list this later.")
                .setReferenceId("reference_id_02")
                .getAlert();

        //Save alert into database
        alertRepository.save(validListingAlert);

        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/invalid_reference_id"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();
    }

    @Test
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deleteAlertMustDeleteAlertIfUnderDelayPeriod() throws Exception {
        //Create alerts with different delay periods.
        Alert alertNotCrossingDelayThreshold = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(100000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_not_compatable_for_deletion").getAlert();

        Alert alertNotCrossingDelayThreshold2 = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(10000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_not_compatable_for_deletion_2").getAlert();

        Alert alertNotCrossingDelayThreshold3 = AlertBuilder
                .createAlert()
                .setDate(new Date())
                .setDelay(100000)
                .setDescription("Lazy alert, will take time to process, list this later.")
                .setReferenceId("reference_id_not_compatable_for_deletion_3").getAlert();

        Alert validListingAlert = AlertBuilder
                .createAlert()
                .setDelay(0)
                .setDate(new Date())
                .setDescription("High priority alert, list this immediately, list this later.")
                .setReferenceId("reference_id_compatable_for_deletion")
                .getAlert();

        //Save alerts into database
        alertRepository.save(alertNotCrossingDelayThreshold);
        alertRepository.save(alertNotCrossingDelayThreshold2);
        alertRepository.save(alertNotCrossingDelayThreshold3);
        alertRepository.save(validListingAlert);

        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/reference_id_compatable_for_deletion"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/reference_id_does_not_exist"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();

        //TODO: Convert bad request to forbidden
        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/reference_id_not_compatable_for_deletion"))
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/reference_id_not_compatable_for_deletion_2"))
                .andExpect(status().isForbidden())
                .andReturn().getResponse();

        mockHttpServletResponse = this.mockMvc
                .perform(delete(ALERTS_URL_PATH + "/reference_id_not_compatable_for_deletion_3"))
                .andExpect(status().isForbidden())
                .andReturn().getResponse();
    }

    /**
     * Utility method to convert Json file contents into bytes
     *
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
     *
     * @param responseJson - Response body contents as string
     * @return - Array of Alert Objects
     * @throws IOException
     */
    private List<Alert> deserializeAlertsJson(String responseJson) throws IOException {
        JsonDeserializer<Alert> deserializedAlerts = new JsonDeserializer<>(jsonFactory, objectMapper, Alert.class);
        return deserializedAlerts.getObjectList(responseJson);
    }
}
