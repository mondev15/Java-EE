package friendsofmine.service;

import friendsofmine.domain.Utilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilisateurServiceTest {

    @Autowired
    private UtilisateurService utilisateurService;


    private Utilisateur util, util1;

    @Before
    public void setup() {
        util = new Utilisateur("nom", "prenom", "toto@toto.fr", "F");
        util1 = new Utilisateur("nom1", "prenom1", "toto@toto.fr", "F");
        utilisateurService.saveUtilisateur(util1);
    }

    @Test
    public void testSaveUtilisateur(){
        assertNull(util.getId());
        utilisateurService.saveUtilisateur(util);
        assertNotNull(util.getId());
    }

    @Test
    public void testUtilisateurRecupereeNotNull() {
        Utilisateur fetched = utilisateurService.findOneUtilisateur(util1.getId());
        assertNotNull(fetched);
    }

    @Test
    public void testUtilisateurRecupereeInchangee() {
        Utilisateur fetched = utilisateurService.findOneUtilisateur(util1.getId());
        assertEquals(fetched.getId(), util1.getId());
        assertEquals(fetched.getEmail(), util1.getEmail());
    }

    @Test
    public void testUtilisateurMiseAJour() {
        Utilisateur fetched = utilisateurService.findOneUtilisateur(util1.getId());
        fetched.setEmail("tutu@tutu.fr");
        utilisateurService.saveUtilisateur(fetched);
        Utilisateur fetchedUpdated = utilisateurService.findOneUtilisateur(fetched.getId());
        assertEquals(fetched.getEmail(), fetchedUpdated.getEmail());
    }

    @Test
    public void testNombreDUtilisateurPersisteeApresMiseAJour() {
        long count = utilisateurService.countUtilisateur();
        Utilisateur fetched = utilisateurService.findOneUtilisateur(util1.getId());
        fetched.setEmail("titi@titi.fr");
        utilisateurService.saveUtilisateur(fetched);
        assertEquals(count, utilisateurService.countUtilisateur());
    }

    @Test
    public void testTypeRepository() {
        assertThat(utilisateurService.getUtilisateurRepository(), instanceOf(PagingAndSortingRepository.class));
    }

}