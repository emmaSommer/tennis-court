package tenniscourts;

import org.springframework.hateoas.EntityModel;

/**
 * @author Emma Sommerova
 */
public abstract class SystemEntity<T> {

    public abstract EntityModel<T> toModel();
}
