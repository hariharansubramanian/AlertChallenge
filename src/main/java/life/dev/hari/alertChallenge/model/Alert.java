package life.dev.hari.alertChallenge.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @Column(name = "reference_id")
    public String referenceId;

    @NotNull
    @Column(name = "delay")
    public Integer delay = 0;

    @NotNull
    @Column(name = "description")
    private String description;

}
