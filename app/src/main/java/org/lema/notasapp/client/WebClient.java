package org.lema.notasapp.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.DefaultHttpClientConnection;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

}
