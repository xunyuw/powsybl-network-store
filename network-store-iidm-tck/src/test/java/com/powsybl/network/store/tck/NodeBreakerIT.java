/**
 * Copyright (c) 2019, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.powsybl.network.store.tck;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.powsybl.iidm.network.tck.AbstractNodeBreakerTest;

import com.powsybl.network.store.server.CassandraConfig;
import com.powsybl.network.store.server.CassandraConstants;
import com.powsybl.network.store.server.NetworkStoreApplication;
import com.powsybl.network.store.tck.cassandra.CassandraUnitDependencyInjectionGlobalExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {NetworkStoreApplication.class, CassandraConfig.class})
@TestExecutionListeners(listeners = {CassandraUnitDependencyInjectionGlobalExecutionListener.class},
                        mergeMode = MERGE_WITH_DEFAULTS)
@CassandraDataSet(value = "iidm.cql", keyspace = CassandraConstants.KEYSPACE_IIDM)
@EmbeddedCassandra(timeout = 60000L)
@TestPropertySource(properties = { "spring.config.location=classpath:application.yaml" })
public class NodeBreakerIT extends AbstractNodeBreakerTest { }
