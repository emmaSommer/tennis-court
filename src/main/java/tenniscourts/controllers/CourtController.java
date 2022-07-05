package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import tenniscourts.entities.Client;
import tenniscourts.entities.CourtType;
import tenniscourts.entities.Court;
import tenniscourts.storage.CourtRepository;

import java.util.Optional;

/**
 * @author Emma Sommerova
 */

@RestController
public class CourtController extends EntityController<Court> {

    public static final String rootName = "courts";

    CourtController(CourtRepository repository) {
        super(repository);
    }

    @GetMapping("/courts")
    @Override
    public CollectionModel<EntityModel<Court>> getAll() {
        return super.getAll();
    }

    @Override
    public String getEntityName() {
        return Court.entityName;
    }

    @GetMapping("/courts/{id}")
    @Override
    public EntityModel<Court> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }

    public EntityModel<Court> addEntity(CourtType courtType){
        Court court = new Court(courtType);
        return super.addEntity(court);
    }

    public EntityModel<Court> updateEntity(Long id, Court newEntity){

        Optional<Court> oldEntity = super.getRepository().findById(id);
        if (oldEntity.isEmpty()){
            return super.addEntity(newEntity);
        }
        Court entity = oldEntity.orElseThrow();
        entity.setType(newEntity.getType());
        return super.addEntity(entity);
    }
}