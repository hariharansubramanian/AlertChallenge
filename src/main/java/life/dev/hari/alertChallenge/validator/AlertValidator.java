package life.dev.hari.alertChallenge.validator;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by plank-hari.s on 7/23/2017.
 * This class helps with validating alert objects
 * Handles catching and throwing exceptions back to the controller
 */
@Component
public class AlertValidator {

    private static final String INVALID_ALERT_POSTED = "Invalid Alert posted.";
    private static final String ALERT_ALREADY_EXISTS_IN_DATABASE = "Alert already exists in database.";
    @Autowired
    private
    AlertRepository alertRepository;

    public void validateAlert(Alert alert) throws IllegalAlertArgumentsException,DuplicateAlertException {
        if (alert == null ||
                alert.getReferenceId() == null ||
                alert.getDelay() == null ||
                alert.getDescription() == null) {
            throw new IllegalAlertArgumentsException(INVALID_ALERT_POSTED);
        }

        Alert alertFromDb = alertRepository.findByReferenceId(alert.getReferenceId());
        if (alertFromDb != null) {
            throw new DuplicateAlertException(ALERT_ALREADY_EXISTS_IN_DATABASE);
        }
    }
}
