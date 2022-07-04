package tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;
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

    private final JpaRepository<T, Long> repository;

    public EntityController(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    //public abstract CollectionModel<EntityModel<T>> getAll();

    //public abstract EntityModel<T> getEntity(Long id);

    public EntityModel<T> getEntity(Long id){
        T entity = repository.findById(id).orElseThrow();
        return entity.toModel();
    }

    public CollectionModel<EntityModel<T>> getAll(){
        List<EntityModel<T>> entities =  this.repository.findAll()
                .stream()
                .map(SystemEntity::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(
                entities,
                linkTo(methodOn(this.getClass()).getAll()).withSelfRel()
        );
    }

}
