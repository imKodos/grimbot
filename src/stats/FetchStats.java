package stats;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FetchStats {
    public FetchStats() {
    }

    public static JSONObject get(String address)
            throws NoSuchAlgorithmException, KeyManagementException, ParseException,
            org.json.simple.parser.ParseException {
        return get(address, "", null);
    }

    public static JSONObject get(String address, String params)
            throws NoSuchAlgorithmException, KeyManagementException, ParseException,
            org.json.simple.parser.ParseException {
        return get(address, params, null);
    }

    public static JSONObject get(String address, String params, HashMap<String, String> properties)
            throws NoSuchAlgorithmException, KeyManagementException, ParseException,
            org.json.simple.parser.ParseException {
        StringBuilder output = new StringBuilder();
        SSLContext sslContext;
        JSONParser parse = new JSONParser();
        JSONObject dataObj = new JSONObject();

        try {
            URL url = new URL(address + params);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // if additional properties are needed, add them
            if (properties != null && !properties.isEmpty()) {
                for (Map.Entry<String, String> propEntry : properties.entrySet()) {
                    con.setRequestProperty(propEntry.getKey(),
                            propEntry.getValue());
                }
            }

            // set the security
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new java.security.SecureRandom());
            con.setSSLSocketFactory(sslContext.getSocketFactory());

            // build the output
            if (con.getResponseCode() == 200) {
                Scanner scanner = new Scanner(con.getInputStream());
                while (scanner.hasNext()) {
                    output.append(scanner.nextLine());
                }
                scanner.close();

                // Using the JSON simple library parse the string into a json object
                dataObj = (JSONObject) parse.parse(output.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataObj;
    }

}
