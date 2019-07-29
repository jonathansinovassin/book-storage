package data;

import data.model.Book;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateDbUnitTestCase extends DBTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateDbUnitTestCase.class);

    private static SessionFactory sessionFactory;
    protected Session session;

    public HibernateDbUnitTestCase() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:books");
    }

    @Before
    public void setUp() throws Exception {
        LOG.info("Loading hibernate...");
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Book.class);
            configuration.setProperty("hibernate.dialect",
                    "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.connection.driver_class",
                    "org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:books");
            configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            configuration.setProperty("hibernate.show_sql", "true");
            sessionFactory = configuration.buildSessionFactory();
        }

        session = sessionFactory.openSession();

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        session.close();
        super.tearDown();
    }

    protected IDataSet getDataSet() throws Exception {
        throw new Exception("Specify data set for test: " + this.getClass().getSimpleName());
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
}