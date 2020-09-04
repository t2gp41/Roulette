package com.company;

import org.json.simple.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class logger {
    public static void system(){

        String data = "{\n";

        data += "   \"session\" : \"" + database.session + "\",\n";
        data += "   \"date\":\"" + library.getLocalTimeAndDate() + "\",\n";
        data += "   \"draw\":\"" + database.result + "\",\n";

        data += "   \"players\":[\n";

        for (int i = 0; i < database.players.size(); i++){
            if(i != (database.players.size() -1)) {
                data += "       \"" + database.players.get(i) + "\",\n";
            }
            else{
                data += "       \"" + database.players.get(i) + "\"\n";
            }
        }

        data += "   ],\n";


        data += "   \"history\":[\n";

        for (int i = 0; i < database.bets.size(); i++){

            if(i != (database.bets.size() -1)) {
                data += "       \"" + database.bets.get(i) + "\",\n";
            }
            else{
                data += "       \"" + database.bets.get(i) + "\"\n";
            }
        }

        data += "   ],\n";

        data += "   \"winners\":[\n";

        for (int i = 0; i < database.winners.size(); i++){
            if(i != (database.winners.size() -1)) {
                data += "       \"" + database.winners.get(i) + "\",\n";
            }
            else{
                data += "       \"" + database.winners.get(i) + "\"\n";
            }
        }

        data += "   ],\n";

        data += "   \"totalBet\" : " + database.totalBet + ",\n";
        data += "   \"totalPayout\" : " + database.totalPayout + "\n";

        data += "}";

        int logIndex = database.logs.size();
        database.logs.add(data);

        library.createFile("com.example.roulette.logs/system/" + fileCount() + ".json", database.logs.get(logIndex));
    }

    public static String fileCount(){
        File directory=new File("com.example.roulette.logs/system/");
        String fileCount = String.valueOf(directory.list().length);

        String pattern = "EEEEE dd HH:mm:ss a";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("en", "ZA"));

        return "logs-" + fileCount + " " + simpleDateFormat.format(new Date());
    }

    public static void player(String nickName, int amount){

        Object fileData = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)fileData;

        String join = library.getLocalTimeAndDate();
        if(playerSummaryObj.containsKey("join")){
            join = playerSummaryObj.get("join").toString();
        }

        int totalBet = 0;
        if(playerSummaryObj.containsKey("totalBet")){
            totalBet = ((Long) playerSummaryObj.get("totalBet")).intValue();
        }

        int totalWon = 0;
        if(playerSummaryObj.containsKey("totalWon")){
            totalWon = ((Long) playerSummaryObj.get("totalWon")).intValue();
        }

        int totalLoss = 0;
        if(playerSummaryObj.containsKey("totalLoss")){
            totalLoss = ((Long) playerSummaryObj.get("totalLoss")).intValue();
        }

        String summary = "{\n" +
                "  \"join\" : \"" + join + "\",\n" +
                "  \"lastVisit\" : \"" + library.getLocalTimeAndDate() + "\",\n" +
                "  \"balance\" : "+ amount + ",\n" +
                "  \"totalBet\" : " + totalBet + ",\n" +
                "  \"totalWon\" : " + totalWon + ",\n" +
                "  \"totalLoss\" : " + totalLoss + "\n" +
                "}";

        library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", summary);
    }

    public static void playerStatus(String nickName, int amount){

        Object fileData = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)fileData;

        String join = library.getLocalTimeAndDate();
        if(playerSummaryObj.containsKey("join")){
            join = playerSummaryObj.get("join").toString();
        }

        int balance = 0;
        if(playerSummaryObj.containsKey("balance")){
            balance = ((Long) playerSummaryObj.get("balance")).intValue();
        }

        int totalBet = 0;
        if(playerSummaryObj.containsKey("totalBet")){
            totalBet = ((Long) playerSummaryObj.get("totalBet")).intValue();
        }

        totalBet += amount;

        int totalWon = 0;
        if(playerSummaryObj.containsKey("totalWon")){
            totalWon = ((Long) playerSummaryObj.get("totalWon")).intValue();
        }

        int totalLoss = 0;
        if(playerSummaryObj.containsKey("totalLoss")){
            totalLoss = ((Long) playerSummaryObj.get("totalLoss")).intValue();
        }

        String summary = "{\n" +
                "  \"join\" : \"" + join + "\",\n" +
                "  \"lastVisit\" : \"" + library.getLocalTimeAndDate() + "\",\n" +
                "  \"balance\" : "+ balance + ",\n" +
                "  \"totalBet\" : " + totalBet + ",\n" +
                "  \"totalWon\" : " + totalWon + ",\n" +
                "  \"totalLoss\" : " + totalLoss + "\n" +
                "}";

        library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", summary);
    }

    public static void playerCredit(String nickName, int amount){

        Object fileData = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)fileData;

        String join = library.getLocalTimeAndDate();
        if(playerSummaryObj.containsKey("join")){
            join = playerSummaryObj.get("join").toString();
        }

        int balance = 0;
        if(playerSummaryObj.containsKey("balance")){
            balance = ((Long) playerSummaryObj.get("balance")).intValue();
        }

        int totalBet = 0;
        if(playerSummaryObj.containsKey("totalBet")){
            totalBet = ((Long) playerSummaryObj.get("totalBet")).intValue();
        }

        int totalWon = 0;
        if(playerSummaryObj.containsKey("totalWon")){
            totalWon = ((Long) playerSummaryObj.get("totalWon")).intValue();
        }

        totalWon += amount;

        int totalLoss = 0;
        if(playerSummaryObj.containsKey("totalLoss")){
            totalLoss = ((Long) playerSummaryObj.get("totalLoss")).intValue();
        }

        String summary = "{\n" +
                "  \"join\" : \"" + join + "\",\n" +
                "  \"lastVisit\" : \"" + library.getLocalTimeAndDate() + "\",\n" +
                "  \"balance\" : "+ balance + ",\n" +
                "  \"totalBet\" : " + totalBet + ",\n" +
                "  \"totalWon\" : " + totalWon + ",\n" +
                "  \"totalLoss\" : " + totalLoss + "\n" +
                "}";

        library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", summary);
    }


    public static void playerDisCredit(String nickName, int amount){

        Object fileData = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)fileData;

        String join = library.getLocalTimeAndDate();
        if(playerSummaryObj.containsKey("join")){
            join = playerSummaryObj.get("join").toString();
        }

        int balance = 0;
        if(playerSummaryObj.containsKey("balance")){
            balance = ((Long) playerSummaryObj.get("balance")).intValue();
        }

        int totalBet = 0;
        if(playerSummaryObj.containsKey("totalBet")){
            totalBet = ((Long) playerSummaryObj.get("totalBet")).intValue();
        }

        int totalWon = 0;
        if(playerSummaryObj.containsKey("totalWon")){
            totalWon = ((Long) playerSummaryObj.get("totalWon")).intValue();
        }

        int totalLoss = 0;
        if(playerSummaryObj.containsKey("totalLoss")){
            totalLoss = ((Long) playerSummaryObj.get("totalLoss")).intValue();
        }

        totalLoss += amount;

        String summary = "{\n" +
                "  \"join\" : \"" + join + "\",\n" +
                "  \"lastVisit\" : \"" + library.getLocalTimeAndDate() + "\",\n" +
                "  \"balance\" : "+ balance + ",\n" +
                "  \"totalBet\" : " + totalBet + ",\n" +
                "  \"totalWon\" : " + totalWon + ",\n" +
                "  \"totalLoss\" : " + totalLoss + "\n" +
                "}";

        library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", summary);
    }


}
