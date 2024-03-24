package criteriaApi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import entity.EmployeesEntity;
import entity.GeographicAreaEntity;

public class JPACriteriaApi {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			
			// Question 2, find geographic area with ID 10
			GeographicAreaEntity ga = entityManager.find(GeographicAreaEntity.class,10);
			
			System.out.println(ga);
			
			
			//Begin Transaction
			entityTransaction.begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			
			//QUESTION 5 - PART A
            CriteriaQuery<GeographicAreaEntity> geoAreaCritQuery = criteriaBuilder.createQuery(GeographicAreaEntity.class);
            Root<GeographicAreaEntity> geoArea = geoAreaCritQuery.from(GeographicAreaEntity.class);
			MultiSelectTen(entityManager,geoAreaCritQuery,geoArea);
			
			// Question 3, Geographic Area information for the Level 2.
			
            Predicate predicate = criteriaBuilder.equal(geoArea.get("level"), "2");
            geoAreaCritQuery.where(predicate);
            CriteriaQuery<GeographicAreaEntity> whereClause = geoAreaCritQuery.select(geoArea);

            // Display Output
            TypedQuery<GeographicAreaEntity> selectQuery = entityManager.createQuery(whereClause);
            List<GeographicAreaEntity> geographicAreaEntityList = selectQuery.getResultList();
            System.out.println("************************** Geographic Area for Level 2 ****************************");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", "ID","Code", "Level", "Name", "AlternativeCode");
            geographicAreaEntityList.forEach(gaEL -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n",
            		gaEL.getGeographicAreaID(), gaEL.getCode(), gaEL.getLevel(), gaEL.getName(), gaEL.getAlternativeCode()));
			
			
		} catch (PersistenceException pe) {
			System.out.println("Error: " + pe.getMessage());
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		} finally {
			// Close Connections
			entityManager.close();
			entityManagerFactory.close();
		}
	}

	// QUESTION 5 - PART A
	public static void MultiSelectTen(EntityManager enMan, CriteriaQuery<GeographicAreaEntity> critQuery, Root<GeographicAreaEntity> geoArea) {

		critQuery.multiselect(geoArea.get("code"),geoArea.get("level"),geoArea.get("name"));
        CriteriaQuery<GeographicAreaEntity> multiSelect = critQuery.select(geoArea);
        
        //Display Output
        TypedQuery<GeographicAreaEntity> query = enMan.createQuery(multiSelect).setMaxResults(10);
        List<GeographicAreaEntity> geoAreaList = query.getResultList();
        System.out.println("********** QUESTION 5 - PART A **********");
        System.out.printf("%-20s %-20s %-20s %n", "Code", "Level", "Name");
        geoAreaList.forEach(emp -> System.out.printf("%-20s %-20s %-20s %n",
                emp.getCode(), emp.getLevel(), emp.getName()));

	}

	// QUESTION 5 - PART B
	public static void TopTwenty() {

	}

	// QUESTION 5 - PART C
	public static void GetGeoAreaPeterborough() {

	}

	// QUESTION 5 - PART D
	public static void BetweenTenAndTwenty() {

	}

	// QUESTION 5 - PART E
	public static void GroupByLevel() {

	}
}
