/*
 * Copyright (C) 2007 Google Inc. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package arrow.framework.util.collections;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;

import arrow.framework.util.AssertUtils;


/**
 * This class provides a skeletal implementation of the {@link Multiset} interface. A new multiset implementation can be
 * created easily by extending this class and implementing the {@link Multiset#entrySet()} method, plus optionally
 * overriding {@link #add(Object, int)} and {@link #remove(Object, int)} to enable modifications to the multiset.<br />
 * The {@link #contains}, {@link #containsAll}, {@link #count}, and {@link #size} implementations all iterate across the
 * set returned by {@link Multiset#entrySet()}, as do many methods acting on the set returned by {@link #elementSet()}.
 * Override those methods for better performance.
 *
 * @author Kevin Bourrillion
 * @param <E> the element type
 */
abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#entrySet()
     */
    @Override
    public abstract Set<Entry<E>> entrySet();

    // Query Operations

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        long sum = 0L;
        for (final Entry<E> entry : this.entrySet()) {
            sum += entry.getCount();
        }
        return (int) Math.min(sum, Integer.MAX_VALUE);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return this.entrySet().isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(final Object element) {
        return this.elementSet().contains(element);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<E> iterator() {
        return new MultisetIterator();
    }

    /**
     * The Class MultisetIterator.
     */
    private class MultisetIterator implements Iterator<E> {

        /** The entry iterator. */
        private final Iterator<Entry<E>> entryIterator;

        /** The current entry. */
        private Entry<E> currentEntry;

        /** Count of subsequent elements equal to current element. */
        private int laterCount;

        /** Count of all elements equal to current element. */
        private int totalCount;

        /** The can remove. */
        private boolean canRemove;

        /**
         * Instantiates a new multiset iterator.
         */
        MultisetIterator() {
            this.entryIterator = AbstractMultiset.this.entrySet().iterator();
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return (this.laterCount > 0) || this.entryIterator.hasNext();
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.laterCount == 0) {
                this.currentEntry = this.entryIterator.next();
                this.totalCount = this.laterCount = this.currentEntry.getCount();
            }
            this.laterCount--;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            AssertUtils
                    .assertTrueWithErrorMessage(this.canRemove, "no calls to next() since the last call to remove()");

            if (this.totalCount == 1) {
                this.entryIterator.remove();
            }
            else {
                AbstractMultiset.this.remove(this.currentEntry.getElement());
            }
            this.totalCount--;
            this.canRemove = false;
        }
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#count(java.lang.Object)
     */
    @Override
    public int count(final Object element) {
        for (final Entry<E> entry : this.entrySet()) {
            if (ObjectUtils.equals(entry.getElement(), element)) {
                return entry.getCount();
            }
        }
        return 0;
    }

    // Modification Operations

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#add(java.lang.Object)
     */
    @Override
    public boolean add(final E element) {
        this.add(element, 1);
        return true;
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#add(java.lang.Object, int)
     */
    @Override
    public int add(final E element, final int occurrences) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(final Object element) {
        return this.remove(element, 1) > 0;
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#remove(java.lang.Object, int)
     */
    @Override
    public int remove(final Object element, final int occurrences) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#setCount(java.lang.Object, int)
     */
    @Override
    public int setCount(final E element, final int count) {
        return Multisets.setCountImpl(this, element, count);
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#setCount(java.lang.Object, int, int)
     */
    @Override
    public boolean setCount(final E element, final int oldCount, final int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }

    // Bulk Operations

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> elements) {
        return this.elementSet().containsAll(elements);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(final Collection<? extends E> elementsToAdd) {
        if (elementsToAdd.isEmpty()) {
            return false;
        }
        if (elementsToAdd instanceof Multiset<?>) {
            final Multiset<? extends E> that = (Multiset<? extends E>) elementsToAdd;
            for (final Entry<? extends E> entry : that.entrySet()) {
                this.add(entry.getElement(), entry.getCount());
            }
        }
        else {
            super.addAll(elementsToAdd);
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> elementsToRemove) {
        final Collection<?> collection =
                (elementsToRemove instanceof Multiset<?>) ? ((Multiset<?>) elementsToRemove).elementSet()
                        : elementsToRemove;
        return this.elementSet().removeAll(collection);
        // TODO: implement retainAll similarly?
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> elementsToRetain) {
        AssertUtils.assertNotNull(elementsToRetain);
        final Iterator<Entry<E>> entries = this.entrySet().iterator();
        boolean modified = false;
        while (entries.hasNext()) {
            final Entry<E> entry = entries.next();
            if (!elementsToRetain.contains(entry.getElement())) {
                entries.remove();
                modified = true;
            }
        }
        return modified;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#clear()
     */
    @Override
    public void clear() {
        this.entrySet().clear();
    }

    // Views

    /** The element set. */
    private transient Set<E> elementSet;

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.Multiset#elementSet()
     */
    @Override
    public Set<E> elementSet() {
        Set<E> result = this.elementSet;
        if (result == null) {
            this.elementSet = result = this.createElementSet();
        }
        return result;
    }

    /**
     * Creates a new instance of this multiset's element set, which will be returned by {@link #elementSet()}.
     *
     * @return the sets the
     */
    Set<E> createElementSet() {
        return new ElementSet();
    }

    /**
     * The Class ElementSet.
     */
    private class ElementSet extends AbstractSet<E> {

        /* (non-Javadoc)
         * @see java.util.AbstractCollection#iterator()
         */
        @Override
        public Iterator<E> iterator() {
            final Iterator<Entry<E>> entryIterator = AbstractMultiset.this.entrySet().iterator();
            return new Iterator<E>() {
                @Override
                public boolean hasNext() {
                    return entryIterator.hasNext();
                }

                @Override
                public E next() {
                    return entryIterator.next().getElement();
                }

                @Override
                public void remove() {
                    entryIterator.remove();
                }
            };
        }

        /* (non-Javadoc)
         * @see java.util.AbstractCollection#size()
         */
        @Override
        public int size() {
            return AbstractMultiset.this.entrySet().size();
        }
    }

    // Object methods

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns {@code true} if {@code other} is a multiset of the same size and if, for each
     * element, the two multisets have the same count.
     * </p>
     */
    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Multiset<?>) {
            final Multiset<?> that = (Multiset<?>) object;
            /*
             * We can't simply check whether the entry sets are equal, since that
             * approach fails when a TreeMultiset has a comparator that returns 0
             * when passed unequal elements.
             */

            if (this.size() != that.size()) {
                return false;
            }
            for (final Entry<?> entry : that.entrySet()) {
                if (this.count(entry.getElement()) != entry.getCount()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc} This implementation returns the hash code of {@link Multiset#entrySet()}.
     */
    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }

    /**
     * {@inheritDoc} This implementation returns the result of invoking {@code toString} on {@link Multiset#entrySet()}.
     */
    @Override
    public String toString() {
        return this.entrySet().toString();
    }
}
