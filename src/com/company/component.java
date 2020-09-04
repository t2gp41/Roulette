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

    public static String betValidator(String cleanMyBet){
        String result = null;
        if(!cleanMyBet.isEmpty()){
            try {
                int ifInteger = Integer.parseInt(cleanMyBet);

                if (ifInteger % 1 == 0 && ifInteger > 0 && ifInteger < 37) {
                    result = cleanMyBet;
                }
            }catch (Exception e){
                if((cleanMyBet.toLowerCase()).equals("e")){
                    result = "EVEN";
                }
                else if((cleanMyBet.toLowerCase()).equals("o")){
                    result = "ODD";
                }
            }
        }
        return result;
    }

    public static int betAmountValidator(String amount){
        int result = 0;
        int cleanMyBet = Integer.parseInt(amount);
        String[] playerDetails = (database.players.get(0)).split(",");
        if (cleanMyBet > 0 && cleanMyBet <= Integer.parseInt(playerDetails[1])) {
            result = cleanMyBet;
        }
        return result;
    }

    public static int initiateBet(String whichNumberToBet, int howMuchToBet){
        database.totalBet += howMuchToBet;
        database.bets.add(database.currentPlayer + "," + whichNumberToBet + "," + howMuchToBet);
        String[] playerDetails = (database.players.get(0)).split(",");

        int currentBalance = Integer.parseInt(playerDetails[1]);
        int remainingBalance = (currentBalance - howMuchToBet);

        database.players.set(0, (playerDetails[0] + "," + remainingBalance));
        database.amountOfBets++;

        logger.player(database.currentPlayer, remainingBalance);
        logger.playerStatus(database.currentPlayer, howMuchToBet);

        return remainingBalance;
    }

    public static void playForOtherPlayers(){

        for(int b = 0; b < (database.players).size(); b++){

            String[] playerDetails = (database.players.get(b)).split(",");

            if(!playerDetails[0].equals(database.currentPlayer)){
                int goingToBet = library.getRandomNumber(1, 3);
                int currentBalance = Integer.parseInt(playerDetails[1]);

                for (int i = 0; i < goingToBet; i++) {
                    int ianBetting = library.getRandomNumber(1, currentBalance);
                    if(currentBalance > 0 && currentBalance >= ianBetting) {
                        database.totalBet += ianBetting;
                        database.bets.add(playerDetails[0] + "," + whichNumberToBet("forBet") + "," + ianBetting);
                        currentBalance -= ianBetting;
                        logger.playerStatus(playerDetails[0], ianBetting);
                    }
                }
                updateCurrentBalance(playerDetails[0], currentBalance);
            }
        }
    }

    public static String whichNumberToBet(String reffrence){
        List<String> allOptions = new ArrayList<>();
        for(int i = 1; i < 37; i++){
            allOptions.add(String.valueOf(i));
        }

        if(reffrence == "forBet") {
            allOptions.add("e");
            allOptions.add("o");
        }

        Random rand = new Random();
        return allOptions.get(rand.nextInt(allOptions.size()));
    }

    public static int getCurrentBalance(){
        String[] playerDetails = (database.players.get(0)).split(",");
        int currentBalance = Integer.parseInt(playerDetails[1]);
        return currentBalance;
    }

    public static void displayResult(){

        database.result = whichNumberToBet("forResult");

        System.out.println("\n");
        System.out.println(dictionary.color("red") + dictionary.style("bold") + "WINNING NUMBER : " + database.result + dictionary.style("reset"));
        System.out.println(dictionary.color("green") + dictionary.style("bold"));
        System.out.printf("%13s", "Player");
        System.out.printf("%13s", "Bet");
        System.out.printf("%13s", "Amount");
        System.out.printf("%13s", "Outcome");
        System.out.printf("%13s", "Winnings");
        System.out.println("" + dictionary.style("reset") + dictionary.color("purple"));
        System.out.printf("%13s", "--------");
        System.out.printf("%13s", "-----");
        System.out.printf("%13s", "--------");
        System.out.printf("%13s", "---------");
        System.out.printf("%13s", "----------");

        for(int a = 0; a < database.bets.size(); a++){
            String[] tmpDetails = (database.bets.get(a)).split(",");
            if(checkForWinner(tmpDetails[1], database.result)){
                System.out.println("" + dictionary.color("blue") + dictionary.style("bold"));
                System.out.printf("%13s", tmpDetails[0]);

                if((tmpDetails[1].toLowerCase()).equals("e")) {
                    System.out.printf("%13s", "Even");
                }
                else if((tmpDetails[1].toLowerCase()).equals("o")){
                    System.out.printf("%13s", "Odd");
                }
                else {
                    System.out.printf("%13s", tmpDetails[1]);
                }

                System.out.printf("%13s", "R" + NumberFormat.getInstance().format(Integer.parseInt(tmpDetails[2])));
                System.out.printf("%13s", "WIN");

                int totalWinAmount = calculateTheWinning(tmpDetails[1], tmpDetails[2]);
                int payout = totalWinAmount + Integer.parseInt(tmpDetails[2]);

                payout(tmpDetails[0], payout);
                database.totalPayout += payout;

                database.winners.add(tmpDetails[0] + "," + totalWinAmount);

                System.out.printf("%13s", "R" + NumberFormat.getInstance().format(totalWinAmount));
                System.out.printf("" + dictionary.style("reset"));

                logger.playerCredit(tmpDetails[0], payout);
            }
            else{
                System.out.println("" + dictionary.color("yellow"));
                System.out.printf("%13s", tmpDetails[0]);

                if((tmpDetails[1].toLowerCase()).equals("e")) {
                    System.out.printf("%13s", "Even");
                }
                else if((tmpDetails[1].toLowerCase()).equals("o")){
                    System.out.printf("%13s", "Odd");
                }
                else {
                    System.out.printf("%13s", tmpDetails[1]);
                }

                System.out.printf("%13s", "R" + NumberFormat.getInstance().format(Integer.parseInt(tmpDetails[2])));
                System.out.printf("%13s", "LOSE");
                System.out.printf("%13s", "R0.00");
                System.out.printf("" + dictionary.style("reset"));

                logger.playerDisCredit(tmpDetails[0], Integer.parseInt(tmpDetails[2]));
            }
        }

        System.out.println("");
        printPlayersBalance();
    }

    public static boolean checkForWinner(String myBet, String winner){
        boolean result = false;

        if(myBet.equals(winner)){
            result  = true;
        }

        int number = Integer.parseInt(winner);

        if((myBet.toLowerCase()).equals("e") && (number % 2) == 0){
            result  = true;
        }

        if((myBet.toLowerCase()).equals("o") && (number % 2) != 0){
            result  = true;
        }

        return result;
    }

    public static int calculateTheWinning(String bet, String amount){
        int result = 0;
        try {
            int ifInteger = Integer.parseInt(bet);

            if (ifInteger % 1 == 0 && ifInteger > 0 && ifInteger <= 36) {
                result = (Integer.parseInt(amount) * 35);
            }
        }catch (Exception e){
            if((bet.toLowerCase()).equals("e")){
                result = (Integer.parseInt(amount));
            }
            else if((bet.toLowerCase()).equals("o")){
                result = (Integer.parseInt(amount));
            }
        }
        return result;
    }

    public static void updateCurrentBalance(String player, int amount){
        for (int x = 0; x < database.players.size(); x++) {
            String[] tmpDetails = (database.players.get(x)).split(",");
            if(tmpDetails[0].equals(player)){
                database.players.set(x, (player + "," + amount));
                logger.player(player, amount);
                break;
            }
        }
    }

    public static void payout(String player, int amount){
        for (int b = 0; b < database.players.size(); b++) {
            String[] tmpDetails = (database.players.get(b)).split(",");
            if(tmpDetails[0].equals(player)){
                int newBalance = Integer.parseInt(tmpDetails[1]) + amount;
                database.players.set(b, (player + "," + newBalance));
                logger.player(player, newBalance);
                break;
            }
        }
    }

    public static int getPlayersCurrentBalance(String player){
        int balance = 0;
        for (int c = 0; c < database.players.size(); c++) {
            String[] tmpDetails = (database.players.get(c)).split(",");
            if(tmpDetails[0].equals(player)){
                balance = Integer.parseInt(tmpDetails[1]);
                break;
            }
        }
        return balance;
    }

}
