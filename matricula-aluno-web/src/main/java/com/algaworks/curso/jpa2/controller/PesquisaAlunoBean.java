package com.algaworks.curso.jpa2.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.curso.jpa2.dao.AlunoDAO;
import com.algaworks.curso.jpa2.modelo.Aluno;
import com.algaworks.curso.jpa2.modelo.AlunoVO;
import com.algaworks.curso.jpa2.service.CadastroAlunoService;
import com.algaworks.curso.jpa2.service.NegocioException;
import com.algaworks.curso.jpa2.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaAlunoBean implements Serializable {

	private List<AlunoVO> alunos = new ArrayList<AlunoVO>();

	private Aluno alunoSelecionado;

	@Inject
	private AlunoDAO dao;

	@Inject
	private CadastroAlunoService service;

	@PostConstruct
	public void inicializar() {
		alunos = service.criarAlunoVO(dao.buscarTodos());
	}

	public void excluir() {
		try {
			service.excluir(alunoSelecionado);
			if (getAlunoVO() != null) {
				this.alunos.remove(getAlunoVO());
			}
			FacesUtil.addSuccessMessage("Aluno " + alunoSelecionado.getNome()
					+ " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	private AlunoVO getAlunoVO() {
		for (AlunoVO vo : alunos) {
			if (vo.getAluno().equals(alunoSelecionado)) {
				return vo;
			}
		}
		return null;
	}

	public List<AlunoVO> getAlunos() {
		return alunos;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

}
