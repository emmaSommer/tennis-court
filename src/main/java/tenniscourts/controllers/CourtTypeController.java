package tenniscourts.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tenniscourts.entities.CourtType;
import tenniscourts.storage.CourtTypeRepository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Emma Sommerova
 */

@RestController
public class CourtTypeController extends EntityController<CourtType> {

    public CourtTypeController(CourtTypeRepository repository) {
        super(repository);
    }


    @GetMapping("/court_types")
    @Override
    public CollectionModel<EntityModel<CourtType>> getAll() {
        return super.getAll();
    }

    @Override
    public String getRootName() {
        return "court_types";
    }

    @GetMapping("/court_types/{id}")
    @Override
    public EntityModel<CourtType> getEntityModel(@PathVariable Long id) {
        return super.getEntityModel(id);
    }


    public EntityModel<CourtType> addEntity(String name, BigDecimal price){
        CourtType courtType = new CourtType(name, price);
        return super.addEntity(courtType);
    }

    @Override
    public CollectionModel<EntityModel<CourtType>> deleteEntity(Long id) {
        // TODO check if no courts exist
        return super.deleteEntity(id);
    }

    @Override
    public EntityModel<CourtType> updateEntity(Long id, CourtType newEntity) {
        Optional<CourtType> oldEntity = super.getRepository().findById(id);
        if (oldEntity.isEmpty()){
            return super.addEntity(newEntity);
        }
        CourtType entity = oldEntity.orElseThrow();
        entity.setName(newEntity.getName());
        entity.setPrice(newEntity.getPrice());
        return super.addEntity(entity);
    }
}
