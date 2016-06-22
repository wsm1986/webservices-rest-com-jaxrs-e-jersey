package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {

	private HttpServer server;
	
    ClientConfig config = new ClientConfig();
    
	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
        config.register(new LoggingFilter());
    
	}

	@After
	public void mataServidor() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		Client client = ClientBuilder.newClient(config);
		// WebTarget target = client.target("http://www.mocky.io"); Alura
		// String conteudo =
		// target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		System.out.println(conteudo);
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		Assert.assertTrue(carrinho.getRua().contains("Rua Vergueiro 3185"));

	}

	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/projeto/1").request().get(String.class);
		System.out.println("Projetos " + conteudo);
		Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
		Assert.assertTrue(projeto.getNome().contains("Minha loja"));
	}

	@Test
	public void testaQueSuportaNovosCarrinhos() {
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:8080");
		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao Paulo");
		String xml = carrinho.toXML();

		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

		Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
		
	}
}