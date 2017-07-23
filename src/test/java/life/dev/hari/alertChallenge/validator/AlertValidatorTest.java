package life.dev.hari.alertChallenge.validator;

import life.dev.hari.alertChallenge.builder.AlertBuilder;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public class AlertValidatorTest {

    @InjectMocks
    AlertValidator alertValidator;

    @Mock
    AlertRepository alertRepository;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalAlertArgumentsException.class)
    public void validateAlertMustThrowIllegalAlertArgumentExceptionWhenAlertIsNull() {
        Alert alert  = null;
        this.alertValidator.validateAlert(alert);
    }

    @Test(expected = IllegalAlertArgumentsException.class)
    public void validateAlertMustThrowIllegalAlertArgumentExceptionWhenAlertDelayIsNull() {

        Alert alertWithNullDelay  = AlertBuilder.createAlert()
                .setReferenceId("reference_1")
                .setDescription("description_1")
                .setDate(new Date())
                .setDelay(null)
                .getAlert();

        this.alertValidator.validateAlert(alertWithNullDelay);
    }

    @Test(expected = IllegalAlertArgumentsException.class)
    public void validateAlertMustThrowIllegalAlertArgumentExceptionWhenAlertReferenceIdIsNull() {

        Alert alertWithNullReferenceId  = AlertBuilder.createAlert()
                .setReferenceId(null)
                .setDescription("description_1")
                .setDate(new Date())
                .setDelay(30)
                .getAlert();

        this.alertValidator.validateAlert(alertWithNullReferenceId);
    }

    @Test(expected = IllegalAlertArgumentsException.class)
    public void validateAlertMustThrowIllegalAlertArgumentExceptionWhenAlertDescriptionIsNull() {

        Alert alertWithNullDescription  = AlertBuilder.createAlert()
                .setReferenceId("reference_1")
                .setDescription(null)
                .setDate(new Date())
                .setDelay(30)
                .getAlert();

        this.alertValidator.validateAlert(alertWithNullDescription);
    }

    @Test(expected = DuplicateAlertException.class)
    public void validateAlertMustThrowIllegalAlertArgumentExceptionWhenAlertAlreadyExists() {

        Alert alert  = AlertBuilder.createAlert()
                .setReferenceId("reference_1")
                .setDescription("description_1")
                .setDate(new Date())
                .setDelay(30)
                .getAlert();
        Alert alertFromDb = alert;
        when(alertRepository.findByReferenceId(alert.getReferenceId())).thenReturn(alertFromDb);
        this.alertValidator.validateAlert(alert);
    }
}
