package buscar.empregos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Indeed{
	//WebDriver
	private static WebDriver driver = BuscarEmpregos.driver;
	
	//URL PARCIAL que será usado para criar o link de acesso a vagas
	private static String URL_PARCIAL = "https://br.indeed.com/ver-emprego?";
	
	//Lista de vagas achadas na busca
	private static List <HashMap<String,String>> lista_vagas = new ArrayList<HashMap<String, String>>();
	
	//Usa os metodos disponíveis para abrir novas páginas e procurar vagas
	public static List<HashMap<String,String>> buscar_emprego(String link, Integer qtd_paginas) {
		//Loop para que seja possível carregar mais de uma página de busca de empregos
		lista_vagas.clear(); //Dando fim no lixo

		for(int pagina = 0; pagina < qtd_paginas ; pagina++ ) {
			ir_para(link, pagina);
			procurar_vagas();			
		}
		return lista_vagas;
	}
	
	//Indo para uma página
	//As paginas do indeed funcionam com o parametro start, quando você usar 50 são mostradas as vagas de número 51, 52, 53, etc
	//A primeira página começa com o start em 0, a segunda começa com o start em 50
	private static void ir_para(String link, Integer numero_pagina) {
		driver.get(link + "&start=" + numero_pagina * 50);
		esperar_carregamento();
	}
	
	//Espera explicita até a página carregar
	private static void esperar_carregamento() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("msgd-ConversationListDock-DockCard-header")));
	}
	
	//Busca todas as vagas que estão na página
	private static void procurar_vagas() {
		//Lista de WebElements que são vagas
		List<WebElement> vagas = new ArrayList<WebElement> ();
		vagas.clear();
		vagas.addAll(driver.findElements(By.className("jobsearch-SerpJobCard")));
		
		//Pegando Titulo, Link e empresa responsável pela candidatura de cada uma das vagas e adicionando a uma lista
		for(WebElement element : vagas) {
			HashMap<String, String> vaga = new HashMap<String, String>();
			
			//Esse JK é uma espécie de ID da vaga, vamos o utilizar para criar o link
			String JK = element.getAttribute("data-jk");
			
			vaga.put("titulo", element.findElement(By.xpath(".//h2[@class='title']//a")).getAttribute("title"));
		
			//O link é composto por URL PARCIAL + JK
			vaga.put("link", URL_PARCIAL + "&jk=" + JK);	
			vaga.put("empresa_candidatura", verifica_vaga_indeed(element));
			
			//Será utilizado para filtrar as vagas no futuro
			vaga.put("desejavel", "yes");
			
			//Adicionando a vaga a uma lista que contem todas as vagas achadas
			lista_vagas.add(vaga);
			
		}
	}
	
	//Verifica se é necessário entrar em outro site para se candidatar a vaga achada no Indeed
	private static String verifica_vaga_indeed(WebElement element) {
		if (element.findElements(By.className("jobCardShelfContainer")).toArray().length >= 1) {
			return "Indeed";
		} else {
			return "Indeed Externo";
		}
	}
		
}
  
