package tenniscourts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tenniscourts.entities.Court;
import tenniscourts.entities.CourtType;
import tenniscourts.entities.Client;
import tenniscourts.storage.CourtRepository;
import tenniscourts.storage.CourtTypeRepository;
import tenniscourts.storage.ClientRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Emma Sommerova
 */

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CourtRepository courtRepo,
                               CourtTypeRepository courtTypeRepo,
                               ClientRepository userRepo) {
        Logger log = LoggerFactory.getLogger(DataLoader.class);
        List<CourtType> courtTypes = getCourtTypes();
        List<Client> users = getUsers();
        return args -> {
            log.info("Load" + userRepo.saveAll(users));
            log.info("Load " + courtTypeRepo.saveAll(courtTypes));
            log.info("Load " + courtRepo.save(new Court(courtTypes.get(0))));
            log.info("Load " + courtRepo.save(new Court(courtTypes.get(1))));
            log.info("Load " + courtRepo.save(new Court(courtTypes.get(2))));
        };


    }

    List<CourtType> getCourtTypes() {
        List<CourtType> courtTypes = new ArrayList<>();
        courtTypes.add(new CourtType("grass", new BigDecimal(100)));
        courtTypes.add(new CourtType("clay", new BigDecimal(90)));
        courtTypes.add(new CourtType("hard", new BigDecimal(80)));
        return courtTypes;
    }

    List<Client> getUsers(){
        List<Client> users = new ArrayList<>();
        users.add(new Client("Bilbo Baggins", "420"));
        users.add(new Client("Saruman", "421"));
        return users;
    }
}
