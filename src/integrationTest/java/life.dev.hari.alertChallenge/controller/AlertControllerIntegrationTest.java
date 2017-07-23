package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public class AlertControllerIntegrationTest extends WebIntegrationTestBase {

    public static final String ALERTS_URL_PATH = "/alerts";

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
        //TODO: Extract the 2 lines below into a method called "getJsonData(jsonResourceFile)"
        ClassPathResource postJsonResourceFile = new ClassPathResource("testData/postAlert/postAlertMustReturnBadRequestForNullReferenceId.json");
        byte[] alertJsonWithNullReferenceId = Files.readAllBytes(postJsonResourceFile.getFile().toPath());

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullReferenceId))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDelay() throws Exception {
        //TODO: Extract the 2 lines below into a method called "getJsonData(jsonResourceFile)"
        ClassPathResource postJsonResourceFile = new ClassPathResource("testData/postAlert/postAlertMustReturnBadRequestForNullDelay.json");
        byte[] alertJsonWithNullDelay = Files.readAllBytes(postJsonResourceFile.getFile().toPath());

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullDelay))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDescription() throws Exception {
        //TODO: Extract the 2 lines below into a method called "getJsonData(jsonResourceFile)"
        ClassPathResource postJsonResourceFile = new ClassPathResource("testData/postAlert/postAlertMustReturnBadRequestForNullDescription.json");
        byte[] alertJsonWithNullDescription = Files.readAllBytes(postJsonResourceFile.getFile().toPath());

        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(alertJsonWithNullDescription))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void postAlertMustReturnBadRequestIfAlertAlreadyExists() throws Exception {
        //TODO: Extract the 2 lines below into a method called "getJsonData(jsonResourceFile)"
        ClassPathResource postJsonResourceFile = new ClassPathResource("testData/postAlert/postAlertMustReturnCreatedForValidAlert.json");
        byte[] validAlertJson = Files.readAllBytes(postJsonResourceFile.getFile().toPath());

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
        //TODO: Extract the 2 lines below into a method called "getJsonData(jsonResourceFile)"
        ClassPathResource postJsonResourceFile = new ClassPathResource("testData/postAlert/postAlertMustReturnCreatedForValidAlert.json");
        byte[] validAlertJson = Files.readAllBytes(postJsonResourceFile.getFile().toPath());

        //Post a valid alert should succeed
        this.mockMvc.perform(post(ALERTS_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(validAlertJson))
                .andExpect(status().isCreated())
                .andReturn();
    }
}
