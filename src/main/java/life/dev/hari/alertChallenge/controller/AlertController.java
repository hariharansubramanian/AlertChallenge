package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.model.Alert;
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

        //TODO: Validate alert object sent in the request
        //TODO: Validate for null contents
        //TODO: Validate if alert with same id & reference id exists already

        //TODO: Set dateCreated with currentTime
        //TODO: Insert into database
        //TODO: Return Json string of the inserted Alert
        return new Alert();
    }
}
