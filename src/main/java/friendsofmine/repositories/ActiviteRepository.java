package friendsofmine.repositories;

import friendsofmine.domain.Activite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ActiviteRepository extends CrudRepository<Activite,Long>,PagingAndSortingRepository<Activite,Long> {

}
