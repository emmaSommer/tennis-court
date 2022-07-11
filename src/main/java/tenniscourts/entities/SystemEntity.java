package tenniscourts.entities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import tenniscourts.controllers.EntityController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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
                linkTo(methodOn(controller.getClass()).getCollectionModel()).withRel(entity.getRootName())
        );
    }

    public static <S extends SystemEntity> CollectionModel<EntityModel<S>> toCollectionModel(List<S> entities, EntityController<S> controller){
        return CollectionModel.of(
                entities.stream()
                        .map(entity -> SystemEntity.toModel(entity, controller))
                        .collect(Collectors.toList()),
                linkTo(methodOn(controller.getClass()).getCollectionModel()).withRel(controller.getRootName())
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

    /**
     * @return true if the entity is in valid state
     */
    public abstract boolean isValid();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SystemEntity entity = (SystemEntity) o;
        return entity.getId() == null ||
                this.getId() == null ||
                entity.getId().equals(this.getId());

    }
}
