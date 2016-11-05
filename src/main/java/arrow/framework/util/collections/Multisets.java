/*
 * JBoss, Home of Professional Open Source Copyright 2010, Red Hat Middleware LLC, and individual contributors by the
 * @authors tag. See the copyright.txt in the distribution for a full listing of individual contributors. Licensed under
 * the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package arrow.framework.util.collections;

import org.apache.commons.lang3.ObjectUtils;

import arrow.framework.util.AssertUtils;


/**
 * The Class Multisets.
 */
public class Multisets {

    /**
     * Instantiates a new multisets.
     */
    private Multisets() {}

    /**
     * Implementation of the {@code equals}, {@code hashCode}, and {@code toString} methods of {@link Multiset.Entry}.
     *
     * @param <E> the element type
     */
    abstract static class AbstractEntry<E> implements Multiset.Entry<E> {

        /**
         * Indicates whether an object equals this entry, following the behavior specified in
         * {@link Multiset.Entry#equals}.
         *
         * @param object the object
         * @return true, if successful
         */
        @Override
        public boolean equals(final Object object) {
            if (object instanceof Multiset.Entry<?>) {
                final Multiset.Entry<?> that = (Multiset.Entry<?>) object;
                return (this.getCount() == that.getCount()) && ObjectUtils.equals(this.getElement(), that.getElement());
            }
            return false;
        }

        /**
         * Return this entry's hash code, following the behavior specified in {@link Multiset.Entry#hashCode}.
         *
         * @return the int
         */
        @Override
        public int hashCode() {
            final E e = this.getElement();
            return ((e == null) ? 0 : e.hashCode()) ^ this.getCount();
        }

        /**
         * Returns a string representation of this multiset entry. The string representation consists of the associated
         * element if the associated count is one, and otherwise the associated element followed by the characters " x "
         * (space, x and space) followed by the count. Elements and counts are converted to strings as by
         * {@code String.valueOf}.
         *
         * @return the string
         */
        @Override
        public String toString() {
            final String text = String.valueOf(this.getElement());
            final int n = this.getCount();
            return (n == 1) ? text : (text + " x " + n);
        }
    }


    /**
     * Sets the count impl.
     *
     * @param <E> the element type
     * @param self the self
     * @param element the element
     * @param oldCount the old count
     * @param newCount the new count
     * @return true, if successful
     */
    static <E> boolean setCountImpl(final Multiset<E> self, final E element, final int oldCount, final int newCount) {
        AssertUtils.assertNonNegativeWithName(oldCount, "oldCount");
        AssertUtils.assertNonNegativeWithName(newCount, "newCount");

        if (self.count(element) == oldCount) {
            self.setCount(element, newCount);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets the count impl.
     *
     * @param <E> the element type
     * @param self the self
     * @param element the element
     * @param count the count
     * @return the int
     */
    static <E> int setCountImpl(final Multiset<E> self, final E element, final int count) {
        AssertUtils.assertNonNegativeWithName(count, "count");

        final int oldCount = self.count(element);

        final int delta = count - oldCount;
        if (delta > 0) {
            self.add(element, delta);
        }
        else if (delta < 0) {
            self.remove(element, -delta);
        }

        return oldCount;
    }
}
