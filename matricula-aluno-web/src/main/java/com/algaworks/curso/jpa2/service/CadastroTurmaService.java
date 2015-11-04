package com.algaworks.curso.jpa2.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.MatriculaDAO;
import com.algaworks.curso.jpa2.dao.TurmaDAO;
import com.algaworks.curso.jpa2.modelo.Matricula;
import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CadastroTurmaService implements Serializable {

	@Inject
	private TurmaDAO dao;

	@Inject
	private MatriculaDAO matriculaDAO;

	@Transactional
	public void salvar(Turma turma) throws NegocioException {

		if (turma.getDescricao() == null
				|| turma.getDescricao().trim().equals("")) {
			throw new NegocioException("A descrição é obrigatório");
		}

		this.dao.salvar(turma);
	}

	@Transactional
	public void excluir(Turma turma) throws NegocioException {
		List<Matricula> matriculas = matriculaDAO
				.buscarMatriculasAssociadasTurma(turma);
		if (matriculas != null && !matriculas.isEmpty()) {
			throw new NegocioException(
					"Ainda existe alunos matrículas nessa turma.");
		}

		dao.excluir(turma);
	}

}
