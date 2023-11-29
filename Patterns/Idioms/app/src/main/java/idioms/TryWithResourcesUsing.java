package idioms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TryWithResourcesUsing {
    public static void use() {
        String url = "https://www.google.com/";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream is = connection.getInputStream()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                boolean isEmpty = false;

                while (!isEmpty) {
                    String line = br.readLine();

                    if (line != null) {
                        sb.append(line + "\n");
                    } else {
                        isEmpty = true;
                    }
                }

                System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
