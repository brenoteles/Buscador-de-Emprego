package buscar.empregos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InfoJobs {
	private static WebDriver driver = BuscarEmpregos.driver;
	
	//Lista de vagas achadas na busca
	private static List <HashMap<String,String>> lista_vagas = new ArrayList<HashMap<String, String>>();
	
	//Usa os metodos disponíveis para abrir novas páginas e procurar vagas
	public static List<HashMap<String,String>> buscar_emprego(String link, Integer qtd_paginas) {
		//Loop para que seja possível carregar mais de uma página de busca de empregos
			for(int pagina = 1; pagina <= qtd_paginas ; pagina++ ) {
				ir_para(link, pagina);
				procurar_vagas();			
			}
			
		return lista_vagas;
	}
	
	//Faz o navegador carregar uma nova página
	private static void ir_para(String link, Integer numero_pagina) {
		if (numero_pagina == 1) {
			driver.get(link);
			esperar_carregamento();
		} else {
			numero_pagina++;
			driver.get(link + "&page=" + numero_pagina.toString());
			esperar_carregamento();
		}
	}
	
	//Busca vagas por toda a página e adiciona na lista
	private static void procurar_vagas() {
		lista_vagas.clear();
		//Lista de WebElements que são vagas
		List<WebElement> vagas = new ArrayList<WebElement> ();
		vagas.clear();
		vagas.addAll(driver.findElements(By.className("element-vaga")));
		
		//Pegando Titulo, Link e empresa responsável pela candidatura de cada uma das vagas e adicionando a uma lista
		for(WebElement element : vagas) {
			HashMap<String, String> vaga = new HashMap<String, String>();
			
			vaga.put("titulo", element.findElement(By.className("vagaTitle")).getAttribute("title"));
			vaga.put("link", element.findElement(By.className("js_rowVaga")).getAttribute("data-url"));
			
			//Se for premium então a responsável é a Infojobs Premium, caso não for é a Infojobs
			vaga.put("empresa_candidatura", verifica_premium(element));
			
			//Será utilizado para filtrar as vagas no futuro
			vaga.put("desejavel", "yes");
			
			//Adicionando a vaga a uma lista que contem todas as vagas achadas
			lista_vagas.add(vaga);
			
		}
	}
	
	//Espera explicita até a página carregar
	private static void esperar_carregamento() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("js_eplSlot")));
	}
	
	//Verifica se o elemento é uma vaga premium ou não
	private static String verifica_premium(WebElement element ) {	
		
		//Verificando se o elemento tem as classes que uma vaga premium possui
		if(element.getAttribute("class").equals("element-vaga unstyled   js_VacancyLimited    js_detailGrid premiumLimited")) {
			return "Infojobs Premium";
		} else {
			return "Infojobs"; 
		}
	}

}
