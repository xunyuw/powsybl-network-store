/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.network.store.client;

import com.powsybl.commons.extensions.Extension;
import com.powsybl.iidm.network.Identifiable;
import com.powsybl.iidm.network.Network;
import com.powsybl.network.store.model.IdentifiableAttributes;
import com.powsybl.network.store.model.Resource;

import java.util.*;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public abstract class AbstractIdentifiableImpl<I extends Identifiable<I>, D extends IdentifiableAttributes> {

    protected final NetworkObjectIndex index;

    protected final Resource<D> resource;

    protected AbstractIdentifiableImpl(NetworkObjectIndex index, Resource<D> resource) {
        this.index = index;
        this.resource = resource;
    }

    public String getId() {
        return resource.getId();
    }

    public String getName() {
        return resource.getAttributes().getName();
    }

    public Properties getProperties() {
        throw new UnsupportedOperationException("Not supported anymore");
    }

    public String getProperty(String key) {
        Map<String, String> properties = resource.getAttributes().getProperties();
        return properties != null ? properties.get(key) : null;
    }

    public String getProperty(String key, String defaultValue) {
        Map<String, String> properties = resource.getAttributes().getProperties();
        return properties != null ? properties.getOrDefault(key, defaultValue) : null;
    }

    public Set<String> getPropertyNames() {
        Map<String, String> properties = resource.getAttributes().getProperties();
        return properties != null ? properties.keySet() : Collections.emptySet();
    }

    public String setProperty(String key, String value) {
        Map<String, String> properties = resource.getAttributes().getProperties();
        if (properties == null) {
            properties = new HashMap<>();
            resource.getAttributes().setProperties(properties);
        }
        return properties.put(key, value);
    }

    public boolean hasProperty() {
        Map<String, String> properties = resource.getAttributes().getProperties();
        return properties != null && !properties.isEmpty();
    }

    public boolean hasProperty(String key) {
        Map<String, String> properties = resource.getAttributes().getProperties();
        return properties != null && properties.containsKey(key);
    }

    public Network getNetwork() {
        return index.getNetwork();
    }

    public <E extends Extension<I>> void addExtension(Class<? super E> type, E extension) {
        // not extension support by default
    }

    public <E extends Extension<I>> E getExtension(Class<? super E> type) {
        return null;
    }

    public <E extends Extension<I>> E getExtensionByName(String name) {
        return null;
    }

    public <E extends Extension<I>> boolean removeExtension(Class<E> type) {
        throw new UnsupportedOperationException("TODO");
    }

    public <E extends Extension<I>> Collection<E> getExtensions() {
        throw new UnsupportedOperationException("TODO");
    }
}
