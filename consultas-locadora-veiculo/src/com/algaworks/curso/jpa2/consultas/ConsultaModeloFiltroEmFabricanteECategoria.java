package com.algaworks.curso.jpa2.consultas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.algaworks.curso.jpa2.util.JPAUtil;

public class ConsultaModeloFiltroEmFabricanteECategoria {
	
	 public static void main(String[] args) {
		 EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		 EntityManager em = emf.createEntityManager();
		 
		 String query = "select mc.descricao "
		 		+ "from ModeloCarro mc "
		 		+ "where mc.fabricante.nome = 'Honda' "
		 		+ "and mc.categoria in ('SEDAN_MEDIO', 'HATCH_COMPACTO')";
		 
		 List<String> modelos = em.createQuery(query, String.class).getResultList();
		 
		 for (String modelo : modelos) {
			 System.out.println("Modelo.: " + modelo);
		 }
		 
		 System.out.println("-----------------------------------------");
		 
		 String query1 = "select mc.descricao "
		 		+ "from ModeloCarro mc "
		 		+ "where mc.fabricante.nome = 'Honda' "
		 		+ "and mc.categoria like 'HATCH%'";
		 
		 List<String> modelos1 = em.createQuery(query1, String.class).getResultList();
		 
		 for (String modelo1 : modelos1) {
			 System.out.println("Modelo.: " + modelo1);
		 }
		 
		 em.close();
	}
	 
}
