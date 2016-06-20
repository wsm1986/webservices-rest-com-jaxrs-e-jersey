package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {

	private HttpServer server;

	@Before
	public void startaServidor() {
		this.server = Servidor.inicializaServidor();
	}

	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		Client client = ClientBuilder.newClient();
		// WebTarget target = client.target("http://www.mocky.io"); Alura
		// String conteudo =
		// target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertTrue(carrinho.getRua().equals("Rua Vergueiro 3185"));

	}

	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projeto").request().get(String.class);
		System.out.println("Projetos " + conteudo);
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		Assert.assertEquals("Minha loja", projeto.getNome());
	}
}