package tenniscourts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tenniscourts.controllers.ClientController;
import tenniscourts.controllers.CourtController;
import tenniscourts.controllers.CourtTypeController;
import tenniscourts.controllers.ReservationController;
import tenniscourts.entities.*;
import tenniscourts.storage.CourtRepository;
import tenniscourts.storage.CourtTypeRepository;
import tenniscourts.storage.ClientRepository;
import tenniscourts.storage.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Emma Sommerova
 */

@Configuration
public class DataLoader {

    private ClientController clientController;
    private CourtController courtController;
    private CourtTypeController courtTypeController;
    private ReservationController reservationController;

    @Bean
    public CommandLineRunner loadData(CourtRepository courtRepo,
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

    private static List<Reservation> getReservations(List<Court> courts, List<Client> clients) {
        List<Reservation> reservations = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        for (Court court : courts
        ) {
            for (Client client : clients
            ) {
                reservations.add(new Reservation(start, end, court, client, PlayType.SINGLES_PLAY));

            }

        }
        return reservations;
    }

    private static List<Court> getCourts(List<CourtType> courtTypes) {
        List<Court> courts = new ArrayList<>();
        for (CourtType type : courtTypes
        ) {
            courts.add(new Court(type));
        }
        return courts;
    }

    private static List<CourtType> getCourtTypes() {
        List<CourtType> courtTypes = new ArrayList<>();
        courtTypes.add(new CourtType("grass", new BigDecimal(10)));
        courtTypes.add(new CourtType("clay", new BigDecimal(9)));
        courtTypes.add(new CourtType("hard", new BigDecimal(8)));
        return courtTypes;
    }

    private static List<Client> getUsers() {
        List<Client> users = new ArrayList<>();
        users.add(new Client("Bilbo Baggins", "420903030030"));
        users.add(new Client("Saruman", "421901131131"));
        return users;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public CourtController getCourtController() {
        return courtController;
    }

    public CourtTypeController getCourtTypeController() {
        return courtTypeController;
    }

    public ReservationController getReservationController() {
        return reservationController;
    }
}
