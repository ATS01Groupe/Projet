package bj.ats.hotman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import bj.ats.hotman.domain.enumeration.Typedroom;

import bj.ats.hotman.domain.enumeration.Etatromms;

/**
 * A Rooms.
 */
@Entity
@Table(name = "rooms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rooms")
public class Rooms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "romsnumber", nullable = false)
    private String romsnumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "typeroom", nullable = false)
    private Typedroom typeroom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private Etatromms etat;

    @OneToOne
    @JoinColumn(unique = true)
    private Badge badge;

    @OneToOne(mappedBy = "room")
    @JsonIgnore
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRomsnumber() {
        return romsnumber;
    }

    public Rooms romsnumber(String romsnumber) {
        this.romsnumber = romsnumber;
        return this;
    }

    public void setRomsnumber(String romsnumber) {
        this.romsnumber = romsnumber;
    }

    public Typedroom getTyperoom() {
        return typeroom;
    }

    public Rooms typeroom(Typedroom typeroom) {
        this.typeroom = typeroom;
        return this;
    }

    public void setTyperoom(Typedroom typeroom) {
        this.typeroom = typeroom;
    }

    public Etatromms getEtat() {
        return etat;
    }

    public Rooms etat(Etatromms etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Etatromms etat) {
        this.etat = etat;
    }

    public Badge getBadge() {
        return badge;
    }

    public Rooms badge(Badge badge) {
        this.badge = badge;
        return this;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Client getClient() {
        return client;
    }

    public Rooms client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rooms rooms = (Rooms) o;
        if (rooms.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rooms.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rooms{" +
            "id=" + getId() +
            ", romsnumber='" + getRomsnumber() + "'" +
            ", typeroom='" + getTyperoom() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
