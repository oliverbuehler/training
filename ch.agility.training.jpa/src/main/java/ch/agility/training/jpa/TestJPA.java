package ch.agility.training.jpa;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Transient;

import org.junit.Ignore;
import org.junit.Test;

public class TestJPA extends DatabaseTest {

	@Transient
	private String test;

	@Test
	@Ignore
	public void test() throws SQLException {
		Connection conn = getConnection();
		System.out.println(executeSqlQuery("show schemas"));
		executeSql("create schema jpa");
		System.out.println(executeSqlQuery("show schemas"));
		executeSql("create table test(name varchar(10))");
		System.out.println(executeSqlQuery("show tables"));
		System.out.println(executeSqlQuery("show columns from public.test"));
	}

	@Test
	public void testPersistTransientMasterDetail() throws SQLException {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("ch.agility.training.jpa");
		EntityManager em = emf.createEntityManager();

		Master master = new Master();
		master.addDetail(new Detail());
		master.addDetail(new Detail());

		em.getTransaction().begin();
		em.persist(master);
		em.getTransaction().commit();

		System.out.println(executeSqlQuery("select * from detail"));
	}

}
