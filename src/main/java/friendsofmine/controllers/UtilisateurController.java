package friendsofmine.controllers;

import friendsofmine.domain.Utilisateur;
import friendsofmine.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by mundial on 02/03/2017.
 */

@Controller
public class UtilisateurController {

    //injection de dépendance
    //@Autowired
    //private BootStrap bootStrap;
    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value="/utilisateurs",method= RequestMethod.GET)
    public String list(Model model){
        //----modification pour ne plus faire référence à la classe bootstrap
        //model.addAttribute("utilisateurs", bootStrap.getInitialisationService().getUtilisateurs());
        model.addAttribute("utilisateurs", utilisateurService.findAllUtilisateurs());
        return "utilisateurs";
    }

//    //ajouté pour faire passer le test UtilisateurControllerTest
    @GetMapping("utilisateur/{id}")
    public String showUtilisateur(@PathVariable Long id, Model model){
        Utilisateur util = utilisateurService.findOneUtilisateur(id);
        if (util == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        model.addAttribute("utilisateur", util);
        return "utilisateurShow";
    }
//pour creer un utilisateur
    @RequestMapping("/utilisateur/new")
    public String newUtilisateur(Model model){
        model.addAttribute("utilisateur", new Utilisateur());
        return "utilisateurForm";
    }

    //@Préciser qu'un utilisateur doit être valide
    @PostMapping(value = "/utilisateur")
    public String createOrUpdateUtilisateur(@Valid Utilisateur util,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "utilisateurForm";
        }
        utilisateurService.saveUtilisateur(util);
        return "redirect:/utilisateur/" + util.getId();
    }

    //pour editer un utilisateur
    @GetMapping("utilisateur/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Utilisateur util = utilisateurService.findOneUtilisateur(id);
        if (util == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        model.addAttribute("utilisateur", util);
        return "utilisateurForm";
    }

    //pour supprimer un utilisateur
    @DeleteMapping("utilisateur/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        Utilisateur util = utilisateurService.findOneUtilisateur(id);
        if (util == null) {
            model.addAttribute("customMessage", "Impossible. Id non valide");
            return "error";
        }
        if (util.getActivites().size() != 0) {
            model.addAttribute("customMessage", "Impossible. L'utilisateur est responsable d'activités. \n " +
                    "Un nouveau responsable doit être désigné avant de supprimer " +
                    util.getPrenom() + " " + util.getNom() + ".");
            return "error";
        }
        utilisateurService.deleteUtilisateur(id);
        return "redirect:/utilisateurs";
    }
}
