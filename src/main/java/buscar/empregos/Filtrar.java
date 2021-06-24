package buscar.empregos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class Filtrar {
	private static List<String> cargos_indesejados = Lists.newArrayList("Motorista", "Açougueiro", "Enfermeiro", "Auditor", "Sênior", "Senior", "Médico", "Líder", "Lider", "Nutricionista", "FISCAL", "Farmaceutico", "Farmacêutico", "Nutrição", "Garantia", "Porteiro", "Encarregado", "Recrutamento e Seleção", "Laboratório", "Montador", "Carreteiro", "Marketing", "Jardineiro", "Mecânico", "Mecanico", "Arquiteto", "Recursos Humanos", "Torneiro", "Vigilante", "Gerente", "Soldador", "Costureira", "Costureiro", "Empilhadeira", "Instrutor", "Limpeza", "Faxineiro", "Faxineira", "Desenhista", "Serralheiro", "Ferramenteiro", "Arquitetura", "Segurança", "Laminação", "Cozinheiro", "Eletromecânica", "Deficiência", "Escavadeira", "Trator", "Pedreiro", "Ilustrador", "Torno", "Vidraceiro", "PCP", "Eletricista", "Pintor", "Cabeleleira", "Controle da Qualidade", "Inspetor", "Bucal", "PCD", "Fotógrafo", "Manobrista", "Retificador", "Fresador", "Padeiro", "Confeiteiro", "Guilhotina", "Químico", "Odontologia", "CNC", "Enfermagem");
	
	public static List<HashMap<String,String>> filtrar_vagas(List<HashMap<String,String>> vagas){
		
		List<HashMap<String,String>> vagas_filtradas = new ArrayList<HashMap<String, String>>() ;
		
		//Modificando o valor da chave "filtro" para "no" caso a vaga não senha desejavel
		outerLoop:
		for(HashMap<String, String> vaga : vagas) {
			for(String cargo : cargos_indesejados) {
				//Verificando se a string tem uma substring que remeta a um cargo indesejado
				if(vaga.get("titulo").toLowerCase().indexOf(cargo.toLowerCase()) != -1) {
					vaga.put("desejavel", "no");
					continue outerLoop;
				}
			}
		}
		
		//Colocando apenas as vagas desejaveis em uma nova lista
		for(HashMap<String,String> vaga : vagas) {
			
			//Vaga deve ser desejavel, não deve ser do infojobs premium e nem indeed externo
			if(vaga.get("desejavel") == "yes" & vaga.get("empresa_candidatura") != "Infojobs Premium" & vaga.get("empresa_candidatura") != "Indeed Externo" ) {
				vagas_filtradas.add(vaga);
			}
		}
		
		
		return vagas_filtradas;
	}
}
