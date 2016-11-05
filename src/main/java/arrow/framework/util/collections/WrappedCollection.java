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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import arrow.framework.util.AssertUtils;


/**
 * Collection decorator that stays in sync with the multimap values for a key. There are two kinds of wrapped
 * collections: full and subcollections. Both have a delegate pointing to the underlying collection class.
 * <p/>
 * <p/>
 * Full collections, identified by a null ancestor field, contain all multimap values for a given key. Its delegate is a
 * value in {@link AbstractMultimap#map} whenever the delegate is non-empty. The {@code refreshIfEmpty},
 * {@code removeIfEmpty}, and {@code addToMap} methods ensure that the {@code WrappedCollection} and map remain
 * consistent.
 * <p/>
 * <p/>
 * A subcollection, such as a sublist, contains some of the values for a given key. Its ancestor field points to the
 * full wrapped collection with all values for the key. The subcollection {@code refreshIfEmpty}, {@code removeIfEmpty},
 * and {@code addToMap} methods call the corresponding methods of the full wrapped collection.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
class WrappedCollection<K, V> extends AbstractCollection<V> {

    /** The abstract multimap. */
    private final AbstractMultimap<K, V> abstractMultimap;

    /** The key. */
    final K key;

    /** The delegate. */
    Collection<V> delegate;

    /** The ancestor. */
    final WrappedCollection<K, V> ancestor;

    /** The ancestor delegate. */
    final Collection<V> ancestorDelegate;

    /**
     * Instantiates a new wrapped collection.
     *
     * @param abstractMultimap the abstract multimap
     * @param key the key
     * @param delegate the delegate
     * @param ancestor the ancestor
     */
    WrappedCollection(final AbstractMultimap<K, V> abstractMultimap, final K key, final Collection<V> delegate,
            final WrappedCollection<K, V> ancestor) {
        this.abstractMultimap = abstractMultimap;
        this.key = key;
        this.delegate = delegate;
        this.ancestor = ancestor;
        this.ancestorDelegate = (ancestor == null) ? null : ancestor.getDelegate();
    }

    /**
     * Gets the parent.
     *
     * @return the parent
     */
    public AbstractMultimap<K, V> getParent() {
        return this.abstractMultimap;
    }

    /**
     * If the delegate collection is empty, but the multimap has values for the key, replace the delegate with the new
     * collection for the key.
     * <p/>
     * <p/>
     * For a subcollection, refresh its ancestor and validate that the ancestor delegate hasn't changed.
     */
    void refreshIfEmpty() {
        if (this.ancestor != null) {
            this.ancestor.refreshIfEmpty();
            if (this.ancestor.getDelegate() != this.ancestorDelegate) {
                throw new ConcurrentModificationException();
            }
        }
        else if (this.delegate.isEmpty()) {
            final Collection<V> newDelegate = this.abstractMultimap.map.get(this.key);
            if (newDelegate != null) {
                this.delegate = newDelegate;
            }
        }
    }

    /**
     * If collection is empty, remove it from {@code map}. For subcollections, check whether the ancestor collection is
     * empty.
     */
    void removeIfEmpty() {
        if (this.ancestor != null) {
            this.ancestor.removeIfEmpty();
        }
        else if (this.delegate.isEmpty()) {
            this.abstractMultimap.map.remove(this.key);
        }
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    K getKey() {
        return this.key;
    }

    /**
     * Add the delegate to the map. Other {@code WrappedCollection} methods should call this method after adding
     * elements to a previously empty collection.
     * <p/>
     * <p/>
     * Subcollection add the ancestor's delegate instead.
     */
    void addToMap() {
        if (this.ancestor != null) {
            this.ancestor.addToMap();
        }
        else {
            this.abstractMultimap.map.put(this.key, this.delegate);
        }
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        this.refreshIfEmpty();
        return this.delegate.size();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        this.refreshIfEmpty();
        return this.delegate.equals(object);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        this.refreshIfEmpty();
        return this.delegate.hashCode();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        this.refreshIfEmpty();
        return this.delegate.toString();
    }

    /**
     * Gets the delegate.
     *
     * @return the delegate
     */
    Collection<V> getDelegate() {
        return this.delegate;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<V> iterator() {
        this.refreshIfEmpty();
        return new WrappedIterator<K, V>(this);
    }


    /* (non-Javadoc)
     * @see java.util.AbstractCollection#add(java.lang.Object)
     */
    @Override
    public boolean add(final V value) {
        this.refreshIfEmpty();
        final boolean wasEmpty = this.delegate.isEmpty();
        final boolean changed = this.delegate.add(value);
        if (changed) {
            this.abstractMultimap.totalSize++;
            if (wasEmpty) {
                this.addToMap();
            }
        }
        return changed;
    }

    /**
     * Gets the ancestor.
     *
     * @return the ancestor
     */
    WrappedCollection<K, V> getAncestor() {
        return this.ancestor;
    }

    // The following methods are provided for better performance.

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(final Collection<? extends V> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        final int oldSize = this.size(); // calls refreshIfEmpty
        final boolean changed = this.delegate.addAll(collection);
        if (changed) {
            final int newSize = this.delegate.size();
            this.abstractMultimap.totalSize += (newSize - oldSize);
            if (oldSize == 0) {
                this.addToMap();
            }
        }
        return changed;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(final Object obj) {
        this.refreshIfEmpty();
        return this.delegate.contains(obj);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> items) {
        this.refreshIfEmpty();
        return this.delegate.containsAll(items);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#clear()
     */
    @Override
    public void clear() {
        final int oldSize = this.size(); // calls refreshIfEmpty
        if (oldSize == 0) {
            return;
        }
        this.delegate.clear();
        this.abstractMultimap.totalSize -= oldSize;
        this.removeIfEmpty(); // maybe shouldn't be removed if this is a sublist
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(final Object obj) {
        this.refreshIfEmpty();
        final boolean changed = this.delegate.remove(obj);
        if (changed) {
            this.abstractMultimap.totalSize--;
            this.removeIfEmpty();
        }
        return changed;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> items) {
        if (items.isEmpty()) {
            return false;
        }
        final int oldSize = this.size(); // calls refreshIfEmpty
        final boolean changed = this.delegate.removeAll(items);
        if (changed) {
            final int newSize = this.delegate.size();
            this.abstractMultimap.totalSize += (newSize - oldSize);
            this.removeIfEmpty();
        }
        return changed;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> items) {
        AssertUtils.assertNotNull(items);
        final int oldSize = this.size(); // calls refreshIfEmpty
        final boolean changed = this.delegate.retainAll(items);
        if (changed) {
            final int newSize = this.delegate.size();
            this.abstractMultimap.totalSize += (newSize - oldSize);
            this.removeIfEmpty();
        }
        return changed;
    }
}
