package tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

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

    public JpaRepository<T, Long> getRepository() {
        return repository;
    }

    public EntityModel<T> getEntityModel(Long id){
        return getEntity(id).toModel();
    }

    public T getEntity(Long id){
        return repository.findById(id).orElseThrow();
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

    public EntityModel<T> addEntity(T entity){
        if (entity == null){
            throw new IllegalArgumentException();
        }
        return repository.save(entity).toModel();
    }

    public CollectionModel<EntityModel<T>> deleteEntity(Long id){
        repository.deleteById(id);
        return getAll();
    }

    public EntityModel<T> updateEntity(Long id, T newEntity){
        Optional<T> entity = repository.findById(id);
        if(entity.isEmpty()){
            return addEntity(newEntity);
        }
        return null;
    }

}
