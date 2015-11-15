package com.algaworks.curso.jpa2.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import com.algaworks.curso.jpa2.dao.AlunoDAO;
import com.algaworks.curso.jpa2.dao.MatriculaDAO;
import com.algaworks.curso.jpa2.modelo.Aluno;
import com.algaworks.curso.jpa2.modelo.AlunoVO;
import com.algaworks.curso.jpa2.modelo.Matricula;
import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.util.jpa.Transactional;

public class CadastroAlunoService implements Serializable {

	@Inject
	private AlunoDAO dao;
	@Inject
	private MatriculaDAO matriculaDAO;

	@Transactional
	public void salvar(Aluno aluno, String observacao,
			List<Turma> turmaSelecionadas) throws NegocioException {

		validarCampos(aluno, observacao, turmaSelecionadas);

		List<Matricula> matriculas = criarMatriculas(aluno, turmaSelecionadas,
				observacao);
		gerarMatriculaAluno(aluno);
		this.dao.salvar(aluno);
		this.matriculaDAO.salvar(matriculas);
	}

	private void validarCampos(Aluno aluno, String observacao,
			List<Turma> turmaSelecionadas) throws NegocioException {
		if (aluno.getNome() == null || aluno.getNome().trim().equals("")) {
			throw new NegocioException("O nome é obrigatório");
		}

		if (observacao == null || observacao.equals("")) {
			throw new NegocioException("A forma de pagamento é obrigatório");
		}

		if (turmaSelecionadas == null || turmaSelecionadas.isEmpty()) {
			throw new NegocioException("A turma é obrigatório");
		}
	}

	@Transactional
	public void atualizar(Aluno aluno, String observacao,
			List<Turma> turmaSelecionadas, List<Turma> turmaAux)
			throws NegocioException {
		validarCampos(aluno, observacao, turmaSelecionadas);
		List<Matricula> matriculasNovas = matricularEmNovaTurma(aluno,
				turmaSelecionadas, turmaAux, observacao);
		List<Turma> turmaTrancada = trancarMatricula(turmaSelecionadas,
				turmaAux);
		this.dao.salvar(aluno);
		this.matriculaDAO.salvar(matriculasNovas);
		if (!turmaTrancada.isEmpty()) {
			this.matriculaDAO.excluir(getMatriculasParaTrancar(aluno,
					turmaTrancada));
		}

		List<Matricula> matriculasAtualizar = this.matriculaDAO
				.buscarMatriculasAssociadasAluno(aluno);
		List<Matricula> atualizar = new ArrayList<Matricula>();
		for (Matricula matricula : matriculasAtualizar) {
			if (!observacao.equals(matricula.getObservacao())) {
				matricula.setObservacao(observacao);
				atualizar.add(matricula);
			}
		}
		this.matriculaDAO.atualizar(atualizar);
	}

	private List<Matricula> getMatriculasParaTrancar(Aluno aluno,
			List<Turma> turmaTrancada) {
		return this.matriculaDAO.buscarTurmasMatriculasDeUmAluno(aluno,
				turmaTrancada);
	}

	private void gerarMatriculaAluno(Aluno aluno) {
		List<Integer> matriculas = dao.buscarMatriculas();
		Random gerador = new Random();
		Integer matricula = matriculas.isEmpty() ? 10 : matriculas.get(0);
		int numero = gerador.nextInt(10) * matricula;
		aluno.setNumeroMatricula(numero);
	}

	private List<Matricula> matricularEmNovaTurma(Aluno aluno,
			List<Turma> turmaSelecionadas, List<Turma> turmaAux,
			String observacao) {
		List<Turma> novas = new ArrayList<Turma>();
		for (Turma turma : turmaSelecionadas) {
			if (!turmaAux.contains(turma)) {
				novas.add(turma);
			}
		}
		return criarMatriculas(aluno, novas, observacao);
	}

	private List<Turma> trancarMatricula(List<Turma> turmaSelecionadas,
			List<Turma> turmaAux) {
		List<Turma> excluidas = new ArrayList<Turma>();
		for (Turma turma : turmaAux) {
			if (!turmaSelecionadas.contains(turma)) {
				excluidas.add(turma);
			}
		}
		return excluidas;
	}

	private List<Matricula> criarMatriculas(Aluno aluno,
			List<Turma> turmaSelecionadas, String observacao) {
		List<Matricula> matriculas = new ArrayList<Matricula>();
		for (Turma turma : turmaSelecionadas) {
			Matricula matricula = new Matricula();
			matricula.setAluno(aluno);
			matricula.setObservacao(observacao);
			matricula.setTurma(turma);
			matriculas.add(matricula);
		}
		return matriculas;
	}

	@Transactional
	public void excluir(Aluno aluno) throws NegocioException {
		List<Matricula> matriculas = matriculaDAO
				.buscarMatriculasAssociadasAluno(aluno);
		this.matriculaDAO.excluir(matriculas);
		dao.excluir(aluno);
	}

	@Transactional
	public List<AlunoVO> criarAlunoVO(List<Aluno> alunos) {
		List<AlunoVO> vos = new ArrayList<AlunoVO>();
		for (Aluno aluno : alunos) {
			AlunoVO vo = new AlunoVO();
			vo.setAluno(aluno);
			List<Matricula> matriculas = matriculaDAO.buscarTodos(aluno);
			ajustarMatriculas(vo, matriculas);
			vos.add(vo);
		}
		return vos;
	}

	private void ajustarMatriculas(AlunoVO vo, List<Matricula> matriculas) {
		vo.setObservacao(matriculas.get(0).getObservacao());
		String aux = "";
		for (Matricula matricula : matriculas) {
			aux = aux.concat(matricula.getTurma().getDescricao());
			int posicao = matriculas.indexOf(matricula);
			int teste = posicao + 1;
			if (teste != matriculas.size()) {
				aux += ", ";
			}
		}
		vo.setTurma(aux);
	}

}
