package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A Projectmessages.
 */
@Entity
@Table(name = "projectmessages")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "projectmessages")
public class Projectmessages implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project")
    private Long project;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "message")
    private String message;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

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

    public Projectmessages project(Long project) {
        this.project = project;
        return this;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Projectmessages date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Projectmessages message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getFile() {
        return file;
    }

    public Projectmessages file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Projectmessages fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getSender() {
        return sender;
    }

    public Projectmessages sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Projectmessages receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
        Projectmessages projectmessages = (Projectmessages) o;
        if (projectmessages.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectmessages.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projectmessages{" +
            "id=" + getId() +
            ", project=" + getProject() +
            ", date='" + getDate() + "'" +
            ", message='" + getMessage() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", sender='" + getSender() + "'" +
            ", receiver='" + getReceiver() + "'" +
            "}";
    }
}
