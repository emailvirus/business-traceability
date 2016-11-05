package arrow.framework.persistence.meta.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

import org.apache.deltaspike.core.util.ReflectionUtils;

import arrow.framework.util.collections.ArrayUtils;


/**
 * The Class MetaModelUtils.
 */
public class MetaModelUtils {

    /**
     * Gets the meta model entity type.
     *
     * @param <T> the generic type
     * @param em the em
     * @param clazz the clazz
     * @return the meta model entity type
     */
    public static <T> EntityType<T> getMetaModelEntityType(final EntityManager em, final Class<T> clazz) {
        final Metamodel m = em.getMetamodel();
        return m.entity(clazz);
    }

    /**
     * Checks whether the <code>aimerClass</code> is a super class of the <code>targetClass</code>.
     *
     * @param aimerClazz the aimer clazz
     * @param targetClazz the target clazz
     * @return e.g. <code>aimerClass</code> = List.class, <code>targetClass</code> = ArrayList.class, the method returns
     *         true.
     */
    public static boolean isSuperClass(final Class<?> aimerClazz, final Class<?> targetClazz) {
        if (targetClazz.equals(aimerClazz)) {
            return true;
        }

        if (targetClazz.equals(Object.class)) {
            return false;
        }

        return MetaModelUtils.isSuperClass(targetClazz.getSuperclass(), aimerClazz);
    }

    /**
     * Checks if is entity class.
     *
     * @param clazz the clazz
     * @return true, if is entity class
     */
    public static boolean isEntityClass(final Class<?> clazz) {
        return clazz.getAnnotation(Entity.class) != null;
    }

    /**
     * Gets the pk field paths.
     *
     * @param clazz the clazz
     * @return the pk field paths
     */
    public static FieldPath[] getPkFieldPaths(final Class<?> clazz) {
        final Field[] fields = clazz.getDeclaredFields();

        for (final Field field : fields) {
            final EmbeddedId emb = field.getAnnotation(EmbeddedId.class);
            if (emb == null) {
                continue;
            }

            final Field[] pkFields = field.getType().getDeclaredFields();
            final FieldPath[] pks = new FieldPath[pkFields.length];

            for (int i = 0; i < pkFields.length; ++i) {
                pks[i] = FieldPath.valueOf(field.getName(), pkFields[i].getName());
            }

            return pks;
        }

        for (final Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                return new FieldPath[] {FieldPath.valueOf(field.getName())};
            }
        }

