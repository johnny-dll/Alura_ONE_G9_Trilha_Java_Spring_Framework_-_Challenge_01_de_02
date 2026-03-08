package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Cliente HTTP para consumir APIs externas (ex: Gutendex).
 * Retorna o corpo da resposta como String.
 *
 * Configurações de timeout:
 * - 10s para conectar
 * - 20s para leitura da resposta
 */
public class ConsumoApi {

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(20);

    private final HttpClient client;

    public ConsumoApi() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                .build();
    }

    /**
     * Executa uma requisição HTTP GET para o endpoint informado.
     *
     * @param url URL completa da API
     * @return corpo da resposta como String
     * @throws RuntimeException caso ocorra erro de IO, interrupção ou HTTP != 200
     */
    public String obterDados(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(READ_TIMEOUT)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro HTTP " + response.statusCode() + " ao acessar: " + url);
            }

            return response.body();

        } catch (IOException e) {
            throw new RuntimeException("Erro de IO ao consumir API: " + url, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Requisição interrompida ao acessar: " + url, e);
        }
    }
}