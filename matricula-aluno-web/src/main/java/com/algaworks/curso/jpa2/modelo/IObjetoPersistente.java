package com.algaworks.curso.jpa2.modelo;

public interface IObjetoPersistente<PK> {

	PK getCodigo();

	void setCodigo(PK codigo);

}
