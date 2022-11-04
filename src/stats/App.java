package stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class App {
    public App() {
    }

    public static void get(String address, String params) throws NoSuchAlgorithmException, KeyManagementException {
        StringBuilder output = new StringBuilder();
        SSLContext sslContext;

        try {
            URL url = new URL(address + params);
            System.out.println(url.toString());
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // con.setRequestProperty("X-RapidAPI-Key",
            // "0d5ab3a4bfmsh5174a54093fd0f6p12a4ffjsn47f368b3d6ea");

            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new java.security.SecureRandom());
            con.setSSLSocketFactory(sslContext.getSocketFactory());

            output.append("Response Code: ").append(con.getResponseCode()).append("\n");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), 1024 * 1024);
                String line = in.readLine();
                while (line != null) {
                    output.append(line);
                    line = in.readLine();
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(output);
    }

}
