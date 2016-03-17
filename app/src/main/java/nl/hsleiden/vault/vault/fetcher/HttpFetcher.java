package nl.hsleiden.vault.vault.fetcher;

/**
 * Created by Perseus on 16-03-16.
 */
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
import java.util.ArrayList;
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

    public static void getCijfers(Document parsedHTML) throws JSONException {

        String HTML = parsedHTML.toString();
        String[] eersteSplit = HTML.split("<td><span id=\"ResultatenPerStudent\">\n" +
                "                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" summary=\"\">");
        String[] tweedeSplit = eersteSplit[1].split("</table></td>\n" +
                "                      </tr>");
        System.out.println(tweedeSplit[0]);

        Document doc = Jsoup.parse(tweedeSplit[0]);

        String dildo = doc.text();

        String[] dildos = dildo.split("''");

        System.out.println(dildos);

        String wat = dildos[0];

        System.out.println(wat);

        doc = parsedHTML;

        ArrayList<String> downServers = new ArrayList<>();
        Elements table = doc.select("tbody").get(13).getElementsByClass("psbToonTekst"); //select the first table.

        for (Element element : table){
            System.out.println(element.text());
        }

//        Elements rows = table.select("tr");
//
//        for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
//            Element row = rows.get(i);
//            Elements cols = row.select("td");
//
//            if (cols.get(3).text().equals("Mutatiedatum")) {
//                if (cols.get(7).text().equals("down"))
//                    downServers.add(cols.get(5).text());
//
//                do {
//                    if(i < rows.size() - 1)
//                        i++;
//                    row = rows.get(i);
//                    cols = row.select("td");
//                    if (cols.get(7).text().equals("down") && cols.get(3).text().equals("")) {
//                        downServers.add(cols.get(5).text());
//                    }
//                    if(i == rows.size() - 1)
//                        break;
//                }
//                while (cols.get(3).text().equals(""));
//                i--; //if there is two Titan names consecutively.
//            }
//        }

        //maak een json object met als key de namen in de eerste tabel



        //deze bevinden zich in de tags <tr> <td>
        //tel de hoeveelheid rijen en add de null value tables ook mee in de list
        //ga nu door de resterende rijen en add de value's waar ze moeten komen


        //ArrayList<String> vakken = new ArrayList<>();

        ArrayList<String> cijfers = new ArrayList<>();
        JSONObject vakkenTotaal = new JSONObject();


        for (int i = 20 ; i < 35 ; i++){ //De eerste tabel waarin cijfers naar voren komen is <TR> 20. Er staan 15 rijen in deze tabel. Ik wil elk van deze rijen.
            JSONObject vakData = new JSONObject();
            Element cijferTabel = doc.select("tr").get(i); //Selecteer de eerste rij van de 15
            //System.out.println(cijferTabel.text()); // print wat er in deze rij staat
            for (int j = 0 ; j < 8 ; j++){ // In deze rij zijn er 7 kolommen die moeten worden weggeschreven.
                //System.out.println(cijferTabel.getElementsByIndexEquals(j).toString());

                if (j == 0){ //datum
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String[] datum = toetsdatum.getElementsByIndexEquals(0).text().split(" ");
                    //System.out.println(datum[0]);
                    try {
                        vakData.put("toetsDatum", datum[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 1){ //curcus
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String[] datum = toetsdatum.getElementsByIndexEquals(0).text().split(" ");
                    //System.out.println(datum[0]);
                    cijfers.add(datum[0]);
                    try {
                        vakkenTotaal.put(datum[0],new JSONObject());
                        vakData.put("curcusCode",datum[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 2){ //omschrijving
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    cijfers.add(datum);
                    try {
                        vakData.put("omschrijving",datum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 3){ //toetstype
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    cijfers.add(datum);
                    try {
                        vakData.put("toetsType",datum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 4){ //weging
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    cijfers.add(datum);
                    try {
                        vakData.put("weging",datum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 5){ //resultaat
                    Element toetsdatum = cijferTabel.select("td").get(j+1); //Selecteer de eerste rij van de 15
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    //System.out.println(datum);
                    cijfers.add(datum);
                    try {
                        vakData.put("cijfer",datum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (j == 6){ //concept
                    Element toetsdatum = cijferTabel.select("td").get(j); //Selecteer de eerste rij van de 15
                    Elements datum = toetsdatum.getAllElements();
                    try {
                        //System.out.println(datum.get(3).text());
                        cijfers.add("true");
                        try {
                            vakData.put("concept","true");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    catch (java.lang.IndexOutOfBoundsException e){
                        //System.out.println("(niet concept)");
                        cijfers.add("false");
                        try {
                            vakData.put("concept","false");
                        } catch (JSONException x) {
                            x.printStackTrace();
                        }
                    }
                }
                else if ( j == 7){ //mutatiedata
                    Element toetsdatum = cijferTabel.select("td").get(j+1); //Selecteer de eerste rij van de 1
                    String datum = toetsdatum.getElementsByIndexEquals(0).text();
                    System.out.println(datum+"\n");
                    try {
                        vakData.put("mutatieDatum",datum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }//end of j
            //add this list to json
            try {
                vakkenTotaal.put(vakData.getString("curcusCode"),vakData);
                System.out.println("Daar komt hij : ");
                System.out.println(vakData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }//end of i
        //clear the list and prep for new




//        for (Element element : cijferTabel) {
//            System.out.println(element.text());
//        }
        //{"name":"IARCH","ects": "3","grade": "1","period": "1"}


        //pass de json naar een klasse die er shit mee gaat doen
        //schoon heel de klas op om die gunstiger te bewerkstellen
        //natty

    }
}