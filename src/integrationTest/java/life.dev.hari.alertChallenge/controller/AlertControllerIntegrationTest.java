package life.dev.hari.alertChallenge.controller;

import life.dev.hari.alertChallenge.WebIntegrationTestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by plank-hari.s on 7/23/2017.
 */
public class AlertControllerIntegrationTest extends WebIntegrationTestBase {

    @Before
    public void setup() throws Exception {
        super.setup();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullReferenceId() throws Exception {
        Assert.fail();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDelay() throws Exception {
        Assert.fail();
    }


    @Test
    public void postAlertMustReturnBadRequestForNullDescription() throws Exception {
        Assert.fail();
    }


    @Test
    public void postAlertMustReturnBadRequestIfAlertAlreadyExists() throws Exception {
        Assert.fail();
    }


    @Test
    public void postAlertMustReturnCreatedForAValidAlert() throws Exception {
        Assert.fail();
    }
}
