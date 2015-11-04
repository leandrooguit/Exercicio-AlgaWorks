package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.ProfessorDAO;
import com.algaworks.curso.jpa2.modelo.Professor;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProfessorBean implements Serializable {

	private List<Professor> professores = new ArrayList<Professor>();

	private Professor professorSelecionado;

	@Inject
	private ProfessorDAO dao;

	@PostConstruct
	public void inicializar() {
		professores = dao.buscarTodos();
	}

	public void excluir() {
		try {
			dao.excluir(professorSelecionado);
			this.professores.remove(professorSelecionado);
			FacesUtil
					.addSuccessMessage("Turma "
							+ professorSelecionado.getNome()
							+ " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Professor getProfessorSelecionado() {
		return professorSelecionado;
	}

	public void setProfessorSelecionado(Professor professorSelecionado) {
		this.professorSelecionado = professorSelecionado;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

}
