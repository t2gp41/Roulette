package com.company;

import java.util.HashMap;

class dictionary {

    public static boolean useCustomStyle = true;

    public static void initiate(){
        String osName = System.getProperty("os.name");

        if(osName.contains("Windows")){
            String tempVersion = (osName.trim()).replaceAll("[^0-9.]", "");

            float windowVersion = 0.0F;
            if(!tempVersion.isEmpty()){
                windowVersion = Float.parseFloat(tempVersion);
            }

            if(windowVersion < 10) {
                useCustomStyle = false;
            }
        }
    }

    public static String values(String reference){
        HashMap<String, String> allValues = new HashMap<>();

        // Customisation for app
        allValues.put("appName", "Roulette");

        allValues.put("askNickName", "Please enter your username");
        allValues.put("nickNameError", "SORRY! You are entering an empty username.");

        return allValues.getOrDefault(reference, "");
    }

    public static String color(String colorName){
        HashMap <String, String> allColorName = new HashMap<>();

        allColorName.put("blue", "\u001B[34m");
        allColorName.put("red", "\u001B[31m");
        allColorName.put("yellow", "\u001B[33m");
        allColorName.put("purple", "\u001B[35m");
        allColorName.put("green", "\u001B[32m");

        if(useCustomStyle && allColorName.containsKey(colorName)){
            return allColorName.get(colorName);
        }
        else{
            //System.out.println("can not find in di.color " + colorName);
            return "";
        }
    }

    public static String style(String properties){
        HashMap <String, String> allProperties = new HashMap<>();

        allProperties.put("bold", "\u001B[1m");
        allProperties.put("reset", "\u001B[0m");

        if(useCustomStyle && allProperties.containsKey(properties)){
            return allProperties.get(properties);
        }
        else{
            //System.out.println("can not find in di.style " + properties);
            return "";
        }
    }
}
