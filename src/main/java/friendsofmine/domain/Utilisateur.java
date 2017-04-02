package friendsofmine.domain;

/**
 * Created by mundial on 02/03/2017.
 */

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utilisateur {

    //un Utilisateur peut avoir réalisé plusieurs activités
    @OneToMany
    @JoinColumn(name="id_utilisateur")
    private Set<Activite> activites = new HashSet<Activite>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=1)
    private String nom;

    @NotNull
    @Size(min=1)
    private String prenom;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp="M|F")
    private String sexe;

    private Date dateNaissance;

    public Utilisateur(String nom, String prenom, String email, String sexe, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
    }

    public Utilisateur(String nom, String prenom, String email, String sexe){
        this.nom=nom;
        this.prenom=prenom;
        this.email= email;
        this.sexe= sexe;
    }

    public Utilisateur(){

    }

    public Long getId() {
       return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    //JPA
    public void addActivites( Activite act) {
        activites.add(act) ;}

    public Set <Activite > getActivites() {
        return activites ;}

}
