package nl.hsleiden.vault.vault;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Perseus on 04-04-16.
 */
public class Brain {
    protected stashGoods goods;
    protected ArrayList<String> nameList;
    protected JSONObject courseListEC = new JSONObject();
    protected JSONObject courseListNOEC = new JSONObject();

    public Brain(stashGoods k){
        goods = k;
        // Best coding ever.

        addCourseEC("IARCH","3");
        addCourseEC("IIBPM","3");
        addCourseEC("IHBO","3");
        addCourseEC("IOPR1","4");
        addCourseEC("INET","3");
        addCourseEC("IWDR","3");
        addCourseEC("IRDB","3");
        addCourseEC("IIBUI","3");
        addCourseEC("IPRODAM","2");
        addCourseEC("IPROMEDT","2");
        addCourseEC("IMUML","3");
        addCourseEC("IOPR2","4");
        addCourseEC("IFIT","3");
        addCourseEC("IPOFIT","2");
        addCourseEC("IPOSE","2");
        addCourseEC("IIPXXXX","10");
        addCourseEC("IPROV","3");
        addCourseEC("ICOMMP","3");
        addCourseEC("ISLP","1");
    }

    public stashGoods getGoods() {
        return goods;
    }

    protected int getPoints(){
        int points = 0;

        for (String name : getGoods().getNameListShort()){ //voor elke naam in de namenlijst (waar je dus een cijfer voor hebt)
            try {
                if (courseListEC.getString(name) != null) { // als de naam voorkomt in de courselistec
                    JSONObject happy = (JSONObject) goods.getGradeList().get(name);
                    if (Integer.valueOf(happy.getString("resultaat")) > 5.5){
                        points = points + Integer.valueOf(courseListEC.getString(name));
                        System.out.println(name + "V");
                    }
                    else {
                        courseListNOEC.put(name,"OV");
                        System.out.println(name + "OV");
                    }
                }
                else {
                    System.out.println("Vak "+name+" niet gevonden in de EC lijst.");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return points;
    }


    protected void addCourseEC(String key, String value){
        try{courseListEC.put(key, value);}
        catch (Exception e ){e.printStackTrace();}
    }
}

//        Kernvakken indicator 4 halen is ultimate:
//        2 behaald word groen mogelijk
//        Na 2 vakken 1 vak behaald is oranje
//        Na 3 vakken 1 vak gehaald word oranje/rood
//        Na 4 vakken 1 vak gehaald word rood met melding en exit formulier
//        Na 2 vakken 2 vakken behaald is oranje richting groen neigend en ligt aan punten
//        Na 3 vakken 2 vakken behaald is oranje en ligt aan punten
//        Na 4 vakken 2 vakken behaald ligt aan punten
//        Na 3 vakken 3 vakken behaald is steeds meer naar groen neigend
//        Na 4 vakken 3 vakken behaald is oranje
//        Na 4 vakken 4 vakken behaald ligt aan punten maar waarschijnlijk groen
//
//        Punten aantal:
//
//        Aantal punten bijhouden bij 40 oranje
//        50 groen
//        60 komt er te staan dat je een baas bent
//
//        Punten verhouding:
//        Bij houden of je meer dan 2/3 of 5/6 haalt.
//        Als je minder dan 2/3 haalt wordt hij rood
//        Als je meer dan 2/3 maar minder dan 5/6 haalt wordt deze oranje
//        Als je meer dan 5/6 haalt wordt hij groen
