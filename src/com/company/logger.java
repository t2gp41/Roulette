package com.company;

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

}
