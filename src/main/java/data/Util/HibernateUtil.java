package data.Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import data.model.Book;

public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private static Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry standardRegistry =
                        new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
                Metadata metaData =
                        new MetadataSources(standardRegistry).addAnnotatedClass(Book.class).getMetadataBuilder().build();
                sessionFactory = metaData.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                LOGGER.error("SessionFactory creation failed", e);
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
