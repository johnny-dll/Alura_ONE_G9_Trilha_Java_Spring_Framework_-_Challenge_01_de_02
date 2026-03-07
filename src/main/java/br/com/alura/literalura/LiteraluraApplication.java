package br.com.alura.literalura;

import br.com.alura.literalura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final ConsumoApi consumoApi; // já injetado via construtor

	public LiteraluraApplication(ConsumoApi consumoApi) {
		this.consumoApi = consumoApi;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Array de títulos a serem buscados
		String[] titulosParaBuscar = {"dom casmurro", "dom quixote"};

		for (String titulo : titulosParaBuscar) {
			buscarESalvarLivro(titulo);
		}
	}

	/** Método auxiliar para buscar e salvar livros na base */
	private void buscarESalvarLivro(String titulo) {
		System.out.println("Buscando livro: " + titulo);
		int quantidadeSalvos = consumoApi.salvarLivrosGutendex(titulo);

		if (quantidadeSalvos == 0) {
			System.out.println("Nenhum livro encontrado para: " + titulo);
		} else {
			System.out.println(quantidadeSalvos + " livro(s) salvo(s) com sucesso!");
		}
	}
}