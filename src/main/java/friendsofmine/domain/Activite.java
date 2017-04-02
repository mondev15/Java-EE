package friendsofmine.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by mundial on 02/03/2017.
 */

@Entity
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name="id_utilisateur")
    @NotNull
    @JsonIgnore
    private Utilisateur responsable; //JsonIgnore pour marquer les propriétés à exclure


    @NotNull
    @Size(min=1)
    private String titre;

    private String descriptif;

    public Activite(String titre, String descriptif, Utilisateur responsable) {
        this.titre= titre;
        this.descriptif= descriptif;
        this.responsable = responsable;
    }

    //ajouté pour faire passer testActiviteRecupereeInchangee et testActiviteRecupereeNotNull
    public Activite(){
        this.titre="defaultTitle";
        this.descriptif="defaultDescriptif";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    //JPA Many To one
    public Utilisateur getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

}

