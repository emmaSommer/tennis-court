package tenniscourts.controllers;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import tenniscourts.entities.SystemEntity;

import java.util.List;

/**
 * Abstract class for managing system entities
 *
 * @author Emma Sommerova
 */
public abstract class EntityController<T extends SystemEntity> {

    /**
     * @return entity managed by the controller
     */
    public abstract String getEntityName();

    public abstract String getRootName();

    public abstract JpaRepository<T, Long> getRepository();

    /**
     * @param id of the entity to be modeled
     * @return REST model of the entity
     * @throws EntityNotFoundException if the entity does not
     *                                 exist in the repository
     */
    public EntityModel<T> getEntityModel(Long id) {
        return SystemEntity.toModel(getEntity(id), this);
    }

    /**
     * @param id of the entity to be found
     * @return entity with the given id
     * @throws EntityNotFoundException if the entity does not
     *                                 exist in the repository
     * @throws InvalidIdException      if the given id is null
     */
    public T getEntity(Long id) {
        try {
            return getRepository().findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(getEntityName(), id, "retrieving from repository"));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new InvalidIdException("Accessing entity with null id\n" + e.getMessage());
        }


    }

    /**
     * @return list of all entities int the repository
     * @throws EntityNotFoundException if the entity does not
     *                                 exist in the repository
     */
    public List<T> getAll() {
        List<T> entities = getRepository().findAll();
        if (entities.isEmpty()) {
            throw new EntityNotFoundException(getRootName());
        }
        return entities;
    }

    /**
     * @return REST model of a collection of all the
     * entities in the repository
     * @throws EntityNotFoundException if the entity does not
     *                                 exist in the repository
     */
    public CollectionModel<EntityModel<T>> getCollectionModel() {
        return SystemEntity.toCollectionModel(
                getAll(), this);
    }

    /**
     * @param entity to be added to the repository
     * @return saved entity
     * @throws InvalidIdException when the entity is not in a valid state
     */
    public T addEntity(T entity) {
        if (entity == null || !entity.isValid()) {
            throw new InvalidEntityException(entity, "save into repository");
        }
        return getRepository().save(entity);
    }

    /**
     * @param id of the entity to be deleted
     * @return list of remaining entities
     */
    public List<T> deleteEntity(Long id) {
        getRepository().deleteById(id);
        return getRepository().findAll();
    }

    /**
     * Updated values in the entity in the repository with the given id
     * if the entity does not exists saves the newEntity into the repository
     *
     * @param id        of the original entity
     * @param newEntity entity with attributes to be given to the original
     * @return updated entity
     */
    public T updateEntity(Long id, T newEntity) {
        if (newEntity == null || !newEntity.isValid()) {
            throw new InvalidEntityException(newEntity, "clone attributes");
        }
        try {
            T entity = getEntity(id);
            entity.cloneAttributes(newEntity);
            return addEntity(entity);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getEntityName(), e.getId(),
                    "updating entity\n\t" + e.getMessage());
        }
    }

}
