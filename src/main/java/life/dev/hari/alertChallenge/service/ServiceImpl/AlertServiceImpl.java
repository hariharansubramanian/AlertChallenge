package life.dev.hari.alertChallenge.service.ServiceImpl;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertDeletionException;
import life.dev.hari.alertChallenge.model.Alert;
import life.dev.hari.alertChallenge.repository.AlertRepository;
import life.dev.hari.alertChallenge.service.AlertService;
import life.dev.hari.alertChallenge.validator.AlertValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public Alert postAlert(Alert alert) throws IllegalAlertArgumentsException, DuplicateAlertException {
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

        return alertsFromDb
                .stream()
                .filter(a -> currentTime > (a.getDateCreated().getTime() + TimeUnit.SECONDS.toMillis(a.getDelay())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAlert(String referenceId) {
        if (referenceId == null) throw new IllegalAlertArgumentsException();

        Alert alertFromDb = alertRepository.findByReferenceId(referenceId);
        if (alertFromDb == null) throw new EntityNotFoundException();

        if (alertFromDb.getDateCreated().getTime() + TimeUnit.SECONDS.toMillis(alertFromDb.getDelay()) > new Date().getTime()) {
            throw new IllegalAlertDeletionException();
        }
        alertRepository.delete(alertFromDb);
    }
}
