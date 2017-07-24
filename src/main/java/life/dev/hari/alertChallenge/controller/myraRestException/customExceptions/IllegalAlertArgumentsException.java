package life.dev.hari.alertChallenge.controller.myraRestException.customExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by plank-hari.s on 7/24/2017.
 */
public class IllegalAlertArgumentsException extends RuntimeException {
    private static Logger logger = LoggerFactory.getLogger(DuplicateAlertException.class);

    public IllegalAlertArgumentsException() {
        super();
    }

    public IllegalAlertArgumentsException(String message, Throwable cause) {
        super(message, cause);
        logger.info(message + " " + cause.getMessage());
    }

    public IllegalAlertArgumentsException(String message) {
        super(message);
        logger.info(message);
    }

    public IllegalAlertArgumentsException(Throwable cause) {
        super(cause);
        logger.info(cause.getMessage());
    }
}
