package com.algaworks.curso.jpa2.consultas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.algaworks.curso.jpa2.util.JPAUtil;

public class ConsultaModeloFiltrandoFabricante {

	public static void main(String[] args) {
		EntityManagerFactory emf = JPAUtil.createEntityManager().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
	    List<String> descricoes = em.createQuery("select mc.descricao from ModeloCarro mc where mc.fabricante.nome='Honda'", 
	    		String.class).getResultList();
		
	    for (String descricao : descricoes) {
	    	System.out.println("Descrição.: " + descricao);
	    }
	    
		em.close();
	}

}
