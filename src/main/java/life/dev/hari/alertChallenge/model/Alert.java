package life.dev.hari.alertChallenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by plank-hari.s on 7/23/2017.
 * Model class for Entity Alert
 */
@Entity
@Table(name = "alert")
public class Alert extends AbstractEntity {

    @NotNull
    @Column(name = "date_created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date dateCreated;

    @NotNull
    @JsonProperty(value = "reference_id")
    @Column(name = "reference_id")
    public String referenceId;

    @NotNull
    @Column(name = "delay")
    public Integer delay;

    @NotNull
    @Column(name = "description")
    private String description;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "dateCreated=" + dateCreated +
                ", referenceId='" + referenceId + '\'' +
                ", delay=" + delay +
                ", description='" + description + '\'' +
                '}';
    }
}
