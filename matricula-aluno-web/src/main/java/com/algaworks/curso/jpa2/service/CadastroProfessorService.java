package com.algaworks.curso.jpa2.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.ProfessorDAO;
import com.algaworks.curso.jpa2.modelo.Professor;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CadastroProfessorService implements Serializable {

	@Inject
	private ProfessorDAO dao;

	@Transactional
	public void salvar(Professor professor) throws NegocioException {

		if (professor.getNome() == null
				|| professor.getNome().trim().equals("")) {
			throw new NegocioException("O nome é obrigatório");
		}

		if (professor.getEspecialidade() == null
				|| professor.getEspecialidade().trim().equals("")) {
			throw new NegocioException("A especialidade é obrigatório");
		}

		this.dao.salvar(professor);
	}

}
