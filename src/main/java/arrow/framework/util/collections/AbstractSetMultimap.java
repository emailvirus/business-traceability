/*
 * Copyright (C) 2007 Google Inc. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package arrow.framework.util.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * Basic implementation of the {@link SetMultimap} interface. It's a wrapper around {@link AbstractMultimap} that
 * converts the returned collections into {@code Sets}. The {@link #createCollection} method must return a {@code Set}.
 *
 * @author Jared Levy
 * @param <K> the key type
 * @param <V> the value type
 */
abstract class AbstractSetMultimap<K, V> extends AbstractMultimap<K, V> implements SetMultimap<K, V> {
    /**
     * Creates a new multimap that uses the provided map.
     *
     * @param map place to store the mapping from each key to its corresponding values
     */
    protected AbstractSetMultimap(final Map<K, Collection<V>> map) {
        super(map);
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.AbstractMultimap#createCollection()
     */
    @Override
    abstract Set<V> createCollection();

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.AbstractMultimap#get(java.lang.Object)
     */
    @Override
    public Set<V> get(final K key) {
        return (Set<V>) super.get(key);
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.AbstractMultimap#entries()
     */
    @Override
    public Set<Map.Entry<K, V>> entries() {
        return (Set<Map.Entry<K, V>>) super.entries();
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.AbstractMultimap#removeAll(java.lang.Object)
     */
    @Override
    public Set<V> removeAll(final Object key) {
        return (Set<V>) super.removeAll(key);
    }

    /**
     * {@inheritDoc} Any duplicates in {@code values} will be stored in the multimap once.
     */
    @Override
    public Set<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return (Set<V>) super.replaceValues(key, values);
    }

}
