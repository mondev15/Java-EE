package friendsofmine.repositories;

import friendsofmine.domain.Utilisateur;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
/**
 * Created by mundial on 14/03/2017.
 */
public interface UtilisateurRepository  extends CrudRepository<Utilisateur,Long>,PagingAndSortingRepository<Utilisateur,Long>  {
}
