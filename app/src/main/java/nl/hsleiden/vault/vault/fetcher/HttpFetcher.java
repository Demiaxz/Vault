package nl.hsleiden.vault.vault.fetcher;

/**
 * Created by Perseus on 16-03-16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
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

import nl.hsleiden.vault.vault.MainActivity;

//class takes as input the username and password to return grade information.

public class HttpFetcher implements Runnable {

    static String username;
    static String password;
    static Context a;
    static CookieManager cookieManager = null;

    public HttpFetcher (String Nusername, String Npassword, Context a) throws Exception {
        username = Nusername;
        password = Npassword;
        run();
    }

    protected static String getUsername() {
        return username;
    }

    protected static String getPassword() {
        return password;
    }

    protected static Context getContext() {
        return a.getApplicationContext();
    }

    protected static CookieManager getCookieManager(){
        return cookieManager;
    }

    protected static void setCookieManager(CookieManager newCookieManager){
        cookieManager = newCookieManager;
    }

    public static TrustManager[] manageMe() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        return trustAllCerts;
    }

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

        if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Connection succes.");
        }
        if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
            System.out.println("Connection failed.");
        }

        boolean debug = false;

        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing first connection.");
        }

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }
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

        if (secondCon.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Connection succes.");
        }
        if (secondCon.getResponseCode() != HttpURLConnection.HTTP_OK){
            System.out.println("Connection failed.");
        }

        boolean debug = false;

        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = secondCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + secondCon.getHeaderField(i));
            }
            System.out.println("Closing second connection.");
        }

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }
        return manager;
    }

    public static CookieManager fetchThird(CookieManager manager) throws Exception {
        System.out.println("Opening third connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/AuthenticateUser.do?startUrl=Personalia.do&inPortal=&callDirect=&requestToken=1947286439a923285f8f56a35685d31e671f4710&gebruikersNaam="+getUsername()+"&wachtWoord="+getPassword()+"&event=login");
        HttpURLConnection secondCon = (HttpURLConnection) url.openConnection();
        secondCon.setReadTimeout(15000);
        secondCon.setConnectTimeout(15000);
        secondCon.setRequestMethod("POST");
        secondCon.setRequestProperty("startUrl", "Personalia.do");
        secondCon.setRequestProperty( "inPortal", "");
        secondCon.setRequestProperty( "callDirect", "");
        secondCon.setRequestProperty("requestToken", "fb9cd625c7b62c0f70b8d5ef1eac5b7a23c67623");
        secondCon.setRequestProperty("gebruikersNaam", getUsername());
        secondCon.setRequestProperty("wachtWoord:", getPassword());
        secondCon.setRequestProperty("event", "login");
        secondCon.setDoOutput(true);
        //Give me information, for now.
        int status = secondCon.getResponseCode();

        if (secondCon.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Connection succes.");
        }
        if (secondCon.getResponseCode() != HttpURLConnection.HTTP_OK){
            System.out.println("Connection failed.");
        }

        boolean debug = true;

        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = secondCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + secondCon.getHeaderField(i));
            }
            System.out.println(secondCon.getContent());
            System.out.println("Closing third connection.");
        }

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }
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

        if (fourthCon.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Connection succes.");
        }
        if (fourthCon.getResponseCode() != HttpURLConnection.HTTP_OK){
            System.out.println("Connection failed.");
        }

        boolean debug = false;

        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = fourthCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + fourthCon.getHeaderField(i));
            }
            System.out.println(fourthCon.getContent());

            System.out.println("Closing fourth connection.");
        }

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
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
        setCookieManager(manager);
        return doc;
    }

    public static Bitmap fetchPhoto() throws Exception {
        System.out.println("Opening photo connection.");
        URL url = new URL("https://studievolg.hsleiden.nl/student/AuthenticateUser.do");
        HttpURLConnection photoCon = (HttpURLConnection) url.openConnection();
        photoCon.setReadTimeout(15000);
        photoCon.setConnectTimeout(15000);
        photoCon.setRequestMethod("GET");
        photoCon.setDoInput(true);

        //Give me information, for now.
        int status = photoCon.getResponseCode();

        if (photoCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("Connection succes.");
        }
        if (photoCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Connection failed.");
        }

        boolean debug = false;

        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = photoCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + photoCon.getHeaderField(i));
            }
            System.out.println(photoCon.getContent());

            System.out.println("Closing photo connection.");
        }

        CookieStore cookieJar = getCookieManager().getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }

        String response = "";
        String lines;
        BufferedReader br = new BufferedReader(new InputStreamReader(photoCon.getInputStream()));
        while ((lines = br.readLine()) != null) {
            response += lines;
        }
        //System.out.println(response.toString());
        //System.out.println("HOERENKOTS");

        String html = response;
        Document doc = Jsoup.parse(html);
        Elements table = doc.select("table").get(13).getAllElements(); //select the first table.\
        //System.out.println(table.toString());
        //System.out.println("cancer");

        Element foto = table.get(8);
        //System.out.println(table.toString());
        //System.out.println("----------------------------");
        String id = table.text();
        String[] ids = id.split("Studentnummer ");
        String[] echtId = ids[1].split(" Voornamen");
        String echtechtId = echtId[0];

        Elements absHref = foto.getElementById(echtechtId).getAllElements();
        String downloadLink = absHref.attr("href");
        //out.println(downloadLink);

        URL urlski = new URL("https://studievolg.hsleiden.nl/student/"+downloadLink);
        HttpURLConnection conn = (HttpURLConnection) urlski.openConnection();

        InputStream in = conn.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        in.close();

        return bitmap;

    }

    public static void getCijfers(Document parsedHTML) throws JSONException {
        String testdate = "";
        String curcus = "";
        String omschrijving = "";
        String toetstype = "";
        String weging = "";
        String resultaat = "";
        String concept = "";
        String mutatiedatum = "";
        System.out.println("Retrieved the goods.");
        Document doc = parsedHTML;

        Elements table = doc.select("tbody").get(13).getElementsByClass("psbToonTekst"); //select the first table.
        for (int i = 20 ; i < 35 ; i++){ //De eerste tabel waarin cijfers naar voren komen is <TR> 20. Er staan 15 rijen in deze tabel. Ik wil elk van deze rijen.
            Element cijferTabel = doc.select("tr").get(i); //Selecteer de eerste rij van de 15
            //System.out.println(cijferTabel.text()); // print wat er in deze rij staat
            for (int j = 0 ; j < 8 ; j++){ // In deze rij zijn er 7 kolommen die moeten worden weggeschreven.
                //System.out.println(cijferTabel.getElementsByIndexEquals(j).toString());

                if (j == 0){ //datum
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String[] datum = toetsdatum.getElementsByIndexEquals(0).text().split(" ");
                    //System.out.println(datum[0]);
                    testdate = datum[0];

                }
                else if (j == 1){ //curcus
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String[] datum = toetsdatum.getElementsByIndexEquals(0).text().split(" ");
                    //System.out.print(datum[0]);
                    curcus = datum[0];
                }
                else if (j == 2){ //omschrijving
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    omschrijving = datum;
                }
                else if (j == 3){ //toetstype
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    toetstype = datum;
                }
                else if (j == 4){ //weging
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    weging = datum;
                }
                else if (j == 5){ //resultaat
                    Element toetsdatum = cijferTabel.select("td").get(j+1); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(" "+datum+"\n");
                    resultaat = datum;
                }
                else if (j == 6){ //concept
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    Elements datum = toetsdatum.getAllElements();
                    try {
                        //System.out.println(datum.gwaet(3).text());
                        concept = "concept";
                    }
                    catch (java.lang.IndexOutOfBoundsException e) {
                        //System.out.println("(niet concept)");
                        concept = "niet concept";
                    }
                }
                else if ( j == 7){ //mutatiedata
                    Element toetsdatum = cijferTabel.select("td").get(j+1); //Selecteer de eerste rij van de 1
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum+"\n");
                    mutatiedatum = datum;
                }

            }//end of j
            //System.out.println("Newest fetch : "+curcus+resultaat);

            try {
                //System.out.println(MainActivity.getContext());
                editRegion(MainActivity.getContext(), curcus, resultaat);
                //System.out.println("Current list. "+MainActivity.getContext().getSharedPreferences("vakData",0).getAll().toString());
                System.out.println(curcus+" Toegevoegd aan SP.");
            }

            catch (NullPointerException e) {
                System.out.println("Something went wrong.");
            }
//            System.out.println(a.getApplicationContext().databaseList().toString());
//            sharedPrefference.getVakData(a.getApplicationContext()).edit().putString(curcus,resultaat).commit();


        }//end of i
    }

    public static void editRegion(Context ctx, String region, String sregion) {
        SharedPreferences settings = ctx.getSharedPreferences("vakData",0);
        //System.out.println(settings.getAll().toString());
        SharedPreferences.Editor ed = settings.edit();
        ed.putString(region,sregion);
        ed.commit();
    }
    @Override
    public void run (){
        //Prepwork
        TrustManager[] trustAllCerts = manageMe();
        // Install the all-trusting trust manager
        final SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
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

        try {
            getCijfers(
                    fetchFourth(
                            fetchThird(
                                    fetchSecond(
                                            fetchFirst(manager)
                                    )
                            )
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


