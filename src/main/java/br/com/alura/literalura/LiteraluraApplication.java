package br.com.alura.literalura;

import br.com.alura.literalura.service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final LivroService livroService;

	public LiteraluraApplication(LivroService livroService) {
		this.livroService = livroService;
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

		Scanner scanner = new Scanner(System.in);
		boolean sair = false;

		while (!sair) {

			System.out.println("\n===== LITERALURA =====");
			System.out.println("1 - Buscar livro por título");
			System.out.println("2 - Listar livros registrados");
			System.out.println("3 - Listar livros por idioma");
			System.out.println("0 - Sair");
			System.out.print("Escolha uma opção: ");

			String opcao = scanner.nextLine();

			switch (opcao) {

				case "1":
					buscarLivro(scanner);
					break;

				case "2":
					livroService.listarLivros();
					break;

				case "3":
					listarPorIdioma(scanner);
					break;

				case "0":
					sair = true;
					System.out.println("Encerrando aplicação...");
					break;

				default:
					System.out.println("Opção inválida.");
			}
		}

		scanner.close();
	}

	/**
	 * BUSCAR LIVRO
	 */
	private void buscarLivro(Scanner scanner) {

		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine();

		livroService.buscarLivroPorTitulo(titulo);
	}

	/**
	 * LISTAR POR IDIOMA
	 */
	private void listarPorIdioma(Scanner scanner) {

		System.out.print("Digite o idioma (ex: en, pt, es, fr): ");
		String idioma = scanner.nextLine();

		livroService.listarLivrosPorIdioma(idioma);
	}
}3