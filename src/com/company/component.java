package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.NumberFormat;
import java.util.*;

class component {

    public static void welcomeBanner(){
        System.out.println("\n" + dictionary.color("green") + dictionary.style("bold") +"+--------------------+");
        System.out.println("|      " + dictionary.values("appName").toUpperCase() + "      |");
        System.out.println("+--------------------+" + dictionary.style("reset"));
    }

    public static String clearNickName(String warning, String showInput){
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n" + dictionary.color("green") + showInput + " : " + dictionary.style("reset"));
        String nickName = scanner.nextLine();
        String result = "";
        boolean rotation = false;
        boolean error = true;
        while(error){
            if(nickName == null || nickName.isEmpty()){
                if(!rotation) {
                    System.out.println("\n" + dictionary.color("red") + warning + dictionary.style("reset"));
                }
                else{
                    rotation = false;
                }

                System.out.print("\n" + dictionary.color("green") + showInput + " : " + dictionary.style("reset"));
                nickName = scanner.nextLine();
            }
            else{
                if(library.countWord(nickName) > 1){
                    System.out.print("\n" + dictionary.color("purple") + dictionary.values("suggestNickName_01") + " " + nickNameFormat((nickName.trim()).replaceAll(" ", "_")) + " " + dictionary.values("suggestNickName_02") + dictionary.style("reset") + "\n");
                    System.out.print(dictionary.color("yellow") + dictionary.values("confirmNickName") + " : " + dictionary.style("reset"));
                    String confirmation = scanner.nextLine();
                    if((confirmation.toLowerCase()).equals("y")){
                        error = false;
                        result = nickNameFormat((nickName.trim()).replaceAll(" ", "_"));
                    }
                    else{
                        rotation = true;
                        nickName = "";
                    }
                }
                else{
                    error = false;
                    result = nickNameFormat((nickName.trim()).replaceAll(" ", "_"));
                }
            }
        }

        database.currentPlayer = result;
        return result;
    }

    private static String nickNameFormat(String nickName){
        String fNickName = nickName.substring(0, 1).toUpperCase() + nickName.substring(1).toLowerCase();
        if(fNickName.length() > 13){
            return (fNickName.substring(0, 10)).trim();
        }else {
            return fNickName;
        }
    }

    public static List<String> createTable(String nickName){
        List<String> tableDetails = new LinkedList<>();

        boolean playersList = false;
        boolean logs = false;

        if(!library.isFileExists("com.example.roulette.players.json")){
            if(library.createFile("com.example.roulette.players.json", dummyData.dummyPlayerList)){
                playersList = true;
            }
        }
        else{
            playersList = true;
        }

        if(!library.isFileExists("com.example.roulette.logs")){
            if(library.createFolder("com.example.roulette.logs")
                    &&
                    library.createFolder("com.example.roulette.logs/system")
                    &&
                    library.createFolder("com.example.roulette.logs/players")
            ){
                logs = true;
            }
        }
        else{
            logs = true;
        }

        List<String> randomPlayer = chooseRandomPlayer();
        randomPlayer.add(0, nickName);

        for(Object selectedPlayer : randomPlayer){
            if(!library.isFileExists("com.example.roulette.logs/players/" + selectedPlayer)) {
                createPlayerLogs((String) selectedPlayer);
                tableDetails.add(selectedPlayer + ",100");
            }
            else{
                int currentBalance = getPlayerBalance((String) selectedPlayer);
                if(currentBalance == 0){
                    rechargePlayer((String) selectedPlayer);
                    tableDetails.add(selectedPlayer + ",100");
                }
                else{
                    tableDetails.add(selectedPlayer + "," + currentBalance);
                }
            }
        }

        return  tableDetails;
    }

    private static List<String> chooseRandomPlayer(){
        List<String> players = new LinkedList<>();

        if(library.isFileExists("com.example.roulette.players.json")) {
            Object allPlayers = library.readJsonFile("com.example.roulette.players.json");

            if(allPlayers != null){
                JSONObject allPlayersObj = (JSONObject) allPlayers;
                JSONArray allPlayer = (JSONArray)allPlayersObj.get("players");
                Collections.shuffle(allPlayer);
                int terms = library.getRandomNumber(1,3);
                for(Object player : allPlayer){
                    players.add(nickNameFormat((String) player));
                    terms--;
                    if(terms == 0) {
                        break;
                    }
                }

            }
        }

        if(players.isEmpty()){
            JSONArray allPlayer = dummyData.dummyPlayerArray();
            Collections.shuffle(allPlayer);
            int terms = library.getRandomNumber(1,3);
            for(Object player : allPlayer){
                players.add(nickNameFormat((String) player));
                terms--;
                if(terms == 0) {
                    break;
                }
            }
        }

        return players;
    }

