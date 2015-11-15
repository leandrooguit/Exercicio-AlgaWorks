package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.modelo.Professor;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class ProfessorDAO implements Serializable {

	@Inject
	private EntityManager manager;

	public Professor buscarPeloCodigo(Long codigo) {
		return manager.find(Professor.class, codigo);
	}

	public void salvar(Professor professor) {
		manager.merge(professor);
	}

	@SuppressWarnings("unchecked")
	public List<Professor> buscarTodos() {
		return manager.createQuery("from Professor").getResultList();
	}

	@Transactional
	public void excluir(Professor professor) throws NegocioException {
		professor = manager.find(Professor.class, professor.getCodigo());
		try {
			manager.remove(professor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Professor não pode ser excluído.");
		}
	}
}
