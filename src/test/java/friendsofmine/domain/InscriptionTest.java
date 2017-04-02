package friendsofmine;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Inscription;
import friendsofmine.domain.Utilisateur;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class InscriptionTest {

    private static Validator validator;

    private Date date;
    private Utilisateur util, resp;
    private Activite act;

    @BeforeClass
    public static void setupContext() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setupData() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date = cal.getTime();
        util = new Utilisateur("Dupuis", "Bernard", "bernie@domain.com", "M");
        resp = new Utilisateur("Dupond", "Sofia", "sof@domain.com", "F");
        act = new Activite("Chant", "Cours particulier uniquement", resp);
    }

    @Test
    public void testActiviteEtParticipantEtDateNonNull() {
        Inscription inscription = new Inscription(util, act, date);
        assertTrue(validator.validate(inscription).size() == 0);
    }


    @Test
    public void testParticipantNull() {
        Inscription inscription = new Inscription(null, act, date);
        assertTrue(validator.validate(inscription).size() != 0);
    }

    @Test
    public void testActiviteNull() {
        Inscription inscription = new Inscription(util, null, date);
        assertTrue(validator.validate(inscription).size() != 0);
    }

    @Test
    public void testDateInscriptionNull() {
        Inscription inscription = new Inscription(util, act, null);
        assertTrue(validator.validate(inscription).size() == 0);
    }
}