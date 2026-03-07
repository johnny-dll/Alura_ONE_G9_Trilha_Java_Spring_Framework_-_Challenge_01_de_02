package br.com.alura.literalura;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final ConsumoApi consumoApi;

	public LiteraluraApplication(ConsumoApi consumoApi) {
		this.consumoApi = consumoApi;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		exibirMenuTeste();
	}

	/** Menu de teste: busca livro na API e exibe objetos convertidos */
	private void exibirMenuTeste() {
		boolean sair = false;

		try (Scanner scanner = new Scanner(System.in)) {
			while (!sair) {
				System.out.println("\n=== MENU DE TESTE ===");
				System.out.println("1 - Buscar livro na API (mostrar objetos convertidos)");
				System.out.println("0 - Sair");
				System.out.print("Escolha uma opção: ");

				String opcao = scanner.nextLine();

				switch (opcao) {
					case "1":
						System.out.print("Digite o título do livro: ");
						String titulo = scanner.nextLine();
						try {
							BookResponse response = consumoApi.buscarLivroGutendex(titulo);
							List<BookResponse.Book> livros = response.getResults();
							if (livros.isEmpty()) {
								System.out.println("Nenhum livro encontrado para o título informado.");
							} else {
								System.out.println("\nResultados encontrados:");
								for (BookResponse.Book livroDto : livros) {
									exibirLivro(livroDto);
								}
							}
						} catch (Exception e) {
							System.out.println("Erro ao buscar livro: " + e.getMessage());
						}
						break;

					case "0":
						sair = true;
						System.out.println("Saindo do menu de teste...");
						break;

					default:
						System.out.println("Opção inválida. Tente novamente.");
				}
			}
		}
	}

	/** Exibe informações de um livro de forma legível */
	private void exibirLivro(BookResponse.Book livroDto) {
		System.out.println("------------------------------------------------");
		System.out.println("Título: " + livroDto.getTitle());
		System.out.println("Downloads: " + livroDto.getDownloads());

		List<String> idiomas = livroDto.getLanguage() != null ? livroDto.getLanguage() : List.of("desconhecido");
		System.out.println("Idiomas: " + idiomas);

		List<BookResponse.Book.Author> autores = livroDto.getAuthors() != null ? livroDto.getAuthors() : List.of();
		System.out.println("Autores:");
		for (BookResponse.Book.Author a : autores) {
			System.out.println("  Nome: " + a.getName());
			System.out.println("  Nascimento: " + a.getBirthYear());
			System.out.println("  Falecimento: " + a.getDeathYear());
		}
	}
}