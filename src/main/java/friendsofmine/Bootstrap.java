package friendsofmine;

import friendsofmine.service.InitialisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by mundial on 02/03/2017.
 */

@Component
public class Bootstrap {

    @Autowired
    private InitialisationService initialisationService;

    //l'annotation @PostConstruct est utilisée pour une méthode qui a besoin d'être exécutée
    // après une injection de dépendance
    //pour réaliser une initialisation
    @PostConstruct
    public void init(){
        try{
        this.initialisationService.initDonnees();
            //ajout de l'inscription
            this.initialisationService.initInscriptions();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public InitialisationService getInitialisationService(){
        return this.initialisationService;
    }

    public void setInitialisationService(InitialisationService initialisationService){
        this.initialisationService= initialisationService;
    }
}
