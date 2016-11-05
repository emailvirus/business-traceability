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

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


/**
 * List decorator that stays in sync with the multimap values for a key.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
class WrappedList<K, V> extends WrappedCollection<K, V> implements List<V> {

    /** The abstract multimap. */
    private final AbstractMultimap<K, V> abstractMultimap;

    /**
     * Instantiates a new wrapped list.
     *
     * @param abstractMultimap the abstract multimap
     * @param key the key
     * @param delegate the delegate
     * @param ancestor the ancestor
     */
    WrappedList(final AbstractMultimap<K, V> abstractMultimap, final K key, final List<V> delegate,
            final WrappedCollection<K, V> ancestor) {
        super(abstractMultimap, key, delegate, ancestor);
        this.abstractMultimap = abstractMultimap;
    }

    /**
     * Gets the list delegate.
     *
     * @return the list delegate
     */
    List<V> getListDelegate() {
        return (List<V>) this.getDelegate();
    }

    /* (non-Javadoc)
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(final int index, final Collection<? extends V> items) {
        if (items.isEmpty()) {
            return false;
        }
        final int oldSize = this.size(); // calls refreshIfEmpty
        final boolean changed = this.getListDelegate().addAll(index, items);
        if (changed) {
            final int newSize = this.getDelegate().size();
            this.abstractMultimap.totalSize += (newSize - oldSize);
            if (oldSize == 0) {
                this.addToMap();
            }
        }
        return changed;
    }

    /* (non-Javadoc)
     * @see java.util.List#get(int)
     */
    @Override
    public V get(final int index) {
        this.refreshIfEmpty();
        return this.getListDelegate().get(index);
    }

    /* (non-Javadoc)
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public V set(final int index, final V element) {
        this.refreshIfEmpty();
        return this.getListDelegate().set(index, element);
    }

    /* (non-Javadoc)
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(final int index, final V element) {
        this.refreshIfEmpty();
        final boolean wasEmpty = this.getDelegate().isEmpty();
        this.getListDelegate().add(index, element);
        this.abstractMultimap.totalSize++;
        if (wasEmpty) {
            this.addToMap();
        }
    }

    /* (non-Javadoc)
     * @see java.util.List#remove(int)
     */
    @Override
    public V remove(final int index) {
        this.refreshIfEmpty();
        final V value = this.getListDelegate().remove(index);
        this.abstractMultimap.totalSize--;
        this.removeIfEmpty();
        return value;
    }

    /* (non-Javadoc)
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(final Object obj) {
        this.refreshIfEmpty();
        return this.getListDelegate().indexOf(obj);
    }

    /* (non-Javadoc)
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(final Object obj) {
        this.refreshIfEmpty();
        return this.getListDelegate().lastIndexOf(obj);
    }

    /* (non-Javadoc)
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<V> listIterator() {
        this.refreshIfEmpty();
        return new WrappedListIterator<K, V>(this);
    }

    /* (non-Javadoc)
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<V> listIterator(final int index) {
        this.refreshIfEmpty();
        return new WrappedListIterator<K, V>(index, this);
    }

    /* (non-Javadoc)
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<V> subList(final int fromIndex, final int toIndex) {
        this.refreshIfEmpty();
        return this.abstractMultimap.wrapList(this.getKey(), this.getListDelegate().subList(fromIndex, toIndex),
                (this.getAncestor() == null) ? this : this.getAncestor());
    }

}
