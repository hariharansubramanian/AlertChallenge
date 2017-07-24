package life.dev.hari.alertChallenge.controller.myraRestException.exceptionHandler;

import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertArgumentsException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.DuplicateAlertException;
import life.dev.hari.alertChallenge.controller.myraRestException.customExceptions.IllegalAlertDeletionException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.security.cert.CertificateException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by plank-hari.s on 7/24/2017.
 * Exception handler class that catches exceptions raised elsewhere and
 * translates them into appropriate HTTP response codes
 */

@ControllerAdvice
public class MyraRestExceptionHandlingAdviser extends ResponseEntityExceptionHandler {

    private static final String ERROR_POST_MISSING_OR_CORRUPTED_DATA = "Error posting an alert with missing or corrupted data";
    private static final String ERROR_POST_DUPLICATE_ALERT_DATA = "Alert with this reference id already exists.";
    private static final String ERROR_DELETING_ALERT_STILL_UNDER_ITS_DELAY_PERIOD = "Error deleting an alert which is still under its delay period.";

    public MyraRestExceptionHandlingAdviser() {
        super();
    }


    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            DataIntegrityViolationException.class,
            CertificateException.class,
            EntityNotFoundException.class,
            InvalidDataAccessApiUsageException.class,
            DataAccessException.class,
            NoSuchAlgorithmException.class,
            KeyStoreException.class,
            IOException.class,
            DuplicateAlertException.class,
            IllegalAlertArgumentsException.class,
            IllegalAlertDeletionException.class
    })

    public final ResponseEntity<Object> handleMyraRestException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof ConstraintViolationException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleConstraintViolationException((ConstraintViolationException) ex,
                    headers, status, request);
        } else if (ex instanceof IllegalArgumentException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleIllegalArgumentException((IllegalArgumentException) ex,
                    headers, status, request);
        } else if (ex instanceof DataIntegrityViolationException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleDataIntegrityViolationException((DataIntegrityViolationException) ex,
                    headers, status, request);
        } else if (ex instanceof DuplicateAlertException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleDuplicateAlertException((DuplicateAlertException) ex,
                    headers, status, request);

        } else if (ex instanceof IllegalAlertArgumentsException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleIllegalAlertArgumentsException((IllegalAlertArgumentsException) ex,
                    headers, status, request);

        } else if (ex instanceof IllegalAlertDeletionException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            return handleIllegalAlertDeletionException((IllegalAlertDeletionException) ex,
                    headers, status, request);

        } else if (ex instanceof EntityNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleEntityNotFoundException((EntityNotFoundException) ex,
                    headers, status, request);
        } else if (ex instanceof InvalidDataAccessApiUsageException) {
            HttpStatus status = HttpStatus.CONFLICT;
            return handleInvalidDataAccessApiUsageException((InvalidDataAccessApiUsageException) ex, headers, status, request);
        } else if (ex instanceof DataAccessException) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            return handleDataAccessException((DataAccessException) ex, headers, status, request);
        } else if (ex instanceof NoSuchAlgorithmException) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            return handleNoSuchAlgorithmException((NoSuchAlgorithmException) ex, headers, status, request);
        } else if (ex instanceof KeyStoreException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleKeyStoreException((KeyStoreException) ex, headers, status, request);
        } else if (ex instanceof IOException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleIOException((IOException) ex, headers, status, request);
        } else {
            logger.warn("Unknown exception type: " + ex.getClass().getName());
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }


    /**
     * Error handlers categorized by Return Code.
     */
    // 400
    private ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Constraint violation")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Incorrect or illegal arguments. Please check your URI and JSON inputs.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Data integrity violation")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    /**
     * Custom Error method which handles duplicate alerts being posted by returning 400 BAD_REQUEST.
     *
     * @return Custom Response Entitiy Object
     * Response Error Title: "Alert with this reference id already exists."
     * Response Error Status code: 400 BAD_REQUEST
     */
    private ResponseEntity<Object> handleDuplicateAlertException(final DuplicateAlertException ex,
                                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle(ERROR_POST_DUPLICATE_ALERT_DATA)
                .setException(ex)
                .setHttpStatus(400);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    /**
     * Custom Error method which handles null or incomplete Alert data from being posted into the system
     * returns 400 BAD_REQUEST.
     *
     * @return Custom Response Entitiy Object
     * Response Error Title: "Error posting an alert with missing or corrupted data"
     * Response Error Status code: 400 BAD_REQUEST
     */
    private ResponseEntity<Object> handleIllegalAlertArgumentsException(final IllegalAlertArgumentsException ex,
                                                                        HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle(ERROR_POST_MISSING_OR_CORRUPTED_DATA)
                .setException(ex)
                .setHttpStatus(400);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    /**
     * Custom Error method thrown when trying to delete an alert which is still under delay period
     * returns 400 BAD_REQUEST.
     *
     * @return Custom Response Entitiy Object
     * Response Error Title: "Error deleting an alert which is still under its delay period."
     * Response Error Status code: 400 BAD_REQUEST
     */
    private ResponseEntity<Object> handleIllegalAlertDeletionException(final IllegalAlertDeletionException ex,
                                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle(ERROR_DELETING_ALERT_STILL_UNDER_ITS_DELAY_PERIOD)
                .setException(ex)
                .setHttpStatus(403);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex,
                                                                 HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Resource Not found.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    // 409
    private ResponseEntity<Object> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException ex,
                                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Data conflicts due to invalid Data access APIs.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleDataAccessException(final DataAccessException ex,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("Data conflicts due to invalid Data access APIs.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleNoSuchAlgorithmException(final NoSuchAlgorithmException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("This certificate uses an algorithm unknown to the server.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    // 500
    private ResponseEntity<Object> handleKeyStoreException(final KeyStoreException ex,
                                                           HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("An internal server error encountered.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

    private ResponseEntity<Object> handleIOException(final IOException ex,
                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse bodyOfResponse = new ErrorResponse()
                .setErrorTitle("An internal server error encountered.")
                .setException(ex);
        return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
    }

}
