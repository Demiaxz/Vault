package nl.hsleiden.vault.vault.fetcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Perseus on 24-03-16.
 */

public class PicFetch implements Runnable{

    static Bitmap picture = null;
    static private String password = null;
    static private String username = null;
    static CookieManager cookieManager = null;
    static boolean debug = false;
    static private Context c;

    public PicFetch(String usernameN, String passwordN, Context context){
        password = passwordN;
        username = usernameN;
        c = context;
    }

    @Override
    public void run(){
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
            cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        picture = authenticateMe(c);
    }

    public Bitmap runAuth(){
        try {
            run();
            return picture;
        }
        catch (IndexOutOfBoundsException e){
            return picture;
        }

    }

    //Method for SSL verification
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

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {

        return password;
    }

    //connect to the main website
    public static Bitmap authenticateMe(Context c) {
        CookieManager manager = cookieManager;

        URL url = null;

        try {
            url = new URL("https://studievolg.hsleiden.nl/student/StartPagina.do");
        } catch (MalformedURLException e) {
            System.out.println("First link not found.");
        }

        HttpURLConnection firstCon = null;

        try {
            firstCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Couldnt open first link.");
        }

        firstCon.setReadTimeout(15000);
        firstCon.setConnectTimeout(15000);

        try {
            firstCon.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.out.println("Couldnt GET the website.");
        }

        firstCon.setDoInput(true);

        int status = 0;
        try {
            status = firstCon.getResponseCode();
            if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Connection succes.");
            }
            if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
                System.out.println("Connection failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldnt get first respondcode.");
        }

        //---------------------------------------------------------------------------------------//
        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing first connection.");
        }
        //---------------------------------------------------------------------------------------//

        CookieStore cookieJar = manager.getCookieStore();
        List<HttpCookie> cookies =
                cookieJar.getCookies();

        //---------------------------------------------------------------------------------------//
        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }

        //---------------------------------------------------------------------------------------//
        //------------------------------------------NEW CON--------------------------------------//
        //---------------------------------------------------------------------------------------//

        try {
            url = new URL("https://studievolg.hsleiden.nl/student/Personalia.do");
        } catch (MalformedURLException e) {
            System.out.println("Second link not found.");
        }

        try {
            firstCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Couldnt open second link.");
        }

        firstCon.setInstanceFollowRedirects(true);

        status = 0;
        try {
            status = firstCon.getResponseCode();
            if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Connection succes.");
            }
            if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
                System.out.println("Connection failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldnt get second respondcode.");
        }

        //---------------------------------------------------------------------------------------//
        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing second connection.");
        }
        //---------------------------------------------------------------------------------------//

        cookieJar = manager.getCookieStore();
        cookies =  cookieJar.getCookies();

        //---------------------------------------------------------------------------------------//
        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }

        //---------------------------------------------------------------------------------------//
        //------------------------------------------NEW CON--------------------------------------//
        //---------------------------------------------------------------------------------------//

        try {
            url = new URL("https://studievolg.hsleiden.nl/student/AuthenticateUser.do?startUrl=Personalia.do&inPortal=&callDirect=&requestToken=1947286439a923285f8f56a35685d31e671f4710&gebruikersNaam="+getUsername()+"&wachtWoord="+getPassword()+"&event=login");
        } catch (MalformedURLException e) {
            System.out.println("Third link not found.");
        }

        try {
            firstCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Couldnt open third link.");
        }

        try {
            firstCon.setRequestMethod("POST");
        } catch (ProtocolException e) {
            System.out.println("Couldnt POST to link.");
        }

        firstCon.setDoOutput(true);

        try {
            status = firstCon.getResponseCode();
            if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Connection succes.");
            }
            if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
                System.out.println("Connection failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldnt get third respondcode.");
        }

        //---------------------------------------------------------------------------------------//
        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing third connection.");
        }
        //---------------------------------------------------------------------------------------//

        cookieJar = manager.getCookieStore();
        cookies =  cookieJar.getCookies();

        //---------------------------------------------------------------------------------------//
        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }

        //---------------------------------------------------------------------------------------//
        //------------------------------------------NEW CON--------------------------------------//
        //---------------------------------------------------------------------------------------//

        try {
            url = new URL("https://studievolg.hsleiden.nl/student/AuthenticateUser.do");
        } catch (MalformedURLException e) {
            System.out.println("fourth link not found.");
        }

        try {
            firstCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Couldnt open fourth link.");
        }

        try {
            firstCon.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.out.println("Couldnt GET the website.");
        }

        firstCon.setDoInput(true);
        firstCon.setInstanceFollowRedirects(true);

        status = 0;
        try {
            status = firstCon.getResponseCode();
            if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Connection succes.");
            }
            if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
                System.out.println("Connection failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldnt get fourth respondcode.");
        }

        //---------------------------------------------------------------------------------------//
        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing fourth connection.");
        }
        //---------------------------------------------------------------------------------------//

        cookieJar = manager.getCookieStore();
        cookies =  cookieJar.getCookies();

        //---------------------------------------------------------------------------------------//
        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }
        //---------------------------------------------------------------------------------------//

        try {
            url = new URL("https://studievolg.hsleiden.nl/student/ToonPersonalia.do");
        } catch (MalformedURLException e) {
            System.out.println("fourth link not found.");
        }

        try {
            firstCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            System.out.println("Couldnt open fourth link.");
        }

        try {
            firstCon.setRequestMethod("GET");
        } catch (ProtocolException e) {
            System.out.println("Couldnt GET the website.");
        }

        firstCon.setDoInput(true);
        firstCon.setInstanceFollowRedirects(true);

        status = 0;
        try {
            status = firstCon.getResponseCode();
            if (firstCon.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("Connection succes.");
            }
            if (firstCon.getResponseCode() != HttpURLConnection.HTTP_OK){
                System.out.println("Connection failed.");
            }
        } catch (IOException e) {
            System.out.println("Couldnt get fourth respondcode.");
        }

        //---------------------------------------------------------------------------------------//
        if (debug) {
            System.out.println("Status = " + status);
            String key;
            System.out.println("Headers-------start-----");
            for (int i = 1; (key = firstCon.getHeaderFieldKey(i)) != null; i++) {
                System.out.println(key + ":" + firstCon.getHeaderField(i));
            }
            System.out.println("Closing fourth connection.");
        }
        //---------------------------------------------------------------------------------------//

        cookieJar = manager.getCookieStore();
        cookies =  cookieJar.getCookies();

        //---------------------------------------------------------------------------------------//
        if (debug) {
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        }
        //---------------------------------------------------------------------------------------//
        String response = "";
        String lines;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(firstCon.getInputStream()));
        } catch (IOException e) {
            System.out.println("Couldnt get inputstream");
        }
        try {
            while ((lines = br.readLine()) != null) {
                response += lines;
            }
        } catch (IOException e) {
            System.out.println("Something happened to the inputstream.");
        }

        String html = response;
        //System.out.println(response);
        Document doc = Jsoup.parse(html);
        //System.out.println(doc.text());
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
        //System.out.println(echtechtId);

        String meuk = table.text();
        String[] meuk1 = meuk.split("Naam ");
        String[] meuk2 = meuk1[1].split(" Studentnummer");
        String voornaam = meuk2[0];
        Elements absHref = foto.getElementById(echtechtId).getAllElements();
        String downloadLink = absHref.attr("href");
        System.out.println(downloadLink);

        URL urlski = null;
        try {
            urlski = new URL("https://studievolg.hsleiden.nl/student/"+downloadLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) urlski.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        try {
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

    }
}
