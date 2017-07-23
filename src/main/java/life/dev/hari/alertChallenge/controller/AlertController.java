package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import life.dev.hari.alertChallenge.service.AlertService;
import life.dev.hari.alertChallenge.service.ServiceImpl.AlertServiceImpl;
import life.dev.hari.alertChallenge.validator.AlertValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;

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
    public Alert postAlert(@RequestBody Alert alert) {
        return alertService.postAlert(alert);
    }
}
