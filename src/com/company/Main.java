package com.company;

public class Main {

    public static void main(String[] args) {
        dictionary.initiate();
        component.welcomeBanner();
        String nickName = component.clearNickName(dictionary.values("nickNameError"), dictionary.values("askNickName"));

        database.players = component.createTable(nickName);
        component.printPlayersBalance();

        // multithread

        System.out.println("will be exit here");

    }
}
