package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class TurmaDAO implements Serializable {

	@Inject
	private EntityManager manager;

	public Turma buscarPeloCodigo(Long codigo) {
		return manager.find(Turma.class, codigo);
	}

	public void salvar(Turma turma) {
		manager.merge(turma);
	}

	@SuppressWarnings("unchecked")
	public List<Turma> buscarTodas() {
		return manager.createQuery("from Turma").getResultList();
	}

	@Transactional
	public void excluir(Turma turma) throws NegocioException {
		turma = manager.find(Turma.class, turma.getCodigo());
		try {
			manager.remove(turma);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Turma não pode ser excluído.");
		}
	}

}
