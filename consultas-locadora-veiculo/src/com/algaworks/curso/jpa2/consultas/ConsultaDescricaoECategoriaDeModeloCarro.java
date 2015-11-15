package com.algaworks.curso.jpa2.consultas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.algaworks.curso.jpa2.util.JPAUtil;

public class ConsultaDescricaoECategoriaDeModeloCarro {

	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		String jpql = "select mc.descricao, mc.categoria from ModeloCarro mc";
		List<Object[]> resultados = em.createQuery(jpql).getResultList();
		
		for (Object[] object : resultados) {
			System.out.println("Descri��o: " + object[0] + ". E categoria: " + object[1]);
		}
		
		em.close();
	}

}
