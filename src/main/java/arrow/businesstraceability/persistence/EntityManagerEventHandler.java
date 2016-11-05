package arrow.businesstraceability.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.framework.persistence.BaseEntity;
import arrow.framework.persistence.event.qualifier.EntityPersisted;
import arrow.framework.persistence.event.qualifier.EntityPersistedOrUpdated;

/**
 * The Class EntityManagerEventHandler.
 */
@Named
@ApplicationScoped
public class EntityManagerEventHandler implements Serializable {

    /**
     * On entity persisted or updated.
     *
     * @param event the event
     */
    public void onEntityPersistedOrUpdated(
            @Observes @EntityPersisted(value = Employee_mst.class) final BaseEntity event) {
        System.out.println("Employee " + event.toString() + " persisted");
    }

    /**
     * On entity persisted or updated all entity class.
     *
     * @param entity the entity
     */
    public static void onEntityPersistedOrUpdatedAllEntityClass(
            @Observes @EntityPersistedOrUpdated(Abstract_entity.class) final BaseEntity entity) {
        System.out.println("Entity:" + entity.toString() + " persisted");
    }
}
