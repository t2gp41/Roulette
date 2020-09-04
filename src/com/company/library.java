package com.company;

import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class library {

    public static int countWord(String input){
        if(input == null || input.isEmpty()){
            return 0;
        }
        else{
            String[] word = (input.trim()).split(" ");
            return word.length;
        }
    }

    public static boolean isFileExists(String path){
        boolean result = false;
        File requestedFile = new File(path);
        if(requestedFile.exists()){
            result = true;
        }
        return result;
    }

    public static boolean createFile(String path, String data){
        boolean result = false;
        try{
            FileWriter localFile = new FileWriter(path);
            localFile.write(data);
            localFile.close();
            result = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static boolean createFolder(String folderName){
        boolean result = false;
        File requestedFolder = new File(folderName);
        if(requestedFolder.mkdir()){
            result = true;
        }
        return result;
    }

    public static Object readJsonFile(String path){
        Object fileData = null;
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader(path);
            fileData = parser.parse(reader);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return fileData;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getLocalTimeAndDate(){
        String pattern = "EEEEE dd MMMMM yyyy HH:mm:ss a";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern, new Locale("en", "ZA"));

        return simpleDateFormat.format(new Date());
    }

}
