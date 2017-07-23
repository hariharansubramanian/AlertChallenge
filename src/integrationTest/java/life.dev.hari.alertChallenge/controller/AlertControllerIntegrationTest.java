package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import life.dev.hari.alertChallenge.model.Alert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public class AlertControllerIntegrationTest extends WebIntegrationTestBase {

    @Before
    public void setup() throws Exception {
        super.setup();
    }


    @Test
    public void dummyTest() throws Exception {
        Alert alert = null;
        Assert.isNull(alert);
    }
}
