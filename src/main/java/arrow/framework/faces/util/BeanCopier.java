/*
 * package: arrow.framework.faces.util file: BeanCopier.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.faces.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.apache.commons.lang3.ArrayUtils;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.framework.exception.ArrException;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.BasePk;

/**
 * The Class BeanCopier.
 */
@SuppressWarnings(value = "DP_DO_INSIDE_DO_PRIVILEGED",
        justification = "Have to ignore because we don't clearly understand")
public class BeanCopier {

    /** The Constant LOGGER. */
    // log injection is not available
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger();

    /** The Constant SETTER_PATTERN. */
    private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z]");

    /**
     * Gets the default getter name.
     *
     * @param setter the setter
     * @return the default getter name
     */
    private static String getDefaultGetterName(final Method setter) {
        return "get" + setter.getName().substring(3);
    }

    /**
     * Checks if is default copy.
     *
     * @param method the method
     * @param srcClass the src class
     * @return true, if is default copy
     */
    private static boolean isDefaultCopy(final Method method, final Class<?> srcClass, final String... excludeFields) {
        final String name = method.getName();
        // if (name.equals("setPk"))
        // return false;
        if (name.equals("setObject_version")) {
            return false;
        }
        // if (name.equals("setCreated_by")) return false;
        // if (name.equals("setCreated_datetime")) return false;
        if (name.equals("setLast_updated_by")) {
            return false;
        }
        if (name.equals("setLast_updated_datetime")) {
            return false;
        }
        if (name.equals("setPosted_by")) {
            return false;
        }
        if (name.equals("setPosted_datetime")) {
            return false;
        }

        if (name.equals("setLast_updated_at")) {
            return false;
        }

        if (ArrayUtils.contains(excludeFields, name.substring(3))) {
            return false;
        }

        // TODO: implement @NoCopy and @DeepCopy?

        // NoCopy noCopy = method.getAnnotation(NoCopy.class);
        // if (noCopy != null)
        // {
        // Class<? extends Serializable> disallowedSrc = noCopy.srcClass();
        // if ((disallowedSrc == null) || disallowedSrc.equals(Serializable.class))
        // return false;
        // if (srcClass.equals(disallowedSrc))
        // return false;
        // }
        //
        // if (method.getAnnotation(DeepCopy.class) != null)
        // return false;
        return BeanCopier.SETTER_PATTERN.matcher(method.getName()).find() && (method.getReturnType() == void.class)
                && (method.getParameterTypes().length == 1);
    }

    // private static boolean isDeepCopy(final Method method, final Class<?> srcClass)
    // {
    // NoCopy noCopy = method.getAnnotation(NoCopy.class);
    // if (noCopy != null)
    // {
    // Class<? extends Serializable> disallowedSrc = noCopy.srcClass();
    // if ((disallowedSrc == null) || disallowedSrc.equals(Serializable.class))
    // return false;
    // if (srcClass.equals(disallowedSrc))
    // return false;
    // }
    //
    // if (method.getName().equals("setPk"))
    // return true;
    // return method.getAnnotation(DeepCopy.class) != null;
    // }

    // private static boolean isExternalSourceCopy(final Method method)
    // {
    // return method.getAnnotation(ExternalSourceCopy.class) != null;
    // }

    // private static Class<?> getDesFieldClass(final Method desMethod)
    // {
    // if (desMethod.getName().equals("setPk"))
    // return desMethod.getParameterTypes()[0];
    //
    // DeepCopy deepCopy = desMethod.getAnnotation(DeepCopy.class);
    // if (deepCopy == null)
    // throw new IllegalArgumentException("Method " + desMethod + " is not @DeepCopy");
    //
    // if (deepCopy.desFieldClass() == void.class)
    // return desMethod.getParameterTypes()[0];
    // else
    // return deepCopy.desFieldClass();
    // }

    // private static Method getSourceGetter(final Method desMethod, final Class<?> srcClass) throws
    // NoSuchMethodException
    // {
    // if (desMethod.getName().equals("setPk"))
    // return srcClass.getMethod("getPk");
    //
    // DeepCopy ca = desMethod.getAnnotation(DeepCopy.class);
    // if (ca == null)
    // throw new IllegalArgumentException("Method " + desMethod + " is not @DeepCopy");
    //
    // SourceDescriptor[] sds = ca.sourceDescriptors();
    // if (sds.length == 0)
    // return srcClass.getMethod(BeanCopier.getDefaultGetterName(desMethod));
    // for (SourceDescriptor sd : sds)
    // {
    // if (sd.srcClass().equals(srcClass))
    // return srcClass.getMethod(sd.srcMethod());
    // }
    //
    // return null;
    // }

    // private static String getCopyCondition(final Method desMethod, final Class<?> srcClass)
    // {
    // DeepCopy ca = desMethod.getAnnotation(DeepCopy.class);
    // if (ca == null)
    // throw new IllegalArgumentException("Method " + desMethod + " is not @DeepCopy");
    //
    // SourceDescriptor[] sds = ca.sourceDescriptors();
    // if (sds.length == 0)
    // return null;
    // else
    // {
    // for (SourceDescriptor sd : sds)
    // {
    // if (sd.srcClass().equals(srcClass))
    // {
    // String condition = sd.condition();
    // if (SynString.isEmpty(condition))
    // return null;
    // return condition;
    // }
    // }
    // }
    // return null;
    // }

    /**
     * Flat copy.
     *
     * @param srcBean the src bean
     * @param desBean the des bean
     * @return true, if successful
     */
    public static boolean flatCopy(final Object srcBean, final Object desBean) {
        final Class<?> srcClass = srcBean.getClass();
        final Class<?> desClass = desBean.getClass();
        final Method[] srcMethods = srcClass.getMethods();
        final Method[] desMethods = desClass.getMethods();

        for (final Method srcMethod : srcMethods) {
            final String srcGetter = srcMethod.getName();

            if (!srcGetter.startsWith("get") || (srcMethod.getParameterTypes().length > 0)
                    || srcGetter.equals("getObject_version")) {
                continue;
            }
            final Class<?> retType = srcMethod.getReturnType();
            if (retType.isPrimitive() || Number.class.isAssignableFrom(retType) || Date.class.isAssignableFrom(retType)
                    || Boolean.class.equals(retType) || String.class.equals(retType)) {
                final String desSetter = "set" + srcGetter.substring(3);

                for (final Method desMethod : desMethods) {
                    if (desMethod.getName().equals(desSetter) && desMethod.getReturnType().equals(void.class)
                            && (desMethod.getParameterTypes().length == 1)) {
                        final Class<?> paramType = desMethod.getParameterTypes()[0];
                        if (paramType.isAssignableFrom(retType)) {
                            try {
                                desMethod.invoke(desBean, srcMethod.invoke(srcBean));
                            } catch (final Exception e) {
                                BeanCopier.LOGGER.trace("Error:", e);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Copy with pk.
     *
     * @param srcBean the src bean
     * @param desBean the des bean
     * @return true, if successful
     */
    public static boolean copyWithPk(final Object srcBean, final Object desBean) {
        BeanCopier.copy(srcBean, desBean);

        final Class<?> srcClass = srcBean.getClass();
        final Class<?> desClass = desBean.getClass();

        // get only visible methods
        try {
            final Field pkFields = desClass.getField("pk");
            pkFields.setAccessible(true);

            final String getterName = "getPk";
            final Method getter = srcClass.getMethod(getterName);
            final Object pk = getter.invoke(srcBean);

            pkFields.set(desBean, pk);
            return true;

        } catch (final NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            BeanCopier.LOGGER.trace("Error while copying Beans", e);
        }
        return false;
    }

    /**
     * this perform a shallow copy from getters of srcBean to setters of desBean, skipping all collections.
     *
     * @param srcBean the src bean
     * @param desBean the des bean
     * @return true, if successful
     */
    public static boolean copy(final Object srcBean, final Object desBean, final String... excludeFields) {
        final Class<?> srcClass = srcBean.getClass();
        final Class<?> desClass = desBean.getClass();

        // get only visible methods
        final Method[] methods = desClass.getMethods();

        for (final Method desMethod : methods) {
            if (BeanCopier.isDefaultCopy(desMethod, srcClass, excludeFields)) {
                final String getterName = BeanCopier.getDefaultGetterName(desMethod);

                try {
                    final Class<?> setterParamType = desMethod.getParameterTypes()[0];
                    // only set non-collection fields.
                    if (Collection.class.isAssignableFrom(setterParamType)) {
                        continue;
                    }

                    final Method getter = srcClass.getMethod(getterName);

                    try {
                        if (setterParamType.isAssignableFrom(getter.getReturnType())) {
                            desMethod.invoke(desBean, getter.invoke(srcBean));
                        }
                        else {
                            throw new IllegalArgumentException();
                        }
                    } catch (final IllegalArgumentException iae) {
                        BeanCopier.LOGGER.trace("Source class: " + srcClass.getName() + ", getter return type: "
                                + getter.getReturnType() + " is not castable to: " + desClass.getName()
                                + ", setter's param type: " + setterParamType);
                    } catch (final InvocationTargetException ite) {
                        BeanCopier.LOGGER.trace("Exception while invoking setter " + desMethod.getName() + ", of class "
                                + desClass.getName() + ", Exception is: " + ite.getTargetException());
                    }

                } catch (final NoSuchMethodException e) {
                    BeanCopier.LOGGER.trace("Missing getter in source bean: " + srcClass.getName() + "." + getterName);
                } catch (final Exception e) {
                    BeanCopier.LOGGER.error("Unexpected Exception: ", e);
                }
            }

        }

        return true;
    }

    /**
     * Copy.
     *
     * @param <T> the generic type
     * @param srcBean the src bean
     * @param desClass the des class
     * @return the t
     * @throws ArrException the arr exception
     */
    public static <T> T copy(final Object srcBean, final Class<T> desClass) throws ArrException {
        if (srcBean == null) {
            return null;
        }

        try {
            final T toBean = desClass.newInstance();
            return BeanCopier.copy(srcBean, toBean) ? toBean : null;
        } catch (final Exception e) {
            BeanCopier.LOGGER.trace("Exception when coping", e);
            throw new ArrException("error.BeanCopier.copyFailed");
        }
    }

    /**
     * Duplicate.
     *
     * @param fromBean the from bean
     * @param toBean the to bean
     * @return the serializable
     */
    @Deprecated
    public static Serializable duplicate(final Serializable fromBean, final Serializable toBean) {

        if ((fromBean == null) || (toBean == null)) {
            return null;
        }

        final Class<? extends Serializable> fromClass = fromBean.getClass();
        final Class<? extends Serializable> toClass = toBean.getClass();

        final Field[] fields = toClass.getDeclaredFields();

        Object pk = null;

        try {
            if (!toClass.getName().endsWith("Pk")) {
                pk = toClass.getMethod("getPk").invoke(toBean);
            }
        } catch (final IllegalArgumentException | SecurityException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e1) {
            BeanCopier.LOGGER.trace("error while reading fields", e1);
        }
        for (final Field field : fields) {

            try {

                final Method getter = fromClass.getMethod(
                        "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));

                if ((pk != null) && !pk.toString().trim().equals("") && (field.getAnnotation(Id.class) != null)) {
                    continue;
                }
                else {
                    if (field.getAnnotation(EmbeddedId.class) != null) {
                        final BasePk newPk = (BasePk) Class.forName(toClass.getName() + "$Pk").newInstance();
                        BeanCopier.copyPk((BasePk) fromClass.getMethod("getPk").invoke(fromBean), newPk);
                        toClass.getMethod("setPk", newPk.getClass()).invoke(toBean, newPk);
                    }
                }

                final String propertyName =
                        field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                if ((field.getAnnotation(Version.class) != null) || (field.getAnnotation(OneToMany.class) != null)
                        || (((field.getAnnotation(ManyToOne.class) != null)
                                || (field.getAnnotation(OneToOne.class) != null))
                                && !field.getType().equals(getter.getReturnType()))) {
                    continue;
                }

                final Method setter = toClass.getMethod("set" + propertyName, getter.getReturnType());

                setter.invoke(toBean, getter.invoke(fromBean));

            } catch (final NoSuchMethodException e) {
                BeanCopier.LOGGER.trace(
                        "Destination bean does not contain the setter correspond to source bean's getter method ");
                continue;
            } catch (final InstantiationException | IllegalArgumentException | IllegalAccessException
                    | InvocationTargetException | ClassNotFoundException e) {
                BeanCopier.LOGGER.trace("Error while copying:", e);
            }
        }
        return toBean;
    }

    /**
     * Copy pk.
     *
     * @param fromBean the from bean
     * @param toBean the to bean
     * @return the base pk
     */
    public static BasePk copyPk(final BasePk fromBean, final BasePk toBean) {
        if ((fromBean == null) || (toBean == null)) {
            return null;
        }

        final Class<?> fromClass = fromBean.getClass();
        final Class<?> toClass = toBean.getClass();

        final Field[] fields = toClass.getDeclaredFields();

        for (final Field field : fields) {
            try {
                final String propertyName =
                        field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                final Method getter = fromClass.getMethod("get" + propertyName);

                if ((field.getAnnotation(OneToMany.class) != null) || (((field.getAnnotation(ManyToOne.class) != null)
                        || (field.getAnnotation(OneToOne.class) != null))
                        && (!field.getType().equals(getter.getReturnType())))) {
                    continue;
                }

                final Method setter = toClass.getMethod("set" + propertyName, getter.getReturnType());

                setter.invoke(toBean, getter.invoke(fromBean));
            } catch (final NoSuchMethodException e) {
                BeanCopier.LOGGER.trace(
                        "Destination bean does not contain " + "the setter correspond to source bean's getter method ");
                continue;
            } catch (final Exception e) {
                BeanCopier.LOGGER.error("Unexpected Exception: ", e);
                break;
            }
        }
        return toBean;
    }
}
