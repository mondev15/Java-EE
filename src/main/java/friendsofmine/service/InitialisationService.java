package friendsofmine.service;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Inscription;
import friendsofmine.domain.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.ArrayList;

/**
 * Created by mundial on 02/03/2017.
 */

//modification de la classe de telle façon que le jeu de données créé
//       soit persisté en base de données au lieu d’être sauvegardé au sein d’une paire de
//      ArrayList.
@Service
@Transactional
public class InitialisationService {

    @Autowired
    private ActiviteService activiteService;
    @Autowired
    private InscriptionService inscriptionService;

    private Utilisateur thom, mary;
    private Activite randonnee, lindyhop, taekwondo;
    private Inscription maryOnTaekwondo;
    private Inscription thomOnRandonnee;
    private Inscription thomOnLindyhop;

    //private ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
    //private ArrayList<Activite> activites = new ArrayList<>();

    public void initDonnees(){

        initMary();
        initThom();
        initRandonnee();
        initLindyHop();
        initTaekwondo();

//        //utilisateurs
//        Utilisateur user1 = new Utilisateur("Durand", "Jacques", "jd@jd.com", "M");
//        Utilisateur user2= new Utilisateur("Dupond", "Jeanne", "jd@jd.com", "F");
//       this.utilisateurs.add(user1);
//       this.utilisateurs.add(user2);
//        //activités
//       this.activites.add(new Activite("unTitre", "unDescriptif",user1));
//        this.activites.add(new Activite("unTitre1", "unDescriptif1", user2));
    }


//    public ArrayList<Utilisateur> getUtilisateurs(){
//        return this.utilisateurs;
//    }
//
//    public ArrayList<Activite> getActivites(){
//        return this.activites;
//    }


    public void initInscriptions(){
        initMaryOnTaekwondo();
        initThomOnRandonnee();
        initThomOnLindyhop();
    }

    private void initThom() {
        thom = new Utilisateur("Thom", "Thom", "thom@thom.com", "M");
    }

    private void initMary() {
        mary = new Utilisateur("Mary", "Mary", "mary@mary.com", "F");
    }

    private void initTaekwondo() {
        taekwondo = new Activite("Taekwondo", "", thom);
        activiteService.saveActivite(taekwondo);

    }

    private void initLindyHop() {
        lindyhop = new Activite("Lindy Hop", "le jeudi soir", thom);
        activiteService.saveActivite(lindyhop);
    }

    private void initRandonnee() {
        randonnee = new Activite("Randonnee", "le lundi matin", mary);
        activiteService.saveActivite(randonnee);
    }

    private void initMaryOnTaekwondo(){
        maryOnTaekwondo = new Inscription();
        maryOnTaekwondo.setParticipant(mary);
        maryOnTaekwondo.setActivite(taekwondo);
        inscriptionService.saveInscription(maryOnTaekwondo);
    }

    private void initThomOnRandonnee(){
        thomOnRandonnee = new Inscription();
        thomOnRandonnee.setParticipant(thom);
        thomOnRandonnee.setActivite(randonnee);
        inscriptionService.saveInscription(thomOnRandonnee);
    }

    private void initThomOnLindyhop(){
        thomOnLindyhop = new Inscription();
        thomOnLindyhop.setParticipant(thom);
        thomOnLindyhop.setActivite(lindyhop);
        inscriptionService.saveInscription(thomOnLindyhop);
    }

    public Utilisateur getThom() {
        return thom;
    }

    public Utilisateur getMary() {
        return mary;
    }

    public Activite getRandonnee() {
        return randonnee;
    }

    public Activite getLindyhop() {
        return lindyhop;
    }

    public Activite getTaekwondo() {
        return taekwondo;
    }

    public Inscription getMaryOnTaekwondo() {
        return maryOnTaekwondo;
    }

    public Inscription getThomOnRandonnee() {
        return thomOnRandonnee;
    }

    public Inscription getThomOnLindyhop() {
        return thomOnLindyhop;
    }
}
