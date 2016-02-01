package org.lema.notasapp.client;


import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class WebClient {

    private final String url;

    public WebClient(String url) {
        this.url = url;
    }

    public String get() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder builder = new StringBuilder();

            while(scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                builder.append(linha);
            }

            return builder.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String post(String entity) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(entity);

            //envia para o servidor
            connection.connect();

            String jsonDeResposta = new Scanner(connection.getInputStream()).next();
            return jsonDeResposta;

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
