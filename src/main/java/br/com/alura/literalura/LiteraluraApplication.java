package br.com.alura.literalura;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final AutorRepository autorRepository;
	private final LivroRepository livroRepository;

	public LiteraluraApplication(AutorRepository autorRepository, LivroRepository livroRepository) {
		this.autorRepository = autorRepository;
		this.livroRepository = livroRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Instancia o serviço de consumo de API
		ConsumoApi consumoApi = new ConsumoApi();
		String json = consumoApi.obterDados("https://gutendex.com/books/?search=dom%20casmurro");

		// Mapeia o JSON para objetos Java
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(json);

		// Pegamos o primeiro livro do resultado
		JsonNode livroNode = root.path("results").get(0);

		if (livroNode != null) {
			// Mapeia autor (apenas o primeiro da lista)
			JsonNode autorNode = livroNode.path("authors").get(0);
			Autor autor = new Autor();
			autor.setNome(autorNode.path("name").asText());
			autor.setAnoNascimento(autorNode.path("birth_year").asInt(0));
			autor.setAnoFalecimento(autorNode.path("death_year").asInt(0));

			// Salva o autor no banco
			autorRepository.save(autor);

			// Mapeia o livro
			Livro livro = new Livro();
			livro.setTitulo(livroNode.path("title").asText());
			livro.setIdioma(livroNode.path("languages").get(0).asText());
			livro.setDownloads(livroNode.path("download_count").asInt());
			livro.setAutor(autor);

			// Salva o livro no banco
			livroRepository.save(livro);

			System.out.println("Livro salvo com sucesso: " + livro);
		} else {
			System.out.println("Nenhum livro encontrado na API.");
		}
	}
}