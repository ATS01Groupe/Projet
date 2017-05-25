package bj.ats.hotman.domain;

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

import bj.ats.hotman.domain.enumeration.Typeduration;

/**
 * A Duration.
 */
@Entity
@Table(name = "duration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "duration")
public class Duration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "datecome", nullable = false)
    private LocalDate datecome;

    @Column(name = "datego")
    private LocalDate datego;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Double duration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private Typeduration type;

    @OneToMany(mappedBy = "durre")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatecome() {
        return datecome;
    }

    public Duration datecome(LocalDate datecome) {
        this.datecome = datecome;
        return this;
    }

    public void setDatecome(LocalDate datecome) {
        this.datecome = datecome;
    }

    public LocalDate getDatego() {
        return datego;
    }

    public Duration datego(LocalDate datego) {
        this.datego = datego;
        return this;
    }

    public void setDatego(LocalDate datego) {
        this.datego = datego;
    }

    public Double getDuration() {
        return duration;
    }

    public Duration duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Typeduration getType() {
        return type;
    }

    public Duration type(Typeduration type) {
        this.type = type;
        return this;
    }

    public void setType(Typeduration type) {
        this.type = type;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Duration clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Duration addClient(Client client) {
        this.clients.add(client);
        client.setDurre(this);
        return this;
    }

    public Duration removeClient(Client client) {
        this.clients.remove(client);
        client.setDurre(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        if (duration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), duration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Duration{" +
            "id=" + getId() +
            ", datecome='" + getDatecome() + "'" +
            ", datego='" + getDatego() + "'" +
            ", duration='" + getDuration() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
