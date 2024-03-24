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
import entity.HouseholdEntity;

public class JPACriteriaApi {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		try {
			// Begin Transaction
			entityTransaction.begin();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<GeographicAreaEntity> geoAreaCritQuery = criteriaBuilder
					.createQuery(GeographicAreaEntity.class);
			Root<GeographicAreaEntity> geoArea = geoAreaCritQuery.from(GeographicAreaEntity.class);

			// Question 2, find geographic area with ID 10
			GeographicAreaEntity ga = entityManager.find(GeographicAreaEntity.class, 10);
			System.out.println(ga);

			// Question 3, Geographic Area information for the Level 2
			GeographicLevelTwo(entityManager, criteriaBuilder, geoAreaCritQuery, geoArea);

			// Question 4 - Part A
			OneCoupleCensus(entityManager, criteriaBuilder);
			
			// Question 4 - Part B
			TwoOrMoreMembers(entityManager, criteriaBuilder);
			
			// Question 4 - Part C
			OneEarnerHousehold(entityManager, criteriaBuilder);
			
			//Question 4 - Part D
			TotalIncome(entityManager, criteriaBuilder);

			// QUESTION 5 - PART A
			MultiSelectTen(entityManager, geoAreaCritQuery, geoArea);

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

			// QUESTION 5 - PART E
			CriteriaQuery<Object[]> groupByQuery = criteriaBuilder.createQuery(Object[].class);
			Root<GeographicAreaEntity> geo2 = groupByQuery.from(GeographicAreaEntity.class);
			groupByQuery.multiselect(geo2.get("level"), criteriaBuilder.count(geo2)).groupBy(geo2.get("level"));

			// Display Output
			TypedQuery<Object[]> query = entityManager.createQuery(groupByQuery);
			List<Object[]> groupByClauseList = query.getResultList();
			System.out.println("********** QUESTION 5 - PART E **********");
			System.out.printf("%-20s %-20s %n", "Level", "Total Geo Area");
			groupByClauseList.forEach(dept -> System.out.printf("%-20s %-20s %n", dept[0], dept[1]));

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

	// Question 3 Geographic Area information for the Level 2
	public static void GeographicLevelTwo(EntityManager manager, CriteriaBuilder criteriaBuilder,
			CriteriaQuery<GeographicAreaEntity> critQuery, Root<GeographicAreaEntity> geoArea) {
		Predicate predicate = criteriaBuilder.equal(geoArea.get("level"), "2");
		critQuery.where(predicate);
		CriteriaQuery<GeographicAreaEntity> whereClause = critQuery.select(geoArea);

		TypedQuery<GeographicAreaEntity> selectQuery = manager.createQuery(whereClause);
		List<GeographicAreaEntity> geographicAreaEntityList = selectQuery.getResultList();
		System.out.println("************************** Question 3 Geographic Area for Level 2 ****************************");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", "ID", "Code", "Level", "Name", "AlternativeCode");
		geographicAreaEntityList
				.forEach(gaEL -> System.out.printf("%-20s %-20s %-20s %-20s %-20s %n", gaEL.getGeographicAreaID(),
						gaEL.getCode(), gaEL.getLevel(), gaEL.getName(), gaEL.getAlternativeCode()));
	}

	// Question 4 - A
	public static void OneCoupleCensus(EntityManager manager, CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<HouseholdEntity> household = criteriaQuery.from(HouseholdEntity.class);

		// where clause
		Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(household.get("householdType"), "4"),
				criteriaBuilder.equal(household.get("censusYear"), "2"));

		criteriaQuery.where(predicate);

		// count the number of records
		criteriaQuery.select(criteriaBuilder.count(household));

		Long totalCount = manager.createQuery(criteriaQuery).getSingleResult();
		
		// Display Output
		System.out.println("************************** Question 4 - Part A ****************************");
		System.out.println("Total number of records: " + totalCount);

	}
	
	// Question 4 - B
	public static void TwoOrMoreMembers (EntityManager manager, CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<HouseholdEntity> household = criteriaQuery.from(HouseholdEntity.class);

		// where clause
		Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(household.get("householdSize"), "3"),
				criteriaBuilder.equal(household.get("censusYear"), "2"));

		criteriaQuery.where(predicate);

		// count the number of records
		criteriaQuery.select(criteriaBuilder.count(household));

		Long totalCount = manager.createQuery(criteriaQuery).getSingleResult();
		
		// Display Output
		System.out.println("************************** Question 4 - Part B ****************************");
		System.out.println("Total number of records: " + totalCount);
	}
	
	// Question 4 - C
	public static void OneEarnerHousehold (EntityManager manager, CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<HouseholdEntity> household = criteriaQuery.from(HouseholdEntity.class);

		// where clause
		Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(household.get("householdEarners"), "3"),
				criteriaBuilder.equal(household.get("censusYear"), "2"));

		criteriaQuery.where(predicate);

		// count the number of records
		criteriaQuery.select(criteriaBuilder.count(household));

		Long totalCount = manager.createQuery(criteriaQuery).getSingleResult();
		
		// Display Output
		System.out.println("************************** Question 4 - Part C ****************************");
		System.out.println("Total number of records: " + totalCount);
	}
	
	// Question 4 - D
	public static void TotalIncome (EntityManager manager, CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<HouseholdEntity> household = criteriaQuery.from(HouseholdEntity.class);

		// where clause
		Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(household.get("totalIncome"), "15"),
				criteriaBuilder.equal(household.get("censusYear"), "2"));

		criteriaQuery.where(predicate);

		// count the number of records
		criteriaQuery.select(criteriaBuilder.count(household));

		Long totalCount = manager.createQuery(criteriaQuery).getSingleResult();
		
		// Display Output
		System.out.println("************************** Question 4 - Part D ****************************");
		System.out.println("Total number of records: " + totalCount);
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
		incomeList.forEach(income -> System.out.printf("%-20s %-20s %n", income.getId(), income.getDescription()));
	}

}
