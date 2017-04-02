package friendsofmine.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mundial on 22/03/2017.
 */

@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn ( name ="id_utilisateur")
    private Utilisateur  participant;

    @NotNull
    @ManyToOne
    @JoinColumn ( name ="id_activite")
    private Activite activite;
    private Date dateInscription;

    public Inscription(Utilisateur participant, Activite activite, Date date) {
        this.participant = participant;
        this.activite = activite;
        this.dateInscription = date;
    }

    public Inscription(){

    }

    public Utilisateur getParticipant() {
        return participant;
    }

    public void setParticipant(Utilisateur participant) {
        this.participant = participant;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date date) {
        this.dateInscription = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

}
