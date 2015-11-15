package com.algaworks.curso.jpa2.consultas;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;

import com.algaworks.curso.jpa2.util.JPAUtil;

public class ConsultaAluguelPorData {

	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		Calendar inicioCalendar = Calendar.getInstance();
		inicioCalendar.set(2013, 7,  12, 7, 0); // 12 de Agosto de 2013 7:00 horas
		Date inicio = inicioCalendar.getTime();
				
		Calendar fimCalendar = Calendar.getInstance();
		fimCalendar.set(2015, 7, 24, 18, 0); // 18 de Agosto de 2013 18:00 horas
		Date fim = fimCalendar.getTime();
		
		String jpql = "select count(a) "
				+ "from Aluguel a "
				+ "where a.dataEntrega BETWEEN :inicio AND :fim";
		
		Long quantidadeEntregas = em.createQuery(jpql, Long.class)
				.setParameter("inicio", inicio, TemporalType.DATE)
				.setParameter("fim",  fim, TemporalType.DATE)
				.getSingleResult();

		System.out.println("Quantidade de entregas: " + quantidadeEntregas);
		
		em.close();
	}
	
}
