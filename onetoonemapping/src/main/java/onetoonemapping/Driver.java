package onetoonemapping;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Driver {
	public static void main(String[] args) {
//		
//		addCar();
//		findByDate(LocalDate.now());
//		findByDate();
//		deleteEngId();
//		allocateEngine(101, 5);
//		findAllCar();
//		dealocateEngine(105) ;
//		findByCarId(103);
//		findByEngId(4);
		deleteCar(102); 
	}

	public static void addCar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Engine e = new Engine();
		e.setCc(3200);
		e.setType("Petrol");

		Car c = new Car();
		c.setBrand("MG");
		c.setModel("MG Hector");
		c.setE(e);
		c.setRegisterDate(LocalDate.of(2025, 4, 12));

		et.begin();
		em.persist(e);
		em.persist(c);
		et.commit();
	}

	public static void findByDate() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		String s = "select c from Car c where c.registerDate=?1";
		et.begin();
		Query q = em.createQuery(s);
		q.setParameter(1, LocalDate.of(2025, 4, 12));
		List<Car> li = q.getResultList();
		li.forEach(al -> System.out.println(al.getId()));
		et.commit();
	}

	public static void deleteEngId() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

//		by using find method
		et.begin();
		Car c = em.find(Car.class, 103);
		c.setE(null);
		em.merge(c);
		et.commit();

	}

	public static void allocateEngine(int i, int j) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		// String jpql = "Update car c set c.engine=:e_id where c.id=:c_id";
		String sql = "Update car set engine_id:e_id where id=:c_id";
		et.begin();
//		Query q = em.createQuery(sql);
		Query q = em.createNativeQuery(sql, Car.class);
		q.setParameter("e_id", j);
		q.setParameter("c_id", i);
		int count = q.executeUpdate();
		System.out.println(count);
		et.commit();
	}

	public static void dealocateEngine(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Car car = em.find(Car.class, c_id); // find the car using Id
		car.setE(null); // remove the engine
		em.merge(car); // update the car in DB
		et.commit();
	}

	public static void findAllCar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		String jpql = "Select c from Car c";
		Query q = em.createQuery(jpql);
		List<Car> c = q.getResultList();
		c.forEach(al -> System.out.println(al));
		et.commit();
	}

	public static void findByCarId(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Car c = em.find(Car.class, c_id);
		System.out.println("MOdel : " + c.getModel() + " " + "Brand : " + c.getBrand() + " " + "Register Date : "
				+ c.getRegisterDate());
		et.commit();
	}

	public static void findByEngId(int e_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		Engine e = em.find(Engine.class, e_id);
		System.out.println("Engine : " + e.getCc() + " " + "Type : "+  e.getType());
	}

	public static void deleteCar(int c_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		et.begin();
		Car c = em.find(Car.class, c_id);
		em.remove(c);
		et.commit();
	}

}