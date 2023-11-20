/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.stilo.model.util.ExcludeFromToString;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * This is the basic abstract entity implementation. All JPA entities should extends this basic entity.
 * Basically this class force to define a unique ID and the methods equals and hashCode based on it.
 */
public abstract class AbstractEntity implements Cloneable, Serializable {

    /**
     * All the subclasses should implement this method. A unique ID can be any valid Java object.
     *
     * @return The object used as unique identifier.
     */
    public abstract Object getId();

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        int hash = 0;
        if (getId() != null) {
            hash = getId().hashCode();
        }
        result = prime * result + hash;
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        final AbstractEntity entity = (AbstractEntity) obj;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (getId() == null) {
			return entity.getId() == null;
        } else return getId().equals(entity.getId());
	}

    /**
     * This method build the toString for a given class.
     *
     * @param clazz The class to be dumped.
     * @return the string with the internal fields.
     */
    protected String buildToString(final Class<? extends Object> clazz) {
        final StringBuilder toString = new StringBuilder();
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {

            // Avoid lazy exception if the field is loaded LAZILY.
            if (!AbstractEntity.class.isAssignableFrom(field.getType())
                    && !Collection.class.isAssignableFrom(field.getType())) {

                boolean log = true;
                final Annotation[] annotations = field.getAnnotations();
                for (final Annotation annotation : annotations) {
                    if (annotation.annotationType() == ExcludeFromToString.class
                            || annotation.annotationType() == ManyToOne.class
                            || annotation.annotationType() == MapsId.class) {
                        log = false;
                    }
                }

                if (!log) {
                    continue;
                }
                try {
                    toString.append(field.getName() + "=" + BeanUtils.getProperty(this, field.getName()) + ";");
                } catch (final NoSuchMethodException e) {
				} catch (final Exception e) {
                    toString.append(field.getName() + "=UNAVAILABLE;");
                }
            }
        }

        if (clazz.getSuperclass() != Object.class) {
            toString.append(this.buildToString(clazz.getSuperclass()));
        }

        return toString.toString();
    }

    @Override
    public final String toString() {
        final StringBuilder toString = new StringBuilder();
        toString.append(this.getClass().getSimpleName() + "[");
        toString.append(this.buildToString(this.getClass()));
        toString.append("]");
        return toString.toString();
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
