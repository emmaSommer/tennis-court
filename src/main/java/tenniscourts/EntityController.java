package tenniscourts;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Emma Sommerova
 */
public abstract class EntityController<T extends SystemEntity<T>> {

    public abstract CollectionModel<EntityModel<T>> getAll();

    public abstract EntityModel<T> getEntity(Long id);

    public EntityModel<T> getEntity(Long id, T entity){
        if (entity == null) {
            throw new RuntimeException(String.valueOf(id));
        }
        return entity.toModel();
    }

    public CollectionModel<EntityModel<T>> getAll(List<T> foundEntities){
        List<EntityModel<T>> entities =  foundEntities
                .stream()
                .map(SystemEntity::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                entities,
                linkTo(methodOn(this.getClass()).getAll()).withSelfRel()
        );
    }

}
