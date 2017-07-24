package life.dev.hari.alertChallenge.repository;

import life.dev.hari.alertChallenge.model.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by plank-hari.s on 7/23/2017.
 * Repository class for the Alert entity
 */
@Repository
@RestResource(exported = false)
public interface AlertRepository extends CrudRepository<Alert,Long> {

    Alert findById(Long id);
    Alert findByReferenceId(String referenceId);
}
