package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadWebPageEx { //TODO complete to parse it.
    public void readWeb() throws MalformedURLException, IOException {

        BufferedReader br = null;

        try {
            String key = "f2a85c175c990c9e64cc754c8666361a";
            String londonWeatherQuery = "https://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=";
            String theURL = londonWeatherQuery + key;

            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                appendStringBuilder(line, sb);
            }

            System.out.println(sb);
        } finally {

            if (br != null) {
                br.close();
            }
        }
    }

    private void appendStringBuilder(String line, StringBuilder sb) {
        sb.append(line);
        sb.append(System.lineSeparator());
    }
}

