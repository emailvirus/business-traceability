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
import java.util.ConcurrentModificationException;
import java.util.Iterator;



/**
 * Collection iterator for {@code WrappedCollection}.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class WrappedIterator<K, V> implements Iterator<V> {

    /** The delegate iterator. */
    final Iterator<V> delegateIterator;

    /** The original delegate. */
    final Collection<V> originalDelegate;

    /** The collection. */
    private final WrappedCollection<K, V> collection;

    /**
     * Instantiates a new wrapped iterator.
     *
     * @param collection the collection
     */
    WrappedIterator(final WrappedCollection<K, V> collection) {
        this.collection = collection;
        this.delegateIterator = collection.getParent().iteratorOrListIterator(collection.delegate);
        this.originalDelegate = collection.delegate;

    }

    /**
     * Instantiates a new wrapped iterator.
     *
     * @param delegateIterator the delegate iterator
     * @param collection the collection
     */
    WrappedIterator(final Iterator<V> delegateIterator, final WrappedCollection<K, V> collection) {
        this.delegateIterator = delegateIterator;
        this.collection = collection;
        this.originalDelegate = collection.delegate;
    }

    /**
     * If the delegate changed since the iterator was created, the iterator is no longer valid.
     */
    void validateIterator() {
        this.collection.refreshIfEmpty();
        if (this.collection.delegate != this.originalDelegate) {
            throw new ConcurrentModificationException();
        }
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        this.validateIterator();
        return this.delegateIterator.hasNext();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public V next() {
        this.validateIterator();
        return this.delegateIterator.next();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
        this.delegateIterator.remove();
        this.collection.getParent().totalSize--;
        this.collection.removeIfEmpty();
    }

    /**
     * Gets the delegate iterator.
     *
     * @return the delegate iterator
     */
    Iterator<V> getDelegateIterator() {
        this.validateIterator();
        return this.delegateIterator;
    }
}
