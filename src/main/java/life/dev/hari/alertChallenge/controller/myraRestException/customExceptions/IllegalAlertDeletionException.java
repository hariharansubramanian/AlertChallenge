package life.dev.hari.alertChallenge.controller.myraRestException.customExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by plank-hari.s on 7/24/2017.
 */
public class IllegalAlertDeletionException extends RuntimeException {
    private static Logger logger = LoggerFactory.getLogger(DuplicateAlertException.class);

    public IllegalAlertDeletionException() {
        super();
    }

    public IllegalAlertDeletionException(String message, Throwable cause) {
        super(message, cause);
        logger.info(message + " " + cause.getMessage());
    }

    public IllegalAlertDeletionException(String message) {
        super(message);
        logger.info(message);
    }

    public IllegalAlertDeletionException(Throwable cause) {
        super(cause);
        logger.info(cause.getMessage());
    }
}
