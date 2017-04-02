package friendsofmine.domain;

//import friendsofmine.domain.Utilisateur;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import org.junit.runner.RunWith;
import static org.junit.Assert.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Date;

public class UtilisateurTest {

    private static Validator validator;
    private Calendar cal;
    private Date date;

    @BeforeClass
    public static void setupContext() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setupData() {
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
    }

    @Test
    public void testUtilisateurFemme() {
        Utilisateur util = new Utilisateur("Dupont", "Jeanne", "jd@jd.com", "F", date);
        assertTrue(validator.validate(util).size() == 0);
    }

    @Test
    public void testUtilisateurHomme() {
        Utilisateur util = new Utilisateur("Durand", "Jacques", "jd@jd.com", "M", date);
        assertTrue(validator.validate(util).size() == 0);
    }

    @Test
    public void testUtilisateurSansDate() {
        Utilisateur util = new Utilisateur("Durand", "Jacques", "jd@jd.com", "M");
        assertTrue(validator.validate(util).size() == 0);
    }

    @Test
    public void testUtilisateurNomNull() {
        Utilisateur util = new Utilisateur(null, "Jacques", "jd@jd.com", "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurNomVide() {
        Utilisateur util = new Utilisateur("", "Jacques", "jd@jd.com", "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurPrenomNull() {
        Utilisateur util = new Utilisateur("Durand", null, "jd@jd.com", "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurPrenomVide() {
        Utilisateur util = new Utilisateur("Durand", "", "jd@jd.com", "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurEmailSansArobase() {
        Utilisateur util = new Utilisateur("Durand", "Eric", "jd.jd.com", "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurEmailNull() {
        Utilisateur util = new Utilisateur("Durand", "Eric", null, "M");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurSexeInvalide() {
        Utilisateur util = new Utilisateur("Durand", "Eric", "jd@jd.com", "V");
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUtilisateurSexeNull() {
        Utilisateur util = new Utilisateur("Durand", "Eric", "jd@jd.com", null);
        assertTrue(validator.validate(util).size() > 0);
    }

    @Test
    public void testUnNouvelUtilisateurEstSansActivite() {
        Utilisateur util = new Utilisateur("Durand", "Eric", "jd@jd.com", "M");
        assertEquals(0, util.getActivites().size());
    }

}