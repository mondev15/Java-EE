package friendsofmine;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Inscription;
import friendsofmine.domain.Utilisateur;
import friendsofmine.service.ActiviteService;
import friendsofmine.service.InitialisationService;
import friendsofmine.service.InscriptionService;
import friendsofmine.service.UtilisateurService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapTest {

    @Autowired
    private Bootstrap bootstrap;

    @Autowired
    ActiviteService activiteService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    InscriptionService inscriptionService;

    private Utilisateur thom, mary;
    private Activite randonnee, lindyhop, taekwondo;
    private Inscription maryOnTaekwondo, thomOnRandonnee, thomOnLindyhop;

    @Before
    public void setUp() {
        InitialisationService initialisationService = bootstrap.getInitialisationService();
        thom = initialisationService.getThom();
        mary = initialisationService.getMary();
        randonnee = initialisationService.getRandonnee();
        lindyhop = initialisationService.getLindyhop();
        taekwondo = initialisationService.getTaekwondo();
        maryOnTaekwondo = initialisationService.getMaryOnTaekwondo();
        thomOnRandonnee = initialisationService.getThomOnRandonnee();
        thomOnLindyhop = initialisationService.getThomOnLindyhop();
    }

    @Test
    public void testNombreActivite() {
        assertEquals(3, activiteService.findAllActivites().size());
    }

    @Test
    public void testNombreUtilisateur() {
        //
        assertEquals(2, utilisateurService.findAllUtilisateurs().size());
    }

    @Test
    public void testNombreInscription() {
        assertEquals(3, inscriptionService.countInscription());
    }

    @Test
    public void testMaryOnTaekwondo() {
        assertEquals(mary, maryOnTaekwondo.getParticipant());
        assertEquals(taekwondo, maryOnTaekwondo.getActivite());
    }

    @Test
    public void testThomOnRandonnee() {
        assertEquals(thom, thomOnRandonnee.getParticipant());
        assertEquals(randonnee, thomOnRandonnee.getActivite());
    }

    @Test
    public void testThomOnLindyhop() {
        assertEquals(thom, thomOnLindyhop.getParticipant());
        assertEquals(lindyhop, thomOnLindyhop.getActivite());
    }

    @Test
    public void testActiviteTriParTitre() {
        ArrayList<Activite> activites = activiteService.findAllActivites();
        assertEquals(lindyhop.getTitre(), activites.get(0).getTitre());
        assertEquals(randonnee.getTitre(), activites.get(1).getTitre());
        assertEquals(taekwondo.getTitre(), activites.get(2).getTitre());
    }

    @Test
    public void testUtilisateursTriParNom() {
        ArrayList<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
        assertEquals(mary.getNom(), utilisateurs.get(0).getNom());
        assertEquals(thom.getNom(), utilisateurs.get(1).getNom());
    }

    @Test
    @Transactional
    public void testNombreDActivitesParUtilisateur() {
        ArrayList<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
        assertEquals(1, utilisateurs.get(0).getActivites().size());
        assertEquals(2, utilisateurs.get(1).getActivites().size());
    }

//
//    @Autowired
//    private BootStrap bootstrap;
//
//    @Autowired
//    ActiviteService activiteService;
//
//    @Autowired
//    UtilisateurService utilisateurService;
//
//    private Utilisateur thom, mary;
//    private Activite randonnee, lindyhop, taekwondo;
//
//    @Before
//    public void setUp() {
//        InitialisationService initialisationService = bootstrap.getInitialisationService();
//        thom = initialisationService.getThom();
//        mary = initialisationService.getMary();
//        randonnee = initialisationService.getRandonnee();
//        lindyhop = initialisationService.getLindyhop();
//        taekwondo = initialisationService.getTaekwondo();
//    }
//
//    @Test
//    public void testNombreActivite() {
//        assertEquals(3, activiteService.findAllActivites().size());
//    }
//
//    @Test
//    public void testNombreUtilisateur() {
//        assertEquals(2, utilisateurService.findAllUtilisateurs().size());
//    }
//
//    @Test
//    public void testActiviteTriParTitre() {
//        ArrayList<Activite> activites = activiteService.findAllActivites();
//        assertEquals(lindyhop.getTitre(), activites.get(0).getTitre());
//        assertEquals(randonnee.getTitre(), activites.get(1).getTitre());
//        assertEquals(taekwondo.getTitre(), activites.get(2).getTitre());
//    }
//
//    @Test
//    public void testUtilisateursTriParNom() {
//        ArrayList<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
//        assertEquals(mary.getNom(), utilisateurs.get(0).getNom());
//        assertEquals(thom.getNom(), utilisateurs.get(1).getNom());
//    }
//
//    @Test
//    @Transactional
//    public void testNombreDActivitesParUtilisateur() {
//        ArrayList<Utilisateur> utilisateurs = utilisateurService.findAllUtilisateurs();
//        assertEquals(1, utilisateurs.get(0).getActivites().size());
//        assertEquals(2, utilisateurs.get(1).getActivites().size());
//    }

}