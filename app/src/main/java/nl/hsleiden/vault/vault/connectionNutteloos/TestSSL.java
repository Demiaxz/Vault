package nl.hsleiden.vault.vault.connectionNutteloos;

/**
 * Created by Perseus on 16-03-16.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TestSSL {
    static String username = "s1087736";
    static String password = "Humanbu21422floop!!0";
    public static void main(String[] args) throws Exception {


        // Create a hash map
        HashMap hm = new HashMap();
        // Put elements to the map
        hm.put("startUrl", "Personalia.do");
        hm.put("inPortal", "");
        hm.put("callDirect", "");
        hm.put("requestToken", "b94536084639d9383e26ec70e3ca6f30161e0202");
        hm.put("gebruikersNaam",username);
        hm.put("wachtWoord",password);
        hm.put("event","login");

        System.out.println(performPostCall("https://studievolg.hsleiden.nl/student/Authenticate.do",hm));

    } // End of main

    public static String performPostCall(String requestURL,
                                   HashMap<String, String> postDataParams) throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
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
        //dildo
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));


        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL urlss = new URL("https://studievolg.hsleiden.nl");
        URLConnection conss = urlss.openConnection();
        final Reader readerss = new InputStreamReader(conss.getInputStream());
        final BufferedReader brss = new BufferedReader(readerss);
        String liness = "";
        while ((liness = brss.readLine()) != null) {
            //System.out.println(liness);
        }
        brss.close();

        URL urls = new URL("https://studievolg.hsleiden.nl/student/Personalia.do");
        URLConnection cons = urls.openConnection();
        final Reader readers = new InputStreamReader(cons.getInputStream());
        final BufferedReader brs = new BufferedReader(readers);
        String line = "";
        while ((line = brs.readLine()) != null) {
            //System.out.println(line);
        }
        brs.close();

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String lines;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lines=br.readLine()) != null) {
                    response+=lines;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        System.out.println(result.toString());
        return result.toString();
    }

} // End of the class //
