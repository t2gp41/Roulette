package com.company;

import java.util.LinkedList;
import java.util.List;

class database extends Thread{

    public static String currentPlayer;
    public static String result;

    public static int totalBet = 0;
    public static int totalPayout = 0;

    public static List<String> players = new LinkedList<>();
    public static List<String> bets = new LinkedList<>();
    public static List<String> winners = new LinkedList<>();
    public static List<String> logs = new LinkedList<>();

    public static int amountOfBets = 0;

    public static boolean healthy = true;

    public static int session = 1;
    public static int timer = 0;

    public static boolean finishPlaying;

    public void run() {
        while(true){
            if(healthy) {
                if(timer < 30){
                    timer ++;
                }
                else{
                    session++;
                    timer = 0;

                    logger.system();

                    finishPlaying = false;
                    bets = new LinkedList<>();
                    winners = new LinkedList<>();
                    amountOfBets = 0;
                    result = "";
                    totalBet = 0;
                    totalPayout = 0;
                }
            }

            try{
                Thread.sleep(1000);
            }catch (Exception e){
                return;
            }
        }
    }

    public static void pause(){
        healthy = false;
    }

    public static void reboot(){
        healthy = true;
    }

}