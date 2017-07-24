package life.dev.hari.alertChallenge.repository;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import life.dev.hari.alertChallenge.builder.AlertBuilder;
import life.dev.hari.alertChallenge.model.Alert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AlertRepositoryIntegrationTest extends WebIntegrationTestBase {

    @Autowired
    private
    AlertRepository alertRepository;

    @After
    public void clearData(){
        alertRepository.deleteAll();
    }

    @Test
    public void saveMustInsertANewAlertIntoDatabase() {
        //Create alert
        Alert alert = AlertBuilder.createAlert()
                .setDelay(30)
                .setDate(new Date())
                .setDescription("Transcription not yet completed")
                .setReferenceId("transcription_start_1")
                .getAlert();

        //Save into database
        alertRepository.save(alert);

        //Get all alerts from the alert table using 'alertRepository'
        List<Alert> alertsFromDb = (List<Alert>) alertRepository.findAll();

        //Validations
        Assert.assertNotNull(alertsFromDb);
        Assert.assertTrue(alertsFromDb.contains(alert));
        Assert.assertTrue(1 == alertsFromDb.size());
    }

    @Test
    public void deleteMustDeleteAnExistingAlertFromDatabase() {
        //Create alert
        Alert alert = AlertBuilder.createAlert()
                .setDelay(30)
                .setDate(new Date())
                .setDescription("Transcription not yet completed")
                .setReferenceId("transcription_start_1")
                .getAlert();

        //Save into database
        alertRepository.save(alert);

        //Deleting the alert from the database
        alertRepository.delete(alert);

        //Validations
        List<Alert> alertsFromDb = (List<Alert>) alertRepository.findAll();
        Assert.assertFalse(alertsFromDb.contains(alert));
        Assert.assertTrue(0 == alertsFromDb.size());
    }

    @Test
    public void findAllMustReturnAllAlertsFromDatabase(){
        //Create multiple alerts
        Alert alert = AlertBuilder.createAlert()
                .setDelay(30)
                .setDate(new Date())
                .setDescription("Transcription not yet completed")
                .setReferenceId("transcription_start_1")
                .getAlert();

        Alert completedAlert = AlertBuilder.createAlert()
                .setDelay(100)
                .setDate(new Date())
                .setDescription("Transcription completed")
                .setReferenceId("transcription_start_2")
                .getAlert();

        Alert initiatedAlert = AlertBuilder.createAlert()
                .setDelay(200)
                .setDate(new Date())
                .setDescription("Transcription initiated")
                .setReferenceId("transcription_start_3")
                .getAlert();

        //Saving multiple alerts into database
        alertRepository.save(alert);
        alertRepository.save(completedAlert);
        alertRepository.save(initiatedAlert);

        //Get all alerts from the alert table using 'alertRepository'
        List<Alert> alertsFromDb = (List<Alert>) alertRepository.findAll();

        //Validations
        Assert.assertTrue(3==alertsFromDb.size());
        Assert.assertTrue(alertsFromDb.contains(alert));
        Assert.assertTrue(alertsFromDb.contains(completedAlert));
        Assert.assertTrue(alertsFromDb.contains(initiatedAlert));
    }

    @Test
    public void findByReferenceIdMustReturnAlertWithSameReferenceIdFromDatabase(){
        //Create alert
        Alert alert = AlertBuilder.createAlert()
                .setDelay(30)
                .setDate(new Date())
                .setDescription("Transcription not yet completed")
                .setReferenceId("transcription_start_1")
                .getAlert();

        //Saving an alert into database
        alertRepository.save(alert);

        //Get alert from database which has the reference_id "transcription_start_1"
        Alert alertFromDb = alertRepository.findByReferenceId("transcription_start_1");

        //Validations
        Assert.assertEquals(alert.getId(),alertFromDb.getId());
        Assert.assertEquals(alert,alertFromDb);
    }
}
