package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe responsável por consumir APIs externas (ex: Gutendex).
 * Retorna o corpo da resposta como String.
 */
public class ConsumoApi {

    /**
     * Executa uma requisição HTTP GET para o endpoint informado
     *
     * @param endereco URL completa da API
     * @return corpo da resposta como String
     */
    public String obterDados(String endereco) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Erro na requisição: HTTP " + response.statusCode());
            }

            return response.body();

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt(); // restaura o estado de interrupção
            throw new RuntimeException("Erro ao consumir API: " + e.getMessage(), e);
        }
    }
}