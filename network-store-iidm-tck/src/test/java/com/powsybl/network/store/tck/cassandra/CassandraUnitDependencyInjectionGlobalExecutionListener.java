package com.powsybl.network.store.tck.cassandra;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.spring.AbstractCassandraUnitTestExecutionListener;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

/**
 * The goal of this abstract listener is to provide utility methods for its subclasses to be able to :
 * - start an embedded Cassandra
 * - load dataset into Cassandra keyspace
 *
 * @author Gaëtan Le Brun
 */
public class CassandraUnitDependencyInjectionGlobalExecutionListener extends AbstractCassandraUnitTestExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraUnitDependencyInjectionGlobalExecutionListener.class);
    private static boolean initialized = false;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        startServer(testContext);
    }

    protected void startServer(TestContext testContext) throws Exception {
        EmbeddedCassandra embeddedCassandra = Objects.requireNonNull(AnnotationUtils.findAnnotation(testContext.getTestClass(), EmbeddedCassandra.class),
                "CassandraUnitTestExecutionListener must be used with @EmbeddedCassandra on " + testContext.getTestClass());
        if (!initialized) {
            String yamlFile = Optional.ofNullable(embeddedCassandra.configuration()).get();
            String tmpDir = embeddedCassandra.tmpDir();
            long timeout = embeddedCassandra.timeout();
            EmbeddedCassandraServerHelper.startEmbeddedCassandra(yamlFile, tmpDir, timeout);

            CassandraDataSet cassandraDataSet = AnnotationUtils.findAnnotation(testContext.getTestClass(), CassandraDataSet.class);
            if (cassandraDataSet != null) {
                String keyspace = cassandraDataSet.keyspace();
                List<String> dataset = dataSetLocations(testContext, cassandraDataSet);
                ListIterator<String> datasetIterator = dataset.listIterator();

                CQLDataLoader cqlDataLoader = new CQLDataLoader(EmbeddedCassandraServerHelper.getSession());
                while (datasetIterator.hasNext()) {
                    String next = datasetIterator.next();
                    boolean dropAndCreateKeyspace = datasetIterator.previousIndex() == 0;
                    cqlDataLoader.load(new ClassPathCQLDataSet(next, dropAndCreateKeyspace, dropAndCreateKeyspace, keyspace));
                }
            }
            initialized = true;
        }
    }

    private List<String> dataSetLocations(TestContext testContext, CassandraDataSet cassandraDataSet) {
        String[] dataset = cassandraDataSet.value();
        if (dataset.length == 0) {
            String alternativePath = alternativePath(testContext.getTestClass(), true, cassandraDataSet.type().name());
            if (testContext.getApplicationContext().getResource(alternativePath).exists()) {
                dataset = new String[]{alternativePath.replace(ResourceUtils.CLASSPATH_URL_PREFIX + "/", "")};
            } else {
                alternativePath = alternativePath(testContext.getTestClass(), false, cassandraDataSet.type().name());
                if (testContext.getApplicationContext().getResource(alternativePath).exists()) {
                    dataset = new String[]{alternativePath.replace(ResourceUtils.CLASSPATH_URL_PREFIX + "/", "")};
                } else {
                    LOGGER.info("No dataset will be loaded");
                }
            }
        }
        return Arrays.asList(dataset);
    }

    protected String alternativePath(Class<?> clazz, boolean includedPackageName, String extension) {
        if (includedPackageName) {
            return ResourceUtils.CLASSPATH_URL_PREFIX + "/" + ClassUtils.convertClassNameToResourcePath(clazz.getName()) + "-dataset" + "." + extension;
        } else {
            return ResourceUtils.CLASSPATH_URL_PREFIX + "/" + clazz.getSimpleName() + "-dataset" + "." + extension;
        }
    }

    @Override
    public int getOrder() {
        // since spring 4.1 the default-order is LOWEST_PRECEDENCE. But we want to start EmbeddedCassandra even before
        // the springcontext such that beans can connect to the started cassandra
        return 0;
    }
}
