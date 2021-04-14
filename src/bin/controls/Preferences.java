package bin.controls;

import java.io.*;

public abstract class Preferences {
    String Word;
    public static File LanguageFolder = new File("Language/");
    public enum Language{
        RU, ENG, UA
    }
    public static Language lang = Language.ENG;

    public Preferences(){}
    public abstract void Localization() throws IOException;

    public final String processFilesFromFolder(File folder, String parametr, Language language) throws FileNotFoundException, IOException
    {
        File[] folderEntries = folder.listFiles();
        for(File entry : folderEntries) {
            if (entry.isDirectory()) {
                processFilesFromFolder(entry, parametr, language);
                continue;
            }
            Word = Localization(entry, parametr, language);
            if (Word != null) return Word;
            else continue;
        }
        return Word;
    }
    public final String Localization(File entry, String parametr, Language language) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(entry), "UTF-8"));
        String finalStr;
        String []arr;
        if(entry.getName().equals(language.toString()+".txt")){
            while ((finalStr = reader.readLine()) != null){
                arr = finalStr.split(" = ");
                if(arr[0].equals("["+parametr+"]")){
                    arr = finalStr.split("'");
                    return arr[1];
                }
            }
        }
        return null;
    }
}
