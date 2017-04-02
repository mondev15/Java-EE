package friendsofmine.service;

import friendsofmine.domain.Inscription;
import friendsofmine.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mundial on 23/03/2017.
 */
@Service
public class InscriptionService {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public Inscription saveInscription(Inscription ins) {

        if (ins.getDateInscription()==null){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2017);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date date = cal.getTime();
            ins.setDateInscription(date);
        }
        return this.inscriptionRepository.save(ins);
    }

    public void deleteInscription(Long id){
        this.inscriptionRepository.delete(id);
    }
    public Inscription findOneInscription(Long id) {
        return this.inscriptionRepository.findOne(id);
    }

    public long countInscription() {
        return this.inscriptionRepository.count();
    }

    public InscriptionRepository getInscriptionRepository() {
        return this.inscriptionRepository;
    }
}
