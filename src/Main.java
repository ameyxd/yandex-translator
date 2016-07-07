
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with love by Amey Ambade on 6/7/16.
 */

public class Main {

    String formatType = "html";
    public static final String KEY = "trnsl.1.1.20160707T101752Z.05c360909711b57d.ab41db364e06cb60d0e11525a9623315ac3e415c";


    public static void main(String[] args) throws Exception {
        Main http = new Main();
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        System.out.println("Enter input language code (en, fr, ru, es, pl, hi)");
        String langInput = sc.next();
        System.out.println("Enter output language code (en, fr, ru, es, pl, hi)");
        String langOutput = sc.next();
        System.out.println("Enter text to translate in" + langInput);
        final String textToTranslate = sc.next();

        http.sendPost(textToTranslate, langInput, langOutput);
        sc.close();
    }

    private void sendPost(String textToTranslate, String langInput, String langOutput) throws Exception {

        String searchEncoded = URLEncoder.encode(textToTranslate, "UTF-8");
//      String url = "https://en.wikipedia.org/w/api.php?action=opensearch&search="+ searchEncoded + "&format=json";

//        String urlForPage = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="+searchEncoded;

        String lang = langInput + "-" + langOutput;

        String urlForPage = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=" + KEY +
                "&text=" + searchEncoded +
                "&lang=" + lang +
                "&[format=" + formatType +
                "&[options=0";
//                "&[callback=<name of the callback function>]";

        URL obj = new URL(urlForPage);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            System.out.println("Error in response");
            return;
        }
//        System.out.println("\nSending 'POST' request to URL : " + urlForPage);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

//        System.out.println(response.toString());

        JSONObject jsonObject = new JSONObject(response.toString());

        JSONArray resultArray = jsonObject.getJSONArray("text");
        String result = resultArray.getString(0);
        System.out.println(result);
    }
}
