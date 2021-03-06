/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.network.store.client;

import com.powsybl.iidm.network.ConnectableType;
import com.powsybl.iidm.network.Load;
import com.powsybl.iidm.network.LoadType;
import com.powsybl.network.store.model.LoadAttributes;
import com.powsybl.network.store.model.Resource;

/**
 * @author Geoffroy Jamgotchian <geoffroy.jamgotchian at rte-france.com>
 */
public class LoadImpl extends AbstractInjectionImpl<Load, LoadAttributes> implements Load {

    public LoadImpl(NetworkObjectIndex index, Resource<LoadAttributes> resource) {
        super(index, resource);
    }

    static LoadImpl create(NetworkObjectIndex index, Resource<LoadAttributes> resource) {
        return new LoadImpl(index, resource);
    }

    @Override
    protected Load getInjection() {
        return this;
    }

    @Override
    public ConnectableType getType() {
        return ConnectableType.LOAD;
    }

    @Override
    public LoadType getLoadType() {
        return resource.getAttributes().getLoadType();
    }

    @Override
    public Load setLoadType(LoadType loadType) {
        resource.getAttributes().setLoadType(loadType);
        return this;
    }

    @Override
    public double getP0() {
        return resource.getAttributes().getP0();
    }

    @Override
    public Load setP0(double p0) {
        resource.getAttributes().setP0(p0);
        return this;
    }

    @Override
    public double getQ0() {
        return resource.getAttributes().getQ0();
    }

    @Override
    public Load setQ0(double q0) {
        resource.getAttributes().setQ0(q0);
        return this;
    }
}
