package life.dev.hari.alertChallenge.service.ServiceImpl;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import life.dev.hari.alertChallenge.service.AlertService;
import life.dev.hari.alertChallenge.validator.AlertValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * Created by plank-hari.s on 7/23/2017.
 * Implements the AlertService interface.
 * Service class responsible for business logic used in AlertController
 */
@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertValidator alertValidator;

    @Autowired
    private AlertRepository alertRepository;

    @Override
    public Alert postAlert(Alert alert) throws IllegalAlertArgumentsException,DuplicateAlertException{
            alertValidator.validateAlert(alert);
            alert.setDateCreated(new Date());
            alertRepository.save(alert);
            return alert;
    }

    @Override
    public Iterable<Alert> getAlerts() {
        //TODO: Implement logic to filter out alerts which are still in the delay threshold
        //TODO: return filtered list
        return alertRepository.findAll();
    }
}
