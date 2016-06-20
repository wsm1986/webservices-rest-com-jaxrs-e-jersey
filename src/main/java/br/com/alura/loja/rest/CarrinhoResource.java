package br.com.alura.loja.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path(value = "carrinhos")
public class CarrinhoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca() {
		CarrinhoDAO dao = new CarrinhoDAO();
		Carrinho carrinho = dao.busca(1L);
		return carrinho.toXML();
	}

}
