package org.example;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config {



     public static String bgImgPath ="assets/"+  "background.jpg";
     public static String panelImgPath ="assets/"+  "panel.jpg";
     public static String goalImagePath = "assets/" +  "goal.png";
     public static String goalTextImagePath = "assets/" +  "goal_text.png";
     public static String winSoundPath = "assets/" +  "win.wav";
     public static String lossSoundPath = "assets/" +  "loss.wav";
     public static String invalidMoveSoundPath = "assets/" +  "invalid_move.wav";
     public static String startupSoundPath = "assets/" +  "startup.wav";
     public static String moveMadeSoundPath = "assets/" +  "move.wav";
     public static Map<Character,String> iconMap = new HashMap<>();

     static {
          iconMap.put('R',"assets/red/");
          iconMap.put('B',"assets/blue/");
          iconMap.put('-',"assets/blank/");
     }

     public static String concatFilenameToPath(String path,String fileName)
     {
          return path + fileName;
     }




}

