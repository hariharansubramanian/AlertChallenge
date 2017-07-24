package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * Created by plank-hari.s on 7/23/2017.
 * Controller class to handle REST API calls to /alerts/**
 */
@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    /**
     * Posts an alert into the database
     *
     * @param alert - The Alert object that needs to be inserted into the Database.
     * @return - Returns 201 with the inserted Alert object in response body.
     */
    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Alert postAlert(@RequestBody Alert alert) throws IllegalAlertArgumentsException, DuplicateAlertException, IllegalAlertArgumentsException {
        return alertService.postAlert(alert);
    }

    /**
     * Gets a list of all alerts from the database
     * Alerts which are still under the delay threshold are NOT returned in the list.
     * @return - Returns 200 with the list of all alerts in the database not crossing the delay threshold.
     */
    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Alert> getAlerts() {
        return alertService.getAlerts();
    }

    /**
     * Deletes an alert of a particular reference_id from the database
     * Alerts which are still under the delay threshold are NOT deleted from database.
     * @return - Returns 204
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{reference_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlert(@PathVariable("reference_id") String referenceId) {
        alertService.deleteAlert(referenceId);
    }
}
