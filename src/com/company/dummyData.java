package com.company;

import org.json.simple.JSONArray;

public class dummyData {
    static String dummyPlayerList = "{\n" +
            " \"players\" : [\n" +
            "   \"IronMan\",\n" +
            "   \"AntMan\",\n" +
            "   \"Hulk\",\n" +
            "   \"Thor\",\n" +
            "   \"Loki\",\n" +
            "   \"Falcon\",\n" +
            "   \"Nick\",\n" +
            "   \"Gamora\",\n" +
            "   \"BlackPanther\",\n" +
            "   \"Drax\"\n" +
            "   ]\n" +
            "}";

    public static JSONArray dummyPlayerArray(){
        JSONArray allPlayer = new JSONArray();
        allPlayer.add("IronMan");
        allPlayer.add("AntMan");
        allPlayer.add("Hulk");
        allPlayer.add("Thor");
        allPlayer.add("Loki");
        allPlayer.add("Falcon");
        allPlayer.add("Nick");
        allPlayer.add("Gamora");
        allPlayer.add("BlackPanther");
        allPlayer.add("Drax");
        return allPlayer;
    }

    public static String createPlayersSummary(){

        String date = library.getLocalTimeAndDate();

        String summary = "{\n" +
                "  \"join\" : \"" + date + "\",\n" +
                "  \"lastVisit\" : \"" + date + "\",\n" +
                "  \"balance\" : 100,\n" +
                "  \"totalBet\" : 0,\n" +
                "  \"totalWon\" : 0,\n" +
                "  \"totalLoss\" : 0\n" +
                "}";
        return summary;
    }

}
