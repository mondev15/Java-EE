package friendsofmine.service;

import friendsofmine.domain.Activite;
import friendsofmine.domain.Utilisateur;
import friendsofmine.repositories.ActiviteRepository;
import friendsofmine.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
    public class ActiviteService {
        @Autowired
        private ActiviteRepository activiteRepository;

        @Autowired
        private UtilisateurRepository utilisateurRepository;

        public Activite findOneActivite(Long id) {
            return this.activiteRepository.findOne(id);
        }

        public ArrayList<Activite> findAllActivites(){
            //trier les activites par ordre croissant des titres
            Iterable<Activite> activites=activiteRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"titre")));
            ArrayList<Activite> activitesList = new ArrayList<Activite>();

            for(Activite act : activites){
                activitesList.add(act);
            }
            return activitesList;
        }

        public Activite saveActivite(Activite activite){
            //pour faire passer testsaveActiviteNull
            if (activite==null){
                throw new IllegalArgumentException("activite must not be null");
            }
            //on récupère le responssble et on vérifie s'il n'est pas null
            Utilisateur responsable = activite.getResponsable();
            if(responsable !=null){
                utilisateurRepository.save(responsable);
                responsable.addActivites(activite);
            }
           return  this.activiteRepository.save(activite);
     }

        public long countActivite(){
            return this.activiteRepository.count();       }

        public ActiviteRepository getActiviteRepository() {
                return this.activiteRepository;
        }

}
