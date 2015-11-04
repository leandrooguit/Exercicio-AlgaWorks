package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.service.CadastroTurmaService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroTurmaBean implements Serializable {

	private Turma turma;
	@Inject
	private CadastroTurmaService service;

	@PostConstruct
	public void inicializar() {
		this.limpar();
	}

	public void salvar() {
		try {
			this.service.salvar(turma);
			FacesUtil.addSuccessMessage("Turma salva com sucesso.");
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
		this.turma = new Turma();
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public boolean isTeste() {
		if (turma.getCodigo() == null) {
			return false;
		}
		return true;
	}

}
