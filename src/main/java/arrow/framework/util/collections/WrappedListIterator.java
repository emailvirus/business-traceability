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

import java.util.ListIterator;


/**
 * ListIterator decorator.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class WrappedListIterator<K, V> extends WrappedIterator<K, V> implements ListIterator<V> {

    /** The list. */
    private final WrappedList<K, V> list;

    /**
     * Instantiates a new wrapped list iterator.
     *
     * @param collection the collection
     */
    WrappedListIterator(final WrappedList<K, V> collection) {
        super(collection);
        this.list = collection;
    }

    /**
     * Instantiates a new wrapped list iterator.
     *
     * @param index the index
     * @param collection the collection
     */
    public WrappedListIterator(final int index, final WrappedList<K, V> collection) {
        super(collection.getListDelegate().listIterator(index), collection);
        this.list = collection;
    }

    /**
     * Gets the delegate list iterator.
     *
     * @return the delegate list iterator
     */
    private ListIterator<V> getDelegateListIterator() {
        return (ListIterator<V>) this.getDelegateIterator();
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#hasPrevious()
     */
    @Override
    public boolean hasPrevious() {
        return this.getDelegateListIterator().hasPrevious();
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previous()
     */
    @Override
    public V previous() {
        return this.getDelegateListIterator().previous();
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#nextIndex()
     */
    @Override
    public int nextIndex() {
        return this.getDelegateListIterator().nextIndex();
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#previousIndex()
     */
    @Override
    public int previousIndex() {
        return this.getDelegateListIterator().previousIndex();
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#set(java.lang.Object)
     */
    @Override
    public void set(final V value) {
        this.getDelegateListIterator().set(value);
    }

    /* (non-Javadoc)
     * @see java.util.ListIterator#add(java.lang.Object)
     */
    @Override
    public void add(final V value) {
        final boolean wasEmpty = this.list.isEmpty();
        this.getDelegateListIterator().add(value);
        this.list.getParent().totalSize++;
        if (wasEmpty) {
            this.list.addToMap();
        }
    }
}
