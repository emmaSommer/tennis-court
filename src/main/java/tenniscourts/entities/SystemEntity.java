package tenniscourts.entities;

import org.springframework.hateoas.EntityModel;
import tenniscourts.controllers.EntityController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Abstract class representing a system entity
 *
 * @author Emma Sommerova
 */
public abstract class SystemEntity {


    /**
     * @param entity     to be represented
     * @param controller of the given entity
     * @param <S>        entity type
     * @return REST model of the entity
     */
    public static <S extends SystemEntity> EntityModel<S> toModel(S entity, EntityController<S> controller) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(controller.getClass()).getEntityModel(entity.getId())).withSelfRel(),
                linkTo(methodOn(controller.getClass()).getAll()).withRel(entity.getRootName())
        );
    }

    /**
     * @return unique identification of the entity
     */
    public abstract Long getId();

    /**
     * @return name under which are entities aggregated
     */
    public abstract String getRootName();

    /**
     * Changes attributes with the values from newEntity
     *
     * @param newEntity with attribute values to be cloned
     */
    public abstract void cloneAttributes(SystemEntity newEntity);

}
