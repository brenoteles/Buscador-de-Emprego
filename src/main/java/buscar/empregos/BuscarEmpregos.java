package buscar.empregos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

public class BuscarEmpregos {
	public static WebDriver driver;
	
	//Print com espaços ao redor
	public static void anunciar(String texto) {
		System.out.println("");
		System.out.println(texto);
		System.out.println("");
	}
	
	//Abrindo o navegador, definindo suas opções, incluindo um profile.
	public static void inicializar() {
		//Setting localização do geckodriver
		System.setProperty("webdriver.gecko.driver",""); //Preencha com a localização de seu geckodriver
		
		//Abrindo profile e setando profile do Firefox
		ProfilesIni profileIni = new ProfilesIni();
	    FirefoxProfile profile = profileIni.getProfile(""); //Coloque o profile de seu navegador, esse profile deve estar logado em uma conta no Indeed e Infojobs
	    FirefoxOptions options = new FirefoxOptions();
	    options.setProfile(profile);
	    
	    //Headless
	    options.setHeadless(true);
	    
	    //Abrindo navegador
	    driver = new FirefoxDriver(options);

	}
	
	//Serve para fechar o navegador
	public static void encerrar() {
		driver.quit();
		anunciar("Navegador fechado.");
	}
	
	public static void main(String[] args) throws IOException {
		List<HashMap<String,String>> vagas = new ArrayList<HashMap<String, String>>() ;
		try {
			inicializar();
			
			//Exemplos :
			
			//Infojobs Guarulhos
			vagas.addAll(InfoJobs.buscar_emprego("https://www.infojobs.com.br/empregos-em-guarulhos,-sp.aspx?Campo=griddate&Orden=desc", 2));
			
			//Infojobs Itaquaquecetuba
			vagas.addAll(InfoJobs.buscar_emprego("https://www.infojobs.com.br/empregos-em-itaquaquecetuba,-sp.aspx?Campo=griddate&Orden=desc", 2));
			
			//Infojobs Aruja
			vagas.addAll(InfoJobs.buscar_emprego("https://www.infojobs.com.br/empregos-em-aruja,-sp.aspx?Campo=griddate&Orden=desc", 2));
		
			//Infojobs Analista de Testes em todo o Brasil Home Office
			vagas.addAll(InfoJobs.buscar_emprego("https://www.infojobs.com.br/vagas-de-emprego-analista+de+testes.aspx?idw=2", 2));
			
			//Indeed Guarulhos
			vagas.addAll(Indeed.buscar_emprego("https://br.indeed.com/empregos?as_and=&as_phr=&as_any=&as_not=&as_ttl=&as_cmp=&jt=all&st=&salary=&radius=0&l=Guarulhos%2C+SP&fromage=any&limit=50&sort=date&psf=advsrch", 2));
			
			//Indeed Itaquaquecetuba
			vagas.addAll(Indeed.buscar_emprego("https://br.indeed.com/empregos?as_and=&as_phr=&as_any=&as_not=&as_ttl=&as_cmp=&jt=all&st=&salary=&radius=0&l=Itaquaquecetuba%2C+SP&fromage=any&limit=50&sort=date&psf=advsrch", 1));
			
			//Indeed Arujá
			vagas.addAll(Indeed.buscar_emprego("https://br.indeed.com/empregos?as_and=&as_phr=&as_any=&as_not=&as_ttl=&as_cmp=&jt=all&st=&salary=&radius=0&l=Arujá%2C+SP&fromage=any&limit=50&sort=date&psf=advsrch", 1));
			
			//Indeed Analista de Testes no Brasil interiro em Home Office 
			vagas.addAll(Indeed.buscar_emprego("https://br.indeed.com/empregos?q=Analista+de+Testes&l=Brasil&sort=date&limit=50&remotejob=032b3046-06a3-4876-8dfd-474eb5e7ed11", 2));
			
			//
		} finally {
			encerrar();
		}
		
		anunciar("Filtrando as vagas");
		//Fazendo o filtro
		vagas = Filtrar.filtrar_vagas(vagas);
		
		anunciar("Salvando as vagas");
		//Salvando em CSV
		SalvarEmCSV.salvar_csv(vagas);
	}
}
