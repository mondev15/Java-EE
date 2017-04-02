package friendsofmine.controllers;

import friendsofmine.Bootstrap;
import friendsofmine.service.ActiviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mundial on 03/03/2017.
 */
@Controller
public class ActiviteController{

  //@Autowired
  //private BootStrap bootStrap;
    @Autowired
    private ActiviteService activiteService;

 @RequestMapping(value="/activites", method= RequestMethod.GET)
 public String list(Model model){
    //modification pour ne plus faire référence à bootstrap
     model.addAttribute("activites",activiteService.findAllActivites());
   return "activites";
 }
}
