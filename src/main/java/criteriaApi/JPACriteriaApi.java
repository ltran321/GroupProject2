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

import entity.AgeEntity;
import entity.TotalIncomeEntity;
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
			
			// Begin Transaction
			entityTransaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			// QUESTION 5 - PART A
			CriteriaQuery<GeographicAreaEntity> geoAreaCritQuery = criteriaBuilder
					.createQuery(GeographicAreaEntity.class);
			Root<GeographicAreaEntity> geoArea = geoAreaCritQuery.from(GeographicAreaEntity.class);
			MultiSelectTen(entityManager, geoAreaCritQuery, geoArea);
			
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

			// QUESTION 5 - PART B
			CriteriaQuery<AgeEntity> ageCritQuery = criteriaBuilder.createQuery(AgeEntity.class);
			Root<AgeEntity> age = ageCritQuery.from(AgeEntity.class);
			TopTwentyCombined(entityManager, criteriaBuilder, ageCritQuery, age);

			// QUESTION 5 - PART C
			GetGeoAreaPeterborough(entityManager, criteriaBuilder, geoAreaCritQuery, geoArea);

			// QUESTION 5 - PART D
			CriteriaQuery<TotalIncomeEntity> incomeCritQuery = criteriaBuilder.createQuery(TotalIncomeEntity.class);
			Root<TotalIncomeEntity> income = incomeCritQuery.from(TotalIncomeEntity.class);
			BetweenTenAndTwenty(entityManager, criteriaBuilder, incomeCritQuery, income);

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
	public static void MultiSelectTen(EntityManager enMan, CriteriaQuery<GeographicAreaEntity> critQuery,
			Root<GeographicAreaEntity> geoArea) {

		critQuery.multiselect(geoArea.get("code"), geoArea.get("level"), geoArea.get("name"));
		CriteriaQuery<GeographicAreaEntity> multiSelect = critQuery.select(geoArea);

		// Display Output
		TypedQuery<GeographicAreaEntity> query = enMan.createQuery(multiSelect).setMaxResults(10);
		List<GeographicAreaEntity> geoAreaList = query.getResultList();
		System.out.println("********** QUESTION 5 - PART A **********");
		System.out.printf("%-20s %-20s %-20s %n", "Code", "Level", "Name");
		geoAreaList.forEach(
				geo -> System.out.printf("%-20s %-20s %-20s %n", geo.getCode(), geo.getLevel(), geo.getName()));

	}

	// QUESTION 5 - PART B
	public static void TopTwentyCombined(EntityManager enMan, CriteriaBuilder critBuilder,
			CriteriaQuery<AgeEntity> critQuery, Root<AgeEntity> age) {

		critQuery.orderBy(critBuilder.desc(age.get("combined")));
		CriteriaQuery<AgeEntity> orderBy = critQuery.select(age);

		// Display Output
		TypedQuery<AgeEntity> query = enMan.createQuery(orderBy).setMaxResults(20);
		List<AgeEntity> ageList = query.getResultList();
		System.out.println("********** QUESTION 5 - PART B **********");
		System.out.printf("%-20s %-20s %-20s %-20s %n", "AgeGroup", "CensusYear", "GeographicArea", "Combined");
		ageList.forEach(ag -> System.out.printf("%-20s %-20s %-20s %-20s %n", ag.getAgeGroup(), ag.getCensusYear(),
				ag.getGeographicArea(), ag.getCombined()));
	}

	// QUESTION 5 - PART C
	public static void GetGeoAreaPeterborough(EntityManager enMan, CriteriaBuilder critBuilder,
			CriteriaQuery<GeographicAreaEntity> critQuery, Root<GeographicAreaEntity> geoArea) {

		Predicate predicate = critBuilder.equal(geoArea.get("name"), "Peterborough");
		critQuery.where(predicate);
		CriteriaQuery<GeographicAreaEntity> whereClause = critQuery.select(geoArea);

		// Display Output
		TypedQuery<GeographicAreaEntity> query = enMan.createQuery(whereClause);
		List<GeographicAreaEntity> geoList = query.getResultList();
		System.out.println("********** QUESTION 5 - PART C **********");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", "GeographicAreaID", "Code", "Level", "Name",
				"AlternativeCode");
		geoList.forEach(geo -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", geo.getGeographicAreaID(),
				geo.getCode(), geo.getLevel(), geo.getName(), geo.getAlternativeCode()));
	}

	// QUESTION 5 - PART D
	public static void BetweenTenAndTwenty(EntityManager enMan, CriteriaBuilder critBuilder,
			CriteriaQuery<TotalIncomeEntity> critQuery, Root<TotalIncomeEntity> totIncome) {

		Predicate predicate = critBuilder.between(totIncome.get("id"), 10, 20);
		critQuery.where(predicate);
		CriteriaQuery<TotalIncomeEntity> whereClause = critQuery.select(totIncome);
		
		// Display Output
		TypedQuery<TotalIncomeEntity> query = enMan.createQuery(whereClause);
		List<TotalIncomeEntity> incomeList = query.getResultList();
		System.out.println("********** QUESTION 5 - PART D **********");
		System.out.printf("%-20s %-20s %n", "ID", "IncomeDescription");
		incomeList.forEach(income -> System.out.printf("%-20s %-20s %n",
				income.getId(), income.getDescription()));
	}

	// QUESTION 5 - PART E
	public static void GroupByLevel() {

	}
}
