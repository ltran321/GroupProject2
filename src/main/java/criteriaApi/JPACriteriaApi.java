package criteriaApi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import entity.GeographicAreaEntity;

public class JPACriteriaApi {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        try {
        	
        	GeographicAreaEntity ga = entityManager.find(GeographicAreaEntity.class, 10);
            
            System.out.println(ga);
            
        }catch (PersistenceException pe) {
            System.out.println("Error: " + pe.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            // Close Connections
            entityManager.close();
            entityManagerFactory.close();
        }
        
	}
}
