package br.com.alura.literalura;

import br.com.alura.literalura.dto.BookResponse;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final ConsumoApi consumoApi;
	private final LivroRepository livroRepository;
	private final AutorRepository autorRepository;

	public LiteraluraApplication(
			ConsumoApi consumoApi,
			LivroRepository livroRepository,
			AutorRepository autorRepository) {

		this.consumoApi = consumoApi;
		this.livroRepository = livroRepository;
		this.autorRepository = autorRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		menuPrincipal();
	}

	/**
	 * MENU PRINCIPAL
	 */
	private void menuPrincipal() {

		boolean sair = false;

		try (Scanner scanner = new Scanner(System.in)) {

			while (!sair) {

				System.out.println("\n=== LITERALURA ===");
				System.out.println("1 - Buscar livro na API");
				System.out.println("2 - Listar livros salvos");
				System.out.println("0 - Sair");
				System.out.print("Escolha uma opção: ");

				String opcao = scanner.nextLine();

				switch (opcao) {

					case "1":
						buscarLivroApi(scanner);
						break;

					case "2":
						listarLivrosSalvos();
						break;

					case "0":
						sair = true;
						System.out.println("Encerrando aplicação...");
						break;

					default:
						System.out.println("Opção inválida.");
				}
			}
		}
	}

	/**
	 * BUSCA LIVRO NA API
	 */
	private void buscarLivroApi(Scanner scanner) {

		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine();

		try {

			BookResponse response = consumoApi.buscarLivroGutendex(titulo);
			List<BookResponse.Book> livros = response.getResults();

			if (livros.isEmpty()) {
				System.out.println("Nenhum livro encontrado.");
				return;
			}

			System.out.println("\nResultados encontrados:");

			for (BookResponse.Book livroDto : livros) {
				exibirLivroApi(livroDto);
			}

			submenuBusca(scanner, titulo);

		} catch (Exception e) {
			System.out.println("Erro ao buscar livro: " + e.getMessage());
		}
	}

	/**
	 * SUBMENU APÓS BUSCA
	 */
	private void submenuBusca(Scanner scanner, String titulo) {

		boolean voltar = false;

		while (!voltar) {

			System.out.println("\nO que deseja fazer?");
			System.out.println("1 - Salvar este(s) livro(s) no banco de dados");
			System.out.println("2 - Fazer nova busca");
			System.out.println("0 - Voltar ao menu principal");
			System.out.print("Escolha: ");

			String opcao = scanner.nextLine();

			switch (opcao) {

				case "1":

					int salvos = consumoApi.salvarLivrosGutendex(titulo);

					System.out.println(salvos + " livro(s) salvo(s) no banco.");
					voltar = true;
					break;

				case "2":
					buscarLivroApi(scanner);
					voltar = true;
					break;

				case "0":
					voltar = true;
					break;

				default:
					System.out.println("Opção inválida.");
			}
		}
	}

	/**
	 * LISTAR LIVROS SALVOS NO BANCO
	 */
	private void listarLivrosSalvos() {

		List<Livro> livros = livroRepository.findAll();

		if (livros.isEmpty()) {
			System.out.println("Nenhum livro salvo no banco.");
			return;
		}

		System.out.println("\n=== LIVROS SALVOS ===");

		for (Livro livro : livros) {
			System.out.println("----------------------------");
			System.out.println("Título: " + livro.getTitulo());
			System.out.println("Autor: " + livro.getAutor().getNome());
			System.out.println("Idioma: " + livro.getIdioma());
			System.out.println("Downloads: " + livro.getDownloads());
		}
	}

	/**
	 * EXIBIR LIVRO VINDO DA API
	 */
	private void exibirLivroApi(BookResponse.Book livroDto) {

		System.out.println("------------------------------------------------");
		System.out.println("Título: " + livroDto.getTitle());
		System.out.println("Downloads: " + livroDto.getDownloads());

		List<String> idiomas = livroDto.getLanguage() != null
				? livroDto.getLanguage()
				: List.of("desconhecido");

		System.out.println("Idiomas: " + idiomas);

		List<BookResponse.Book.Author> autores = livroDto.getAuthors() != null
				? livroDto.getAuthors()
				: List.of();

		System.out.println("Autores:");

		for (BookResponse.Book.Author a : autores) {

			System.out.println("  Nome: " + a.getName());
			System.out.println("  Nascimento: " + a.getBirthYear());
			System.out.println("  Falecimento: " + a.getDeathYear());
		}
	}
}2
