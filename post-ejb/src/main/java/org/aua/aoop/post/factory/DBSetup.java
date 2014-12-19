package org.aua.aoop.post.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DBSetup {

    private static DBSetup db;
    private StandardServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;

    public StandardServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void setUp() throws Exception {
        // A SessionFactory is set up once for an application
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    private DBSetup() {}

    public static DBSetup getInstance() {
        if (db == null) {
            db = new DBSetup();

            // set up the first time
            try {
                db.setUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return db;
    }
}
