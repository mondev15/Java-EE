package friendsofmine.service;

import friendsofmine.domain.Utilisateur;
import friendsofmine.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public Utilisateur findOneUtilisateur(Long id) {
        return this.utilisateurRepository.findOne(id);
    }


    public ArrayList<Utilisateur> findAllUtilisateurs(){
        //trier les utilisateurs par ordre croissant de nom
        Iterable<Utilisateur> utilisateurs = utilisateurRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"nom")));
        ArrayList<Utilisateur> utilisateursList = new ArrayList<Utilisateur>();

        for(Utilisateur ut : utilisateurs){
            utilisateursList.add(ut);
        }
        return utilisateursList;
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur){
        //pour faire passer testsaveUtilisateurNull
        if (utilisateur==null){
            throw new IllegalArgumentException("argument is null");
        }
        return this.utilisateurRepository.save(utilisateur);
    }


    public long countUtilisateur(){
        return this.utilisateurRepository.count();       }

    public UtilisateurRepository getUtilisateurRepository() {
        return this.utilisateurRepository;
    }

    public void deleteUtilisateur(Long id) {
        this.utilisateurRepository.delete(id);
    }
}
