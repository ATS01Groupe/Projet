package bj.ats.hotman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Groupe.
 */
@Entity
@Table(name = "groupe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "groupe")
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namegroupe")
    private String namegroupe;

    @OneToMany(mappedBy = "groupe")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamegroupe() {
        return namegroupe;
    }

    public Groupe namegroupe(String namegroupe) {
        this.namegroupe = namegroupe;
        return this;
    }

    public void setNamegroupe(String namegroupe) {
        this.namegroupe = namegroupe;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Groupe clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Groupe addClient(Client client) {
        this.clients.add(client);
        client.setGroupe(this);
        return this;
    }

    public Groupe removeClient(Client client) {
        this.clients.remove(client);
        client.setGroupe(null);
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
        Groupe groupe = (Groupe) o;
        if (groupe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Groupe{" +
            "id=" + getId() +
            ", namegroupe='" + getNamegroupe() + "'" +
            "}";
    }
}
