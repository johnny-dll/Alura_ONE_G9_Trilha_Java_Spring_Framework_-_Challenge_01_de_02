package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe responsável por realizar requisições HTTP para obter dados de APIs externas.
 */
public class ConsumoApi {

    public String obterDados(String endereco) {
        // Cria o cliente HTTP configurado para seguir redirecionamentos automaticamente
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        // Configura a requisição disfarçando o Java de navegador (User-Agent)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .header("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = null;

        try {
            // Tenta enviar a requisição e obter a resposta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            // Em caso de erro (falha de rede, etc), lança uma exceção com a mensagem
            throw new RuntimeException("Erro ao consultar a API: " + e.getMessage());
        }

        // Retorna o corpo da resposta (o JSON como String)
        return response.body();
    }
}