package ch.agility.training.jpa;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Transient;

import org.junit.Ignore;
import org.junit.Test;

import ch.agility.training.jpa.entity.Master;

public class TestJPA extends DatabaseTest {

	@Transient
	private String test;

	// @Before
	// public void setUp() throws SQLException {
	// DatabaseHelper.executeSql("create table is not exists master()");
	// }

	@Test
	@Ignore
	public void test() throws SQLException {
		System.out.println(DatabaseHelper.executeSqlQuery("show schemas"));
		// DatabaseHelper.executeSql("create schema jpa");
		// System.out.println(DatabaseHelper.executeSqlQuery("show schemas"));
		// DatabaseHelper.executeSql("create table jpa.test (name
		// varchar(10));");
		// System.out.println(DatabaseHelper.executeSqlQuery("show tables from
		// JPA"));
		// DatabaseHelper.executeSql("insert into jpa.test values ('1')");
		// System.out.println(DatabaseHelper.executeSqlQuery("select * from
		// jpa.test"));
		// System.out.println(DatabaseHelper.executeSqlQuery("show columns from
		// jpa.test"));
	}

	@Test
	// @Ignore
	public void testPersistence() throws SQLException {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("ch.agility.training.jpa");
		EntityManager em = emf.createEntityManager();

		System.out.println(DatabaseHelper.executeSqlQuery("show schemas"));
		System.out.println(DatabaseHelper.executeSqlQuery("show tables"));

		Master masterEntity = new Master();
		masterEntity.setStringAttribute("string1");

		em.getTransaction().begin();
		em.persist(masterEntity);
		em.getTransaction().commit();
		System.out.println(masterEntity.getId());
		System.out.println(
				DatabaseHelper.executeSqlQuery("select * from jpa.master"));

		Master reloaded = em.find(Master.class, masterEntity.getId());
		System.out.println(reloaded.getStringAttribute());

		em.close();
		emf.close();
	}

}
