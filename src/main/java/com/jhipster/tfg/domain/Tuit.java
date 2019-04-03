package com.jhipster.tfg.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tuit.
 */
@Entity
@Table(name = "tuit")
public class Tuit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_emisor", nullable = false)
    private String userEmisor;

    @Column(name = "user_receptor")
    private String userReceptor;

    @NotNull
    @Size(max = 240)
    @Column(name = "texto_tuit", length = 240, nullable = false)
    private String textoTuit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmisor() {
        return userEmisor;
    }

    public Tuit userEmisor(String userEmisor) {
        this.userEmisor = userEmisor;
        return this;
    }

    public void setUserEmisor(String userEmisor) {
        this.userEmisor = userEmisor;
    }

    public String getUserReceptor() {
        return userReceptor;
    }

    public Tuit userReceptor(String userReceptor) {
        this.userReceptor = userReceptor;
        return this;
    }

    public void setUserReceptor(String userReceptor) {
        this.userReceptor = userReceptor;
    }

    public String getTextoTuit() {
        return textoTuit;
    }

    public Tuit textoTuit(String textoTuit) {
        this.textoTuit = textoTuit;
        return this;
    }

    public void setTextoTuit(String textoTuit) {
        this.textoTuit = textoTuit;
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
        Tuit tuit = (Tuit) o;
        if (tuit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tuit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tuit{" +
            "id=" + getId() +
            ", userEmisor='" + getUserEmisor() + "'" +
            ", userReceptor='" + getUserReceptor() + "'" +
            ", textoTuit='" + getTextoTuit() + "'" +
            "}";
    }
}
