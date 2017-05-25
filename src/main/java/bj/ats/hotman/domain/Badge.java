package bj.ats.hotman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Badge.
 */
@Entity
@Table(name = "badge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "badge")
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numberbadge", nullable = false)
    private String numberbadge;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @OneToOne(mappedBy = "badge")
    @JsonIgnore
    private Rooms rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberbadge() {
        return numberbadge;
    }

    public Badge numberbadge(String numberbadge) {
        this.numberbadge = numberbadge;
        return this;
    }

    public void setNumberbadge(String numberbadge) {
        this.numberbadge = numberbadge;
    }

    public String getMatricule() {
        return matricule;
    }

    public Badge matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Rooms getRooms() {
        return rooms;
    }

    public Badge rooms(Rooms rooms) {
        this.rooms = rooms;
        return this;
    }

    public void setRooms(Rooms rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Badge badge = (Badge) o;
        if (badge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", numberbadge='" + getNumberbadge() + "'" +
            ", matricule='" + getMatricule() + "'" +
            "}";
    }
}
