package tenniscourts.entities;

import org.springframework.hateoas.EntityModel;
import tenniscourts.controllers.EntityController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */
public abstract class SystemEntity {


    public static <S extends SystemEntity> EntityModel<S> toModel(S entity, EntityController<S> controller) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(controller.getClass()).getEntityModel(entity.getId())).withSelfRel(),
                linkTo(methodOn(controller.getClass()).getAll()).withRel(entity.getRootName())
        );
    }

    public abstract Long getId();

    public abstract String getRootName();

}
