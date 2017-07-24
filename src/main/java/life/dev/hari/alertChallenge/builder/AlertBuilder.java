package life.dev.hari.alertChallenge.builder;

import life.dev.hari.alertChallenge.model.Alert;

import java.util.Date;

/**
 * Created by plank-hari.s on 7/23/2017.
 * This class implements a Builder pattern to enable chain-setters while instantiating an Alert
 * Private constructor to create an Alert instance
 * Enabling chained-setters by returning an AlertBuilder instance
 * getAlert() is used to return the Alert object itself.
 */
public class AlertBuilder {
    private Alert alert;

    private AlertBuilder(){
        this.alert = new Alert();
    }
    public static AlertBuilder createAlert() {return new AlertBuilder();}

    public AlertBuilder setDate(Date date){
        this.alert.setDateCreated(date);
        return this;
    }

    public AlertBuilder setReferenceId(String referenceId){
        this.alert.setReferenceId(referenceId);
        return this;
    }

    public AlertBuilder setDelay(Integer delay){
        this.alert.setDelay(delay);
        return this;

    }

    public AlertBuilder setDescription(String description){
        this.alert.setDescription(description);
        return this;
    }

    public Alert getAlert(){return this.alert;}

}
