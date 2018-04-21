package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A Appointmentmessages.
 */
@Entity
@Table(name = "appointmentmessages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "appointmentmessages")
public class Appointmentmessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project")
    private Long project;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "state")
    private Integer state;

    @Column(name = "message")
    private String message;

    @Column(name = "reply")
    private String reply;

    @Column(name = "createdate")
    private ZonedDateTime createdate;

    @Column(name = "coach")
    private String coach;

    @Column(name = "owner")
    private String owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProject() {
        return project;
    }

    public Appointmentmessages project(Long project) {
        this.project = project;
        return this;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Appointmentmessages date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public Appointmentmessages state(Integer state) {
        this.state = state;
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public Appointmentmessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public Appointmentmessages reply(String reply) {
        this.reply = reply;
        return this;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ZonedDateTime getCreatedate() {
        return createdate;
    }

    public Appointmentmessages createdate(ZonedDateTime createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(ZonedDateTime createdate) {
        this.createdate = createdate;
    }

    public String getCoach() {
        return coach;
    }

    public Appointmentmessages coach(String coach) {
        this.coach = coach;
        return this;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getOwner() {
        return owner;
    }

    public Appointmentmessages owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointmentmessages appointmentmessages = (Appointmentmessages) o;
        if (appointmentmessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentmessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointmentmessages{" +
            "id=" + getId() +
            ", project=" + getProject() +
            ", date='" + getDate() + "'" +
            ", state=" + getState() +
            ", message='" + getMessage() + "'" +
            ", reply='" + getReply() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", coach='" + getCoach() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
