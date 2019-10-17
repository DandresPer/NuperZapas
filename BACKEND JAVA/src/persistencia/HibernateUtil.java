package persistencia;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil { 
	//Construir SessionFactory estatico
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	 private static SessionFactory buildSessionFactory() {
	        try {
	            SessionFactory sessionFactory = new Configuration()
	                    .configure("hibernate.cfg.xml").buildSessionFactory();
	            return sessionFactory;
	        } catch (Throwable ex) {
	            System.err.println("FALLO AL CREAR SESSIONFACTORY: " + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
                                         
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public static void shutdown() {
	        getSessionFactory().close();
	    }
}
