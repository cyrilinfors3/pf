package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "creationdate", nullable = false)
    private LocalDate creationdate;

    @NotNull
    @Lob
    @Column(name = "documentaion", nullable = false)
    private byte[] documentaion;

    @Column(name = "documentaion_content_type", nullable = false)
    private String documentaionContentType;

    @Column(name = "status")
    private String status;

    @Column(name = "owner")
    private Long owner;

    @Column(name = "sponsor")
    private Long sponsor;

    @Column(name = "coach")
    private Long coach;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vote> votes = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Projectevolution> projectevolutions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Project title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreationdate() {
        return creationdate;
    }

    public Project creationdate(LocalDate creationdate) {
        this.creationdate = creationdate;
        return this;
    }

    public void setCreationdate(LocalDate creationdate) {
        this.creationdate = creationdate;
    }

    public byte[] getDocumentaion() {
        return documentaion;
    }

    public Project documentaion(byte[] documentaion) {
        this.documentaion = documentaion;
        return this;
    }

    public void setDocumentaion(byte[] documentaion) {
        this.documentaion = documentaion;
    }

    public String getDocumentaionContentType() {
        return documentaionContentType;
    }

    public Project documentaionContentType(String documentaionContentType) {
        this.documentaionContentType = documentaionContentType;
        return this;
    }

    public void setDocumentaionContentType(String documentaionContentType) {
        this.documentaionContentType = documentaionContentType;
    }

    public String getStatus() {
        return status;
    }

    public Project status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOwner() {
        return owner;
    }

    public Project owner(Long owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getSponsor() {
        return sponsor;
    }

    public Project sponsor(Long sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public void setSponsor(Long sponsor) {
        this.sponsor = sponsor;
    }

    public Long getCoach() {
        return coach;
    }

    public Project coach(Long coach) {
        this.coach = coach;
        return this;
    }

    public void setCoach(Long coach) {
        this.coach = coach;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Project votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Project addVote(Vote vote) {
        this.votes.add(vote);
        vote.setProject(this);
        return this;
    }

    public Project removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setProject(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Projectevolution> getProjectevolutions() {
        return projectevolutions;
    }

    public Project projectevolutions(Set<Projectevolution> projectevolutions) {
        this.projectevolutions = projectevolutions;
        return this;
    }

    public Project addProjectevolution(Projectevolution projectevolution) {
        this.projectevolutions.add(projectevolution);
        projectevolution.setProject(this);
        return this;
    }

    public Project removeProjectevolution(Projectevolution projectevolution) {
        this.projectevolutions.remove(projectevolution);
        projectevolution.setProject(null);
        return this;
    }

    public void setProjectevolutions(Set<Projectevolution> projectevolutions) {
        this.projectevolutions = projectevolutions;
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", creationdate='" + getCreationdate() + "'" +
            ", documentaion='" + getDocumentaion() + "'" +
            ", documentaionContentType='" + getDocumentaionContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", owner=" + getOwner() +
            ", sponsor=" + getSponsor() +
            ", coach=" + getCoach() +
            "}";
    }
}
