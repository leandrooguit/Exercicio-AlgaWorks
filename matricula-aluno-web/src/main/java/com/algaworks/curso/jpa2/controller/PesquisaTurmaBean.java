package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.TurmaDAO;
import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.service.CadastroTurmaService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaTurmaBean implements Serializable {

	private List<Turma> turmas = new ArrayList<Turma>();

	private Turma turmaSelecionada;

	@Inject
	private TurmaDAO dao;

	@Inject
	private CadastroTurmaService service;

	@PostConstruct
	public void inicializar() {
		turmas = dao.buscarTodas();
	}

	public void excluir() {
		try {
			service.excluir(turmaSelecionada);
			this.turmas.remove(turmaSelecionada);
			FacesUtil.addSuccessMessage("Turma "
					+ turmaSelecionada.getDescricao()
					+ " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

}
