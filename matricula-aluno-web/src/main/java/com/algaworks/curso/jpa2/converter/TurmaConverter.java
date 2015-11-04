package com.algaworks.curso.jpa2.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.algaworks.curso.jpa2.dao.TurmaDAO;
import com.algaworks.curso.jpa2.modelo.Turma;
import com.algaworks.curso.jpa2.util.cdi.CDIServiceLocator;

public abstract class TurmaConverter implements Converter {

	private TurmaDAO dao;

	public TurmaConverter() {
		this.dao = CDIServiceLocator.getBean(TurmaDAO.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Turma retorno = null;

		if (value != null) {
			retorno = this.dao.buscarPeloCodigo(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Long codigo = ((Turma) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());

			return retorno;
		}

		return "";
	}

}
