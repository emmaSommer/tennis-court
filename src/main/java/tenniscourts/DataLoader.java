package tenniscourts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tenniscourts.entities.*;
import tenniscourts.storage.CourtRepository;
import tenniscourts.storage.CourtTypeRepository;
import tenniscourts.storage.ClientRepository;
import tenniscourts.storage.ReservationRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
                               ClientRepository userRepo,
                               ReservationRepository reservationRepo) {
        Logger log = LoggerFactory.getLogger(DataLoader.class);
        List<CourtType> courtTypes = getCourtTypes();
        List<Court> courts = getCourts(courtTypes);
        List<Client> users = getUsers();
        List<Reservation> reservations = getReservations(courts, users);
        return args -> {
            log.info("Load" + userRepo.saveAll(users));
            log.info("Load " + courtTypeRepo.saveAll(courtTypes));
            log.info("Load " + courtRepo.saveAll(courts));
            log.info("Load " + reservationRepo.saveAll(reservations));
        };


    }

    private List<Reservation> getReservations(List<Court> courts, List<Client> clients) {
        List<Reservation> reservations = new ArrayList<>();
        Date date = Date.valueOf(LocalDate.now());
        for (Court court: courts
             ) {
            for (Client client: clients
                 ) {
                reservations.add(new Reservation(date, date, court, client, PlayType.SINGLES_PLAY));

            }

        }
        return reservations;
    }

    private List<Court> getCourts(List<CourtType> courtTypes) {
        List<Court> courts = new ArrayList<>();
        for (CourtType type:courtTypes
             ) {
            courts.add(new Court(type));
        }
        return courts;
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
