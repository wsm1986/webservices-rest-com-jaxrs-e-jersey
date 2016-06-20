package br.com.alura.loja.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path(value="projeto")
public class ProjetoResourse {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca() {
		ProjetoDAO dao = new ProjetoDAO();
		Projeto pr = dao.busca(1L);
		return pr.toXml();
	}
}
