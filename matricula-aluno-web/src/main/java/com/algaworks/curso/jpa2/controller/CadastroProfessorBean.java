package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.modelo.Professor;
import com.algaworks.curso.jpa2.service.CadastroProfessorService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProfessorBean implements Serializable {

	private Professor professor;
	@Inject
	private CadastroProfessorService service;

	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	public void salvar() {
		try {
			this.service.salvar(professor);
			FacesUtil.addSuccessMessage("Professor salvo com sucesso.");
			limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil
					.addErrorMessage("Erro desconhecido. Contatar o administrador.");
		}
	}

	public void limpar() {
		this.professor = new Professor();
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public boolean isTeste() {
		if (professor.getCodigo() == null) {
			return false;
		}
		return true;
	}

}