    private static boolean createPlayerLogs(String nickName){
        boolean result = false;
        if(library.createFolder("com.example.roulette.logs/players/" + nickName)
                &&
                library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", dummyData.createPlayersSummary())){
            result = true;
        }
        return result;
    }

    private static int getPlayerBalance(String nickName){
        int currentBalance = 0;
        Object playerSummary = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)playerSummary;
        if(playerSummaryObj.containsKey("balance")){
            currentBalance = ((Long) playerSummaryObj.get("balance")).intValue();
        }
        return currentBalance;
    }

    private static void rechargePlayer(String nickName){
        Object playerSummary = library.readJsonFile("com.example.roulette.logs/players/" + nickName + "/summary.json");
        JSONObject playerSummaryObj = (JSONObject)playerSummary;
        if(playerSummaryObj.containsKey("balance")){
            playerSummaryObj.put("balance", 100);
            library.createFile("com.example.roulette.logs/players/" + nickName + "/summary.json", playerSummaryObj.toString());
        }
    }

    public static void printPlayersBalance(){

        System.out.println("" + dictionary.color("green") + dictionary.style("bold"));
        System.out.printf("%13s", "Player");
        System.out.printf("%13s", "Balance") ;
        System.out.println("" + dictionary.style("reset") + dictionary.color("purple"));
        System.out.printf("%13s", "--------");
        System.out.printf("%13s", "---------");
        System.out.println("" + dictionary.color("green"));

        for (int i = 0; i < database.players.size(); i++) {
            String[] tmpDetails = (database.players.get(i)).split(",");
            System.out.printf("%13s", tmpDetails[0]);
            System.out.printf("%13s", "R" + NumberFormat.getInstance().format(Integer.parseInt(tmpDetails[1])));
            System.out.println("");
        }
        System.out.print("" + dictionary.style("reset"));
    }

    public static void placeMyBet() {
        Scanner scanner = new Scanner(System.in);
        String whichNumberToBet = null;
        int howMuchToBet = 0;

        if(database.amountOfBets > 0){
            System.out.println("");
        }

        System.out.print(dictionary.color("green") + dictionary.values("placeYourBet") + " : " + dictionary.style("reset"));
        String myBet = (scanner.next()).replaceAll("[^0-9EeOo]", "");

        while (component.betValidator(myBet) == null) {
            if(database.timer > 20){
                myBet = null;
                break;
            }
            else {
                myBet = (scanner.next()).replaceAll("[^0-9EeOo]", "");
            }
        }

        if (myBet != null) {
            whichNumberToBet = myBet;

            System.out.print(dictionary.color("green") + dictionary.values("placeYourMoney") + " : " + dictionary.style("reset"));
            String myMoney = (scanner.next()).replaceAll("[^0-9]", "");

            while (betAmountValidator(myMoney) == 0) {
                if(database.timer > 20){
                    myMoney = null;
                    break;
                }
                else {
                    myMoney = (scanner.next()).replaceAll("[^0-9]", "");
                }
            }

            if(myMoney != null){
                howMuchToBet = Integer.parseInt(myMoney);
            }
        }

        if (database.timer < 25 && whichNumberToBet != null && howMuchToBet > 0) {
            // place the final bet
            int remainingBalance = initiateBet(whichNumberToBet, howMuchToBet);
            System.out.println(dictionary.color("purple") + dictionary.style("bold") + "\nplaced a bet on " + whichNumberToBet + " for R" + howMuchToBet + ", " + dictionary.color("red") + "Remaining Balance - R" + remainingBalance + dictionary.style("reset"));

            if(remainingBalance > 0) {
                System.out.print(dictionary.color("yellow") + dictionary.style("bold") + "Would you like to make an other bet, yes(press y), no(press n) : " + dictionary.style("reset"));

                String response = scanner.next();
                if ((response.toLowerCase()).equals("n")) {
                    database.finishPlaying = true;
                } else {
                    return;
                }
            }
            else{
                database.finishPlaying = true;
            }
        }
    }

}
