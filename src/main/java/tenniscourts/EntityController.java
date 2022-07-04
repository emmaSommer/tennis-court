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

    public abstract List<T> getAll();

    public abstract Optional<T> getEntity(Long id);

    public EntityModel<T> findById(Long id){
        T entity = getEntity(id) //
                .orElseThrow(() -> new RuntimeException(String.valueOf(id)));

        return entity.toModel();
    }

    public CollectionModel<EntityModel<T>> findAll(){
        List<EntityModel<T>> entities =  getAll()
                .stream()
                .map(SystemEntity::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                entities,
                linkTo(methodOn(this.getClass()).getAll()).withSelfRel()
        );
    }

}
