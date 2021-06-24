package buscar.empregos;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class SalvarEmCSV {
	
	private static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/vagas.csv";
	
	//Pega a lista passada e salva ela como um arquivo CSV
	public static void salvar_csv(List<HashMap<String,String>> vagas) throws IOException {
		try (
            Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));

            CSVWriter csvWriter = new CSVWriter(writer);
        ) {
            String[] headerRecord = {"Titulo", "Link", "Empresa Responsável"};
            csvWriter.writeNext(headerRecord);
            
            //Salvando cada uma das vagas no arquivo csv
            for(HashMap<String,String> vaga : vagas) {
    			csvWriter.writeNext(new String[] { vaga.get("titulo"), vaga.get("link"), vaga.get("empresa_candidatura") });
    		}
        }
	}

}
