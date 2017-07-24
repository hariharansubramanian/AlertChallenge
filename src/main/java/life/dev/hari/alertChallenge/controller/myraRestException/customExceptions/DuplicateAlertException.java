package life.dev.hari.alertChallenge.controller.myraRestException.customExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by plank-hari.s on 9/12/2016.
 */
public class DuplicateAlertException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(DuplicateAlertException.class);

    public DuplicateAlertException() {
        super();
    }

    public DuplicateAlertException(String message, Throwable cause) {
        super(message, cause);
        logger.info(message + " " + cause.getMessage());
    }

    public DuplicateAlertException(String message) {
        super(message);
        logger.info(message);
    }

    public DuplicateAlertException(Throwable cause) {
        super(cause);
        logger.info(cause.getMessage());
    }
}
