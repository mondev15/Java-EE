package friendsofmine.controllers;


import friendsofmine.domain.Activite;
import friendsofmine.domain.Utilisateur;
import friendsofmine.service.ActiviteService;
import friendsofmine.service.UtilisateurService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UtilisateurControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ActiviteService activiteService;

    private Utilisateur util, utilNonSauve;

    @Before
    public void setup() {
        util = new Utilisateur("Doe", "John", "john@doe.com", "M");
        utilisateurService.saveUtilisateur(util);
        utilNonSauve = new Utilisateur("Morrissey", "Steven Patrick", "momo@rrissey.co.uk", "M");
    }

    @Test
    public void testGetUtilisateurs() throws Exception{
        mockMvc.perform(get("/utilisateurs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurs"))
                .andExpect(content().string(Matchers.containsString("<h2>Liste des Utilisateurs</h2>")))
                .andDo(print());
    }

    @Test
    public void testReadUtilisateurIdValide() throws Exception{
        mockMvc.perform(get("/utilisateur/" + util.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurShow"))
                .andExpect(content().string(Matchers.containsString(util.getNom())))
                .andExpect(content().string(Matchers.containsString(util.getPrenom())))
                .andExpect(content().string(Matchers.containsString(util.getEmail())))
                .andDo(print());
    }

    @Test
    public void testReadUtilisateurIdInvalide() throws Exception{
        mockMvc.perform(get("/utilisateur/" + (util.getId() + 1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andDo(print());
    }

    @Test
    public void testCreateUtilisateur() throws Exception{
        mockMvc.perform(get("/utilisateur/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurForm"))
                .andDo(print());
    }

    @Test
    public void testCreateUtilisateurValide() throws Exception{
        mockMvc.perform(post("/utilisateur")
                .param("nom", utilNonSauve.getNom())
                .param("prenom", utilNonSauve.getPrenom())
                .param("email", utilNonSauve.getEmail())
                .param("sexe", utilNonSauve.getSexe()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("/utilisateur/*"))
                .andDo(print());
    }

    @Test
    public void testCreateUtilisateurNomInvalide() throws Exception{
        mockMvc.perform(post("/utilisateur")
                .param("nom", "")
                .param("prenom", utilNonSauve.getPrenom())
                .param("email", utilNonSauve.getEmail())
                .param("sexe", utilNonSauve.getSexe()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurForm"))
                .andExpect(content().string(Matchers.containsString("size must be between 1 and")))
                .andDo(print());
    }

    @Test
    public void testCreateUtilisateurPrenomInvalide() throws Exception{
        mockMvc.perform(post("/utilisateur")
                .param("nom", utilNonSauve.getNom())
                .param("prenom", "")
                .param("email", utilNonSauve.getEmail())
                .param("sexe", utilNonSauve.getSexe()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurForm"))
                .andExpect(content().string(Matchers.containsString("size must be between 1 and")))
                .andDo(print());
    }

    @Test
    public void testCreateUtilisateurEmailInvalide() throws Exception{
        mockMvc.perform(post("/utilisateur")
                .param("nom", utilNonSauve.getNom())
                .param("prenom", utilNonSauve.getPrenom())
                .param("email", "toto")
                .param("sexe", utilNonSauve.getSexe()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurForm"))
                .andExpect(content().string(Matchers.containsString("not a well-formed email address")))
                .andDo(print());
    }

    @Test
    public void testEditUtilisateurIdValide() throws Exception{
        Long savId = util.getId();
        mockMvc.perform(get("/utilisateur/edit/" + savId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("utilisateurForm"))
                .andExpect(content().string(Matchers.containsString(util.getNom())))
                .andExpect(content().string(Matchers.containsString(util.getPrenom())))
                .andExpect(content().string(Matchers.containsString(util.getEmail())))
                .andExpect(model().attribute("utilisateur", hasProperty("id", is(savId))))
                .andDo(print());
    }

    @Test
    public void testEditUtilisateurIdInvalide() throws Exception{
        mockMvc.perform(get("/utilisateur/edit/" + (util.getId() + 1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andDo(print());
    }

    @Test
    public void testDeleteUtilisateur() throws Exception {
        long count = utilisateurService.countUtilisateur();
        assertTrue(count > 0);
        mockMvc.perform(delete("/utilisateur/delete/" + util.getId()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/utilisateurs"))
                .andDo(print());
        assertEquals(count - 1, utilisateurService.countUtilisateur());
    }

    @Test
    public void testDeleteUtilisateurAvecActivite() throws Exception {
        long count = utilisateurService.countUtilisateur();
        Activite act = new Activite("Football", "Le mardi soir", util);
        activiteService.saveActivite(act);
        assertTrue(util.getActivites().size() > 0);
        mockMvc.perform(delete("/utilisateur/delete/" + util.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("error"))
                .andExpect(content().string(Matchers.containsString("Impossible. L&#39;utilisateur est responsable d&#39;activités")))
                .andDo(print());
        assertEquals(count, utilisateurService.countUtilisateur());
    }

//
//    @Test
//    public void testGetUtilisateurs() throws Exception{
//        mockMvc.perform(get("/utilisateurs"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurs"))
//                .andExpect(content().string(Matchers.containsString("<h2>Liste des Utilisateurs</h2>")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testReadUtilisateurIdValide() throws Exception{
//        mockMvc.perform(get("/utilisateur/" + util.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurShow"))
//                .andExpect(content().string(Matchers.containsString(util.getNom())))
//                .andExpect(content().string(Matchers.containsString(util.getPrenom())))
//                .andExpect(content().string(Matchers.containsString(util.getEmail())))
//                .andDo(print());
//    }
//
//    @Test
//    public void testReadUtilisateurIdInvalide() throws Exception{
//        mockMvc.perform(get("/utilisateur/" + (util.getId() + 1)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("error"))
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUtilisateur() throws Exception{
//        mockMvc.perform(get("/utilisateur/new"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurForm"))
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUtilisateurValide() throws Exception{
//        mockMvc.perform(post("/utilisateur")
//                .param("nom", utilNonSauve.getNom())
//                .param("prenom", utilNonSauve.getPrenom())
//                .param("email", utilNonSauve.getEmail())
//                .param("sexe", utilNonSauve.getSexe()))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrlPattern("/utilisateur/*"))
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUtilisateurNomInvalide() throws Exception{
//        mockMvc.perform(post("/utilisateur")
//                .param("nom", "")
//                .param("prenom", utilNonSauve.getPrenom())
//                .param("email", utilNonSauve.getEmail())
//                .param("sexe", utilNonSauve.getSexe()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurForm"))
//                .andExpect(content().string(Matchers.containsString("size must be between 1 and")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUtilisateurPrenomInvalide() throws Exception{
//        mockMvc.perform(post("/utilisateur")
//                .param("nom", utilNonSauve.getNom())
//                .param("prenom", "")
//                .param("email", utilNonSauve.getEmail())
//                .param("sexe", utilNonSauve.getSexe()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurForm"))
//                .andExpect(content().string(Matchers.containsString("size must be between 1 and")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUtilisateurEmailInvalide() throws Exception{
//        mockMvc.perform(post("/utilisateur")
//                .param("nom", utilNonSauve.getNom())
//                .param("prenom", utilNonSauve.getPrenom())
//                .param("email", "toto")
//                .param("sexe", utilNonSauve.getSexe()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurForm"))
//                .andExpect(content().string(Matchers.containsString("not a well-formed email address")))
//                .andDo(print());
//    }
//
//    @Test
//    public void testEditUtilisateurIdValide() throws Exception{
//        Long savId = util.getId();
//        mockMvc.perform(get("/utilisateur/edit/" + savId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("utilisateurForm"))
//                .andExpect(content().string(Matchers.containsString(util.getNom())))
//                .andExpect(content().string(Matchers.containsString(util.getPrenom())))
//                .andExpect(content().string(Matchers.containsString(util.getEmail())))
//                .andExpect(model().attribute("utilisateur", hasProperty("id", is(savId))))
//                .andDo(print());
//    }
//
//    @Test
//    public void testEditUtilisateurIdInvalide() throws Exception{
//        mockMvc.perform(get("/utilisateur/edit/" + (util.getId() + 1)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("error"))
//                .andDo(print());
//    }

}
