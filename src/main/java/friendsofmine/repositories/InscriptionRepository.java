package friendsofmine.repositories;
import friendsofmine.domain.Inscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by mundial on 23/03/2017.
 */
public interface InscriptionRepository extends CrudRepository<Inscription,Long>,PagingAndSortingRepository<Inscription,Long> {
}
