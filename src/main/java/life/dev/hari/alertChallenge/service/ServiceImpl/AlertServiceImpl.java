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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        //Get all allerts from database
        List<Alert> alertsFromDb = (List<Alert>) alertRepository.findAll();

        long currentTime = new Date().getTime();
        //Using Lambda, extract alerts which have crossed the delay threshold.
        List<Alert> alertsCrossingDelayThreshold = alertsFromDb
                .stream()
                .filter(a -> currentTime > (a.getDateCreated().getTime() + TimeUnit.SECONDS.toMillis(a.getDelay())))
                .collect(Collectors.toList());

        return alertsCrossingDelayThreshold;
    }

    @Override
    public void deleteAlert(String referenceId) {
        //TODO: Using Lambda, extract alerts which have crossed the delay threshold and matches this referenceId.
        //TODO: Get alert from Database which have crossed delay threshold and has same reference Id
        //TODO: Handle validation against Null refrence_id and return 400
    }
}
