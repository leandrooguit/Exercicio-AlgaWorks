package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.MatriculaDAO;
import com.algaworks.curso.jpa2.dao.TurmaDAO;
import com.algaworks.curso.jpa2.modelo.Aluno;
import com.algaworks.curso.jpa2.modelo.Matricula;
import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.service.CadastroAlunoService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAlunoBean implements Serializable {

	private Aluno aluno;

	private String observacao;

	private List<Turma> turmas;

	List<Turma> turmasAux = new ArrayList<Turma>();

	private List<Turma> turmaSelecionadas;

	private List<Matricula> matriculas;

	@Inject
	private CadastroAlunoService service;

	@Inject
	private TurmaDAO turmaDAO;

	@Inject
	private MatriculaDAO matriculaDAO;

	@PostConstruct
	public void inicializar() {
		this.limpar();

		this.turmas = this.turmaDAO.buscarTodas();
	}

	public void salvar() {
		try {
			if (aluno.getCodigo() == null) {
				this.service.salvar(aluno, observacao, turmaSelecionadas);
			} else {
				this.service.atualizar(aluno, observacao, turmaSelecionadas,
						turmasAux);
			}
			FacesUtil.addSuccessMessage("Aluno salvo com sucesso.");
			this.limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void limpar() {
		this.aluno = new Aluno();
		turmaSelecionadas = new ArrayList<Turma>();
		observacao = null;
	}

	public boolean isTeste() {
		if (aluno.getCodigo() == null) {
			return false;
		}
		if (matriculas == null) {
			matriculas = matriculaDAO.buscarMatriculasAssociadasAluno(aluno);
			observacao = matriculas.get(0).getObservacao();
			for (Matricula matricula : matriculas) {
				turmaSelecionadas.add(matricula.getTurma());
				turmasAux.add(matricula.getTurma());
			}
		}
		return true;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Turma> getTurmaSelecionadas() {
		return turmaSelecionadas;
	}

	public void setTurmaSelecionadas(List<Turma> turmaSelecionadas) {
		this.turmaSelecionadas = turmaSelecionadas;
	}

}
