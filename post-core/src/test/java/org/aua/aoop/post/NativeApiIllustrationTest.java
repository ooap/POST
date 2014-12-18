package org.aua.aoop.post;

import org.aua.aoop.post.entiries.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class NativeApiIllustrationTest{
    private SessionFactory sessionFactory;
    private StandardServiceRegistry serviceRegistry;

    @Before
    public void setUp() throws Exception {
		// A SessionFactory is set up once for an application
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	@After
    public void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

    @Test
	public void testBasicUsage() {
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new User("User1"));
		session.save( new User( "User2" ));
		session.getTransaction().commit();
		session.close();

		// now lets pull events from the database and list them
		session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> result = session.createCriteria(User.class).list();
		for ( User event :  result ) {
			System.out.println( "User (" + event.getId() + ") : " + event.getName() );
		}
        session.getTransaction().commit();
        session.close();
	}
}
