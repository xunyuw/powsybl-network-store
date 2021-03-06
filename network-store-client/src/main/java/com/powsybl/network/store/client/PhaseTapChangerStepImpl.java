/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.network.store.client;

import com.powsybl.iidm.network.PhaseTapChangerStep;
import com.powsybl.network.store.model.PhaseTapChangerStepAttributes;

/**
 * @author Abdelsalem Hedhili <abdelsalem.hedhili at rte-france.com>
 */

public class PhaseTapChangerStepImpl implements PhaseTapChangerStep {

    PhaseTapChangerStepAttributes attributes;

    public PhaseTapChangerStepImpl(PhaseTapChangerStepAttributes attributes) {
        this.attributes = attributes;
    }

    public double getRho() {
        return attributes.getRho();
    }

    public PhaseTapChangerStepImpl setRho(double rho) {
        attributes.setRho(rho);
        return this;
    }

    public double getR() {
        return attributes.getR();
    }

    public PhaseTapChangerStepImpl setR(double r) {
        attributes.setR(r);
        return this;
    }

    public double getX() {
        return attributes.getX();
    }

    public PhaseTapChangerStepImpl setX(double x) {
        attributes.setX(x);
        return this;
    }

    public double getB() {
        return attributes.getB();
    }

    public PhaseTapChangerStepImpl setB(double b) {
        attributes.setB(b);
        return this;
    }

    public double getG() {
        return attributes.getG();
    }

    public PhaseTapChangerStepImpl setG(double g) {
        attributes.setG(g);
        return this;
    }

    @Override
    public double getAlpha() {
        return attributes.getAlpha();
    }

    @Override
    public PhaseTapChangerStep setAlpha(double alpha) {
        attributes.setAlpha(alpha);
        return this;
    }
}
