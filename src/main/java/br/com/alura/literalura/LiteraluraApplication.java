package br.com.alura.literalura;

import br.com.alura.literalura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Instanciamos a nossa classe de consumo
		ConsumoApi consumoApi = new ConsumoApi();

		// Fazemos uma chamada de teste buscando por "Dom Casmurro"
		// Substituímos o '+' por '%20' que é o código oficial para espaço em URLs
		var json = consumoApi.obterDados("https://gutendex.com/books/?search=dom%20casmurro");

		// Imprimimos o resultado (o JSON bruto) no console
		System.out.println(json);
	}
}