package nl.hsleiden.vault.vault.fetcher;

/**
 * Created by Perseus on 16-03-16.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpFetcher {

    static String aap = null;

    public static void main(String[] args) throws Exception {


        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Declare non CA's approval
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        // Cookie handling
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);

        getCijfers(
                fetchFourth(
                        fetchThird(
                                fetchSecond(
                                        fetchFirst(manager)
                                )
                        )
                )
        );


    }//END OF MAIN

    public static CookieManager fetchFirst(CookieManager manager) throws Exception {
        System.out.println("Opening first connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/StartPagina.do");
        HttpURLConnection firstCon = (HttpURLConnection) url.openConnection();
        firstCon.setReadTimeout(15000);
        firstCon.setConnectTimeout(15000);
        firstCon.setRequestMethod("GET");
        firstCon.setDoInput(true);

        //Give me information, for now.
        int status = firstCon.getResponseCode();
        System.out.println("Status = " + status);
        String key;
        System.out.println("Headers-------start-----");
        for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
            System.out.println(key + ":" + firstCon.getHeaderField(i));
        }
        System.out.println("Closing first connection.");

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();
        for (HttpCookie cookie : cookies) {
            System.out.println("CookieHandler retrieved cookie: " + cookie);
        }
        System.out.println("");
        return manager;
    }

    public static CookieManager fetchSecond(CookieManager manager) throws Exception {
        System.out.println("Opening second connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/Personalia.do");
        HttpURLConnection secondCon = (HttpURLConnection) url.openConnection();
        secondCon.setReadTimeout(15000);
        secondCon.setConnectTimeout(15000);
        secondCon.setRequestMethod("GET");
        secondCon.setDoInput(true);
        secondCon.setInstanceFollowRedirects(true);

        //Give me information, for now.
        int status = secondCon.getResponseCode();
        System.out.println("Status = " + status);
        String key;
        System.out.println("Headers-------start-----");
        for (int i = 1; (key = secondCon.getHeaderFieldKey(i)) != null; i++) {
            System.out.println(key + ":" + secondCon.getHeaderField(i));
        }
        System.out.println("Closing second connection.");

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();
        for (HttpCookie cookie : cookies) {
            System.out.println("CookieHandler retrieved cookie: " + cookie);
        }
        System.out.println("");
        return manager;
    }

    public static CookieManager fetchThird(CookieManager manager) throws Exception {
        System.out.println("Opening third connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/AuthenticateUser.do?startUrl=Personalia.do&inPortal=&callDirect=&requestToken=1947286439a923285f8f56a35685d31e671f4710&gebruikersNaam=s1087736&wachtWoord=Humanbu21422floop%21%210&event=login");
        HttpURLConnection secondCon = (HttpURLConnection) url.openConnection();
        secondCon.setReadTimeout(15000);
        secondCon.setConnectTimeout(15000);
        secondCon.setRequestMethod("POST");
        secondCon.setDoOutput(true);

        //Give me information, for now.
        int status = secondCon.getResponseCode();
        System.out.println("Status = " + status);
        String key;
        System.out.println("Headers-------start-----");
        for (int i = 1; (key = secondCon.getHeaderFieldKey(i)) != null; i++) {
            System.out.println(key + ":" + secondCon.getHeaderField(i));
        }
        System.out.println("Closing second connection.");

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();
        for (HttpCookie cookie : cookies) {
            System.out.println("CookieHandler retrieved cookie: " + cookie);
        }

        System.out.println(manager.getCookieStore().getCookies());

        return manager;
    }

    public static Document fetchFourth(CookieManager manager) throws Exception {
        System.out.println("Opening fourth connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/ToonResultaten.do");
        HttpURLConnection fourthCon = (HttpURLConnection) url.openConnection();
        fourthCon.setReadTimeout(15000);
        fourthCon.setConnectTimeout(15000);
        fourthCon.setRequestMethod("GET");
        fourthCon.setDoInput(true);

        //Give me information, for now.
        int status = fourthCon.getResponseCode();
        System.out.println("Status = " + status);
        String key;
        System.out.println("Headers-------start-----");
        for (int i = 1; (key = fourthCon.getHeaderFieldKey(i)) != null; i++) {
            System.out.println(key + ":" + fourthCon.getHeaderField(i));
        }
        System.out.println("Closing fourth connection.");

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();
        for (HttpCookie cookie : cookies) {
            System.out.println("CookieHandler retrieved cookie: " + cookie);
        }

        String response = "";
        String lines;
        BufferedReader br = new BufferedReader(new InputStreamReader(fourthCon.getInputStream()));
        while ((lines = br.readLine()) != null) {
            response += lines;
        }
        System.out.println(response);
        System.out.println(manager.getCookieStore().getCookies());


        System.out.println("");

        String html = response;
        Document doc = Jsoup.parse(html);
        return doc;
    }

    public static void getCijfers(Document parsedHTML) {

        String HTML = parsedHTML.toString();
        String[] eersteSplit = HTML.split("<table class=\"OraTableContent\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\" width=\"100%\" summary=\"Deze tabel toont een overzicht van je behaalde resultaten.\">\n" +
                "                         <tbody>");
        String[] tweedeSplit = eersteSplit[1].split("</tbody>\n" +
                "                        </table></td>\n" +
                "                      </tr>");
        System.out.println(tweedeSplit[0]);

        Document doc = Jsoup.parse(tweedeSplit[0]);

        //maak een json object met als key de namen in de eerste tabel
        //deze bevinden zich in de tags <tr> <td>
        //tel de hoeveelheid rijen en add de null value tables ook mee in de list
        //ga nu door de resterende rijen en add de value's waar ze moeten komen
        //pass de json naar een klasse die er shit mee gaat doen
        //schoon heel de klas op om die gunstiger te bewerkstellen
        //natty

    }
}