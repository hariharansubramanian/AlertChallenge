package life.dev.hari.alertChallenge.service;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public interface AlertService {

    /**
     * POST an alert
     * Implementated method handles all logic related to posting an Alert
     * Validates Alert object
     * Sets current time as date created
     * If Validated, Inserts into database with new generated id automatically
     * @param alert - RequestBody alert
     * @return - Inserted Alert Json
     * @throws IllegalAlertArgumentsException
     */
    Alert postAlert(Alert alert) throws IllegalAlertArgumentsException;

    /**
     * GET all alerts
     * Implemented method handles all logic related to getting list of all alerts
     * Uses lambda expressions to filter the list of alerts based on delay threshold
     * @return - List of all alerts from database which have crossed their delay thresholds
     */
    Iterable<Alert> getAlerts();

    /**
     * DELETE an alert by passing its reference_id
     * Handles all logic related to deleting an alert
     * Validates for valid reference_id and alert from database
     * Deletes alert of a provided reference_id
     * Does not delete alerts which are under delay period
     * @param referenceId - Reference id of the alert attempting to be deleted.
     */
    void deleteAlert(String referenceId);
}
