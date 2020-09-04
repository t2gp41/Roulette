package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        dictionary.initiate();
        component.welcomeBanner();
        String nickName = component.clearNickName(dictionary.values("nickNameError"), dictionary.values("askNickName"));

        database.players = component.createTable(nickName);
        component.printPlayersBalance();

        database dbInstance = new database();
        dbInstance.start();

        boolean session = true;
        int loaderCounter = 0;

        while(session){
            if(database.timer < 19 && !database.finishPlaying){

                if(database.timer == 0){
                    if(component.getCurrentBalance() > 0){
                        loaderCounter = 0;
                        component.playForOtherPlayers();
                        System.out.println(dictionary.style("bold") + dictionary.color("green") + "\n  ROUND : " + database.session + dictionary.style("reset"));
                        System.out.println(dictionary.color("purple") + "---------------\n" + dictionary.style("reset"));

                        if(database.session % 3 == 0){
                            Scanner scanner = new Scanner(System.in);
                            System.out.print(dictionary.color("blue") + dictionary.style("bold") + "\nAre you enjoying the game? Yes Please(press y), Not Really(press n) : ");
                            String response = scanner.next();
                            if ((response.toLowerCase()).equals("n")) {
                                session = false;
                                database.pause();
                                break;
                            }
                            else {
                                System.out.println("\n" + dictionary.style("reset"));
                            }
                        }

                    }
                    else{
                        session = false;
                        database.pause();
                        break;
                    }
                }

                if(session) {
                    component.placeMyBet();
                }
            }
            else{
                if(database.timer == 30){
                    component.displayResult();
                }
                else {
                    if(loaderCounter == 0){
                        System.out.println("");
                    }

                    loaderCounter++;
                    System.out.print(dictionary.color("blue") + dictionary.style("bold") + "\rDraw in " + (30 - database.timer) + " seconds ---->>><<<----" + dictionary.style("reset"));
                }
                try{Thread.sleep(1000);}catch (Exception e){}
            }
        }


    }
}
