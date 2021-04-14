package bin.data;

public class Config {
    public String dbPass;
    public String dbHost = "35.235.33.212";
    public String dbPort = "3306";
    public String dbUser = "boss";

    static Config _instance = null;
    private Config(){}
    public static synchronized Config getInstance(){
        if (_instance == null) _instance = new Config();
         return _instance;
    }
}
