package nl.hsleiden.vault.vault;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Perseus on 24-03-16.
 */
public class stashGoods {
    JSONObject gradeDetails = new JSONObject();
    JSONObject gradeList = new JSONObject();
    ArrayList<String> nameList = new ArrayList<>();

    public stashGoods(Document document) {


        String testdate = "";
        String curcus = "";
        String omschrijving = "";
        String toetstype = "";
        String weging = "";
        String resultaat = "";
        String concept = "";
        String mutatiedatum = "";
        System.out.println("Retrieved the goods for stashing.");
        Document doc = document;

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
                else if (j == 3) { //toetstype
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
                        concept = "op concept";
                    }
                    catch (java.lang.IndexOutOfBoundsException e) {
                        //System.out.println("(niet concept)");
                        concept = "niet langer op concept";
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
                try {
                    JSONObject x = new JSONObject();
                    x.put("testdate", testdate);
                    x.put("curcus", curcus+" "+toetstype);
                    x.put("omschrijving", omschrijving);
                    x.put("toetstype", toetstype);
                    x.put("weging", weging);
                    x.put("resultaat", resultaat);
                    x.put("concept", concept);
                    x.put("mutatiedatum", mutatiedatum);

                    String verhaal = curcus + " is op "+ testdate+" afgenomen als een "+toetstype+", en weegt voor:"+weging+". Jouw cijfer, "+resultaat+", staat "+concept+
                            ". De datum waarop dit in osiris is verwerkt is: "+mutatiedatum;

                    x.put("verhaal",verhaal);

                    getGradeList().put(curcus+" "+toetstype, x);
                    nameList.add(curcus+" "+toetstype);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


                System.out.println(curcus+" Toegevoegd aan SP.");
            }

            catch (NullPointerException e) {
                System.out.println("Something went wrong.");
            }
//            System.out.println(a.getApplicationContext().databaseList().toString());
//            sharedPrefference.getVakData(a.getApplicationContext()).edit().putString(curcus,resultaat).commit();


        }//end of i
    }

    public JSONObject getGradeDetails() {
        return gradeDetails;
    }

    public JSONObject getGradeList() {
        return gradeList;
    }

    public ArrayList<String> getNameList() {
        return nameList;
    }
}

