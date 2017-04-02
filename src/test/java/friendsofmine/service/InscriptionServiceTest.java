package friendsofmine.service;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Inscription;
import friendsofmine.domain.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InscriptionServiceTest {

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ActiviteService activiteService;

    private Calendar cal;
    private Inscription ins, ins1;
    private Utilisateur util, resp;
    private Activite act;

    @Before
    public void setup() {
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        util = new Utilisateur("Dupuis", "Bernard", "bernie@domain.com", "M");
        utilisateurService.saveUtilisateur(util);

        resp = new Utilisateur("Dupond", "Sofia", "sof@domain.com", "F");
        act = new Activite("Chant", "Cours particulier uniquement", resp);
        activiteService.saveActivite(act);

        ins = new Inscription(util, act, date);
        inscriptionService.saveInscription(ins);
        ins1 = new Inscription(util, act, date);
    }

    @Test
    public void testSaveUtilisateur(){
        assertNull(ins1.getId());
        inscriptionService.saveInscription(ins1);
        assertNotNull(ins1.getId());
    }

    @Test
    public void testInscriptionRecupereeNotNull() {
        Inscription fetched = inscriptionService.findOneInscription(ins.getId());
        assertNotNull(fetched);
    }

    @Test
    public void testInscriptionRecupereeInchangee() {
        Inscription fetched = inscriptionService.findOneInscription(ins.getId());
        assertEquals(fetched.getId(), ins.getId());
        assertEquals(fetched.getActivite().getTitre(), ins.getActivite().getTitre());
        assertEquals(fetched.getParticipant().getNom(), ins.getParticipant().getNom());
    }

    @Test
    public void testInscriptionMiseAJourIdPreserve() {
        Long idAvantMiseAJour = ins.getId();
        Inscription fetched = inscriptionService.findOneInscription(idAvantMiseAJour);
        fetched.setDateInscription(cal.getTime());
        inscriptionService.saveInscription(fetched);
        assertEquals(idAvantMiseAJour, fetched.getId());
    }

    @Test
    public void testInscriptionMiseAJourPreserve() {
        Inscription fetched = inscriptionService.findOneInscription(ins.getId());
        fetched.setDateInscription(cal.getTime());
        inscriptionService.saveInscription(fetched);
        Inscription fetchedUpdated = inscriptionService.findOneInscription(fetched.getId());
        assertEquals(fetched.getDateInscription(), fetchedUpdated.getDateInscription());
    }

    @Test
    public void testNombreDUtilisateurPersisteeApresMiseAJour() {
        long count = inscriptionService.countInscription();
        Inscription fetched = inscriptionService.findOneInscription(ins.getId());
        fetched.setDateInscription(cal.getTime());
        inscriptionService.saveInscription(fetched);
        assertEquals(count, inscriptionService.countInscription());
    }

    @Test
    public void testUneInscriptionPersisteeAUneDateDInscriptionNonNull() {
        Inscription inscription = new Inscription(util, act, null);
        inscriptionService.saveInscription(inscription);
        assertNotNull(inscription.getDateInscription());
    }

    @Test
    public void testTypeRepository() {
        assertThat(inscriptionService.getInscriptionRepository(), instanceOf(PagingAndSortingRepository.class));
    }

}