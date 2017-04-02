package friendsofmine.service;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Utilisateur;
//import friendsofmine.service.ActiviteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiviteServiceTest {

    @Autowired
    private ActiviteService activiteService;

    private Activite act, act1, act2;
    private Utilisateur thom = new Utilisateur("thom", "yorke", "thom@yorke.fr", "M");
    private Utilisateur mary = new Utilisateur("mary", "yorke", "mary@yorke.fr", "F");


    @Before
    public void setup() {
        act = new Activite("titre", "descriptif", thom);
        act1 = new Activite("titre1", "descriptif1", thom);
        act2 = new Activite("titre2", "descriptif2", mary);
        activiteService.saveActivite(act1);
    }

    @Test
    public void testSaveActiviteHasId(){
        assertNull(act.getId());
        activiteService.saveActivite(act);
        assertNotNull(act.getId());
    }

    @Test
    public void testSaveActiviteSaveResponsable() {
        assertEquals(0, mary.getActivites().size());
        activiteService.saveActivite(act2);
        assertEquals(1, mary.getActivites().size());
    }

    @Test
    public void testSaveActiviteSaveResponsable2() {
        assertEquals(1, thom.getActivites().size());
        activiteService.saveActivite(act);
        assertEquals(2, thom.getActivites().size());
    }

    @Test
    public void testSaveActiviteSaveResponsable3() {
        assertEquals(1, thom.getActivites().size());
        activiteService.saveActivite(act1);
        assertEquals(1, thom.getActivites().size());
    }

    @Test
    public void testSaveActiviteAjouterActiviteAuResponsable() {
        assertFalse(mary.getActivites().contains(act2));
        activiteService.saveActivite(act2);
        assertTrue(mary.getActivites().contains(act2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveActiviteNull(){
        activiteService.saveActivite(null);
    }

    @Test
    public void testActiviteRecupereeNotNull() {
        Activite fetched = activiteService.findOneActivite(act1.getId());
        assertNotNull(fetched);
    }

    @Test
    public void testActiviteRecupereeInchangee() {
        Activite fetched = activiteService.findOneActivite(act1.getId());
        assertEquals(fetched.getId(), act1.getId());
        assertEquals(fetched.getDescriptif(), act1.getDescriptif());
    }


    @Test
    @Transactional
    public void testActiviteMiseAJour() {
        Activite fetched = activiteService.findOneActivite(act1.getId());
        fetched.setDescriptif("Nouvelle description");
        activiteService.saveActivite(fetched);
        Activite fetchedUpdated = activiteService.findOneActivite(fetched.getId());
        assertEquals(fetched.getDescriptif(), fetchedUpdated.getDescriptif());
    }

    @Test
    @Transactional
    public void testNombreDActivitePersisteeApresMiseAJour() {
        long count = activiteService.countActivite();
        Activite fetched = activiteService.findOneActivite(act1.getId());
        fetched.setDescriptif("Nouvelle description");
        activiteService.saveActivite(fetched);
        assertEquals(count, activiteService.countActivite());
    }

    @Test
    public void testTypeRepository() {
        assertThat(activiteService.getActiviteRepository(), instanceOf(PagingAndSortingRepository.class));
    }

}