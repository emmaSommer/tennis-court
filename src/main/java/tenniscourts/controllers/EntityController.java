package tenniscourts.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import tenniscourts.entities.SystemEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Abstract class for managing system entities
 *
 * @author Emma Sommerova
 */
public abstract class EntityController<T extends SystemEntity> {

    private final JpaRepository<T, Long> repository;

    /**
     * Constructor
     *
     * @param repository storing the entities
     */
    public EntityController(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }


    /**
     * @return entity managed by the controller
     */
    public abstract String getEntityName();

    public JpaRepository<T, Long> getRepository() {
        return repository;
    }

    /**
     * @param id of the entity to be modeled
     * @return REST model of the entity
     */
    public EntityModel<T> getEntityModel(Long id) {
        return SystemEntity.toModel(getEntity(id), this);
    }

    /**
     * @param id of the entity to be found
     * @return entity with the given id
     * @throws EntityNotFoundException if the entity does not
     *                                 exist in the repository
     */
    public T getEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getEntityName(), id));
    }

    /**
     * @return REST model of a collection of all the
     * entities in the repository
     */
    public CollectionModel<EntityModel<T>> getAll() {
        List<EntityModel<T>> entities = this.repository.findAll()
                .stream()
                .map(entity -> SystemEntity.toModel(entity, this))
                .collect(Collectors.toList());
        return CollectionModel.of(
                entities,
                linkTo(methodOn(this.getClass()).getAll()).withSelfRel()
        );
    }

    /**
     * @param entity to be added to the repository
     * @return REST model of the new entity
     */
    public EntityModel<T> addEntity(T entity) {
        if (entity == null || !entity.isValid()) {
            // todo add error message
            throw new IllegalArgumentException();
        }
        repository.save(entity);
        return SystemEntity.toModel(entity, this);
    }

    /**
     * @param id of the entity to be deleted
     * @return REST model of the collection without the
     * deleted entity
     */
    public CollectionModel<EntityModel<T>> deleteEntity(Long id) {
        repository.deleteById(id);
        return getAll();
    }

    /**
     * Updated values in the entity in the repository with the given id
     * if the entity does not exists saves the newEntity into the repository
     *
     * @param id        of the original entity
     * @param newEntity entity with attributes to be given to the original
     * @return REST model of the updated entity
     */
    public EntityModel<T> updateEntity(Long id, T newEntity) {
        if (newEntity == null || !newEntity.isValid()){
            // todo
            throw new IllegalArgumentException();
        }
        try {
            T entity = getEntity(id);
            entity.cloneAttributes(newEntity);
            return addEntity(entity);

        } catch (EntityNotFoundException e){
            throw new EntityNotFoundException("Attempting to update entity not found in repository");
        }
    }

}
