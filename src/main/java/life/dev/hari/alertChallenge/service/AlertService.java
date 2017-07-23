package life.dev.hari.alertChallenge.service;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.model.Alert;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public interface AlertService {

    Alert postAlert(Alert alert) throws IllegalAlertArgumentsException;
}