        return new FieldPath[] {};
    }

    /**
     * Gets the field paths with annotation.
     *
     * @param clazz the clazz
     * @param annotation the annotation
     * @return the field paths with annotation
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<FieldPath> getFieldPathsWithAnnotation(final Class<?> clazz, final Class annotation) {
        final List<FieldPath> paths = new ArrayList<>();
        final Set<Field> fields = ReflectionUtils.getAllDeclaredFields(clazz);

        for (final Field field : fields) {
            if (field.getAnnotation(annotation) != null) {
                paths.add(FieldPath.valueOf(field.getName()));
            }
        }

        return paths;
    }

    /**
     * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and <code>field</code>.
     *
     * @param <X> the generic type
     * @param <Y> the generic type
     * @param singularAttribute the singular attribute
     * @param field the field that we want to retrieve the {@link SingularAttribute} object. Notice that this is a
     *        field, not a field path. For example, an <code>address</code> has field <code>person</code> with type =
     *        <code>Person</code>. Person has field <code>name</code> with type = {@link String}. Then for the
     *        {@link SingularAttribute} object that represents the <code>address</code> attribute, <code>person</code>
     *        is a valid field, but <code>person.name</code> is invalid (it is a field path, not a field).
     * @return the singular attribute internally
     */
    private static <X, Y> SingularAttribute<? super Y, ?> getSingularAttributeInternally(
            final SingularAttribute<X, Y> singularAttribute, final String field) {
        final Type<Y> type = singularAttribute.getType();

        if (!(type instanceof ManagedType)) {
            throw new RuntimeException("singularAttribute: [" + singularAttribute.getName()
                    + "] invalid: its associated type [" + type.getJavaType().getName() + "] is not a managed type.");
        }

        final ManagedType<Y> managedType = (ManagedType<Y>) type;
        return managedType.getSingularAttribute(field);
    }

    /**
     * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and
     * <code>fieldPath</code>.
     *
     * @param <X> the generic type
     * @param <Y> the generic type
     * @param singularAttribute the singular attribute
     * @param fieldPath the field path. It could also be a field. e.g. person or person.name are all valid.
     * @return the singular attributes
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <X, Y> SingularAttribute[] getSingularAttributes(final SingularAttribute<X, Y> singularAttribute,
            final String fieldPath) {
        final String[] fields = fieldPath.split("\\.");

        final SingularAttribute[] results = new SingularAttribute[fields.length];
        results[0] = MetaModelUtils.getSingularAttributeInternally(singularAttribute, fields[0]);

        for (int i = 1; i < fields.length; ++i) {
            results[i] = MetaModelUtils.getSingularAttributeInternally(results[i - 1], fields[i]);
        }

        return results;
    }

    /**
     * Gets the next {@link SingularAttribute} from the specified <code>singularAttribute</code> and
     * <code>fieldPath</code>.
     *
     * @param <X> the generic type
     * @param <Y> the generic type
     * @param singularAttribute the singular attribute
     * @param fieldPath the field path. It could also be a field. e.g. person or person.name are all valid.
     * @return the singular attribute
     */
    @SuppressWarnings("rawtypes")
    public static <X, Y> SingularAttribute getSingularAttribute(final SingularAttribute<X, Y> singularAttribute,
            final String fieldPath) {
        final SingularAttribute[] results = MetaModelUtils.getSingularAttributes(singularAttribute, fieldPath);
        return ArrayUtils.isEmpty(results) ? null : results[results.length - 1];
    }

    /**
     * Gets the singular attributes for the specified <code>fieldPath</code> and <code>clazz</code>. For example, an
     * <code>address</code> has field <code>person</code> with type = <code>Person</code>. Person has field
     * <code>name</code>. Then with <code>clazz=Address.class</code> and <code>fieldPath=person.name</code>, this method
     * will return an array of two {@link SingularAttribute} objects:
     * <ul>
     * <li>A {@link SingularAttribute} object for attribute <code>person</code> in <code>Address</code> class.</li>
     * <li>A {@link SingularAttribute} object for attribute <code>name</code> in <code>Person</code> class.</li>
     * </ul>
     *
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @param fieldPath the field path. It could also be a field. e.g. person or person.name are all valid.
     * @return the singular attributes
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static SingularAttribute[] getSingularAttributes(final EntityManager entityManager, final Class<?> clazz,
            final FieldPath fieldPath) {
        final EntityType<?> entityType = MetaModelUtils.getMetaModelEntityType(entityManager, clazz);

        final SingularAttribute[] results = new SingularAttribute[fieldPath.getFields().length];
        results[0] = entityType.getSingularAttribute(fieldPath.getFields()[0]);

        for (int i = 1; i < fieldPath.getFields().length; ++i) {
            results[i] = MetaModelUtils.getSingularAttribute(results[i - 1], fieldPath.getFields()[i]);
        }

        return results;
    }

    /**
     * Gets the leaf singular attribute for the specified <code>fieldPath</code> and <code>clazz</code>. For example, an
     * <code>address</code> has field <code>person</code> with type = <code>Person</code>. Person has field
     * <code>name</code>. Then with <code>clazz=Address.class</code> and <code>fieldPath=person.name</code>, this method
     * will return a {@link SingularAttribute} object for attribute <code>name</code> in <code>Person</code> class.
     *
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @param fieldPath the field path. It could also be a field. e.g. person or person.name are all valid.
     * @return the singular attribute
     */
    @SuppressWarnings("rawtypes")
    public static SingularAttribute getSingularAttribute(final EntityManager entityManager, final Class<?> clazz,
            final FieldPath fieldPath) {
        final SingularAttribute[] results = MetaModelUtils.getSingularAttributes(entityManager, clazz, fieldPath);
        return ArrayUtils.isEmpty(results) ? null : results[results.length - 1];
    }

    /**
     * Gets the pk singular attributes.
     *
     * @param <T> the generic type
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @return the pk singular attributes
     */
    @SuppressWarnings("rawtypes")
    public static <T> SingularAttribute[] getPkSingularAttributes(final EntityManager entityManager,
            final Class<T> clazz) {
        final FieldPath[] pkFieldPaths = MetaModelUtils.getPkFieldPaths(clazz.getSuperclass());
        final SingularAttribute[] results = new SingularAttribute[pkFieldPaths.length];
        for (int i = 0; i < pkFieldPaths.length; i++) {
            results[i] = MetaModelUtils.getSingularAttribute(entityManager, clazz, pkFieldPaths[i]);
        }
        return results;
    }

    /**
     * Get single primary key attribute for specific entityClass return null in case entityClass has zero or composite
     * primary keys.
     *
     * @param <T> the generic type
     * @param entityClass the entity class
     * @param emMain the em main
     * @return the single pk attribute
     */
    public static <T> SingularAttribute<? super T, ?> getSinglePKAttribute(final Class<T> entityClass,
            final EntityManager emMain) {
        @SuppressWarnings("unchecked")
        final SingularAttribute<? super T, ?>[] pkAttributes =
                MetaModelUtils.getPkSingularAttributes(emMain, entityClass);
        if (pkAttributes.length == 1) {
            return pkAttributes[0];
        }
        return null;
    }

    /**
     * Gets the singulare attributes by annocation.
     *
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @param annotation the annotation
     * @return the singulare attributes by annocation
     */
    @SuppressWarnings("rawtypes")
    public static List<SingularAttribute> getSingulareAttributesByAnnocation(final EntityManager entityManager,
            final Class<?> clazz, final Class<?> annotation) {
        final List<SingularAttribute> attributes = new ArrayList<>();
        final List<FieldPath> paths = MetaModelUtils.getFieldPathsWithAnnotation(clazz, annotation);
        for (final FieldPath path : paths) {
            attributes.add(MetaModelUtils.getSingularAttribute(entityManager, clazz, path));
        }
        return attributes;
    }

    /**
     * Gets the fundamental singulare attributes by order.
     *
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @return the fundamental singulare attributes by order
     */
    @SuppressWarnings("rawtypes")
    public static List<SingularAttribute> getFundamentalSingulareAttributesByOrder(final EntityManager entityManager,
            final Class<?> clazz) {
        // final Set<Field> fields = ReflectionUtils.getAllDeclaredFields(clazz);
        final Map<Integer, FieldPath> orderedFieldPaths = new TreeMap<>();
        // for (final Field field : fields) {
        // final Fundamental fundAnnotation = field.getAnnotation(Fundamental.class);
        // if (fundAnnotation != null) {
        // int priority = fundAnnotation.priority();
        // while (orderedFieldPaths.containsKey(priority)) {
        // priority++;
        // }
        // orderedFieldPaths.put(priority, FieldPath.valueOf(field.getName()));
        // }
        // }

        final List<SingularAttribute> attributes = new ArrayList<>();
        for (final FieldPath path : orderedFieldPaths.values()) {
            attributes.add(MetaModelUtils.getSingularAttribute(entityManager, clazz, path));
        }
        return attributes;
    }

    /**
     * Gets the field path from an array of <code>singularAttributes</code>.
     *
     * @param singularAttributes the singular attributes
     * @return FieldPath
     */
    @SuppressWarnings("rawtypes")
    public static FieldPath buildFieldPath(final SingularAttribute[] singularAttributes) {
        final List<String> fieldList = new ArrayList<String>();

        for (final SingularAttribute attribute : singularAttributes) {
            fieldList.add(attribute.getName());
        }

        return FieldPath.valueOf(fieldList);
    }

    /**
     * Builds a {@link Path} object from the specified <code>root</code> and <code>singularAttributes</code>.
     *
     * @param <T> the generic type
     * @param root the root
     * @param singularAttributes the singular attributes
     * @return the path
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Path buildPath(final Root<T> root, final SingularAttribute[] singularAttributes) {
        Path path = root.get(singularAttributes[0]);

        for (int i = 1; i < singularAttributes.length; ++i) {
            path = path.get(singularAttributes[i]);
        }

        return path;
    }

    /**
     * Gets the path.
     *
     * @param <T> the generic type
     * @param root the root
     * @param entityManager the entity manager
     * @param clazz the clazz
     * @param fieldPath the field path
     * @return the path
     */
    @SuppressWarnings("rawtypes")
    public static <T> Path getPath(final Root<T> root, final EntityManager entityManager, final Class<T> clazz,
            final FieldPath fieldPath) {
        final SingularAttribute[] singularAttributes =
                MetaModelUtils.getSingularAttributes(entityManager, clazz, fieldPath);
        return MetaModelUtils.buildPath(root, singularAttributes);
    }

    /**
     * Breaks the <code>path</code> to an array of associated {@link SingularAttribute} objects.
     *
     * @param path the path
     * @return the singular attribute[]
     */
    @SuppressWarnings("rawtypes")
    public static SingularAttribute[] decompile(Path path) {
        final List<SingularAttribute> attributeList = new ArrayList<SingularAttribute>();

        SingularAttribute singularAttribute;
        Object object;

        while (true) {
            object = path.getModel();
            if (object instanceof EntityType<?>) {
                break;
            }

            singularAttribute = (SingularAttribute) object;
            attributeList.add(singularAttribute);
            path = path.getParentPath();
        }

        final SingularAttribute[] attributes = new SingularAttribute[attributeList.size()];

        for (int i = attributeList.size() - 1; i >= 0; --i) {
            attributes[attributeList.size() - i - 1] = attributeList.get(i);
        }

        return attributes;
    }

    /**
     * Invoke internally.
     *
     * @param entityManager the entity manager
     * @param entityObject the entity object
     * @param singularAttributes the singular attributes
     * @param fromIndex the from index
     * @return the object
     */
    private static Object invokeInternally(final EntityManager entityManager, final Object entityObject,
            @SuppressWarnings("rawtypes") final SingularAttribute[] singularAttributes, final int fromIndex) {
        if (fromIndex >= singularAttributes.length) {
            throw new IllegalArgumentException("fromIndex [" + fromIndex + "] is invalid. There are only "
                    + singularAttributes.length + " elements.");
        }

        final Member member = singularAttributes[fromIndex].getJavaMember();
        if (!(member instanceof Field)) {
            throw new IllegalStateException("object: [" + entityObject.toString() + "]: "
                    + singularAttributes[fromIndex].getName() + " (Java Member = " + member + ") is not a field.");
        }

        final Object result;

        try {
            result = ((Field) member).get(entityObject);
        } catch (final Exception ex) {
            throw new IllegalStateException("Exception accessing field " + singularAttributes[fromIndex].getName()
                    + " of object " + entityObject + ": " + ex.getMessage());
        }

        if (fromIndex == (singularAttributes.length - 1)) {
            return result;
        }

        return MetaModelUtils.invokeInternally(entityManager, result, singularAttributes, fromIndex + 1);
    }

    /**
     * Invoke.
     *
     * @param entityManager the entity manager
     * @param entityObject the entity object
     * @param sas the sas
     * @return the object
     */
    public static Object invoke(final EntityManager entityManager, final Object entityObject,
            @SuppressWarnings("rawtypes") final SingularAttribute[] sas) {
        return MetaModelUtils.invokeInternally(entityManager, entityObject, sas, 0);
    }

    /**
     * Invoke.
     *
     * @param entityManager the entity manager
     * @param entityObject the entity object
     * @param fieldPath the field path
     * @return the object
     */
    @SuppressWarnings("rawtypes")
    public static Object invoke(final EntityManager entityManager, final Object entityObject,
            final FieldPath fieldPath) {
        final SingularAttribute[] singularAttributes =
                MetaModelUtils.getSingularAttributes(entityManager, entityObject.getClass(), fieldPath);
        return MetaModelUtils.invokeInternally(entityManager, entityObject, singularAttributes, 0);
    }
}
