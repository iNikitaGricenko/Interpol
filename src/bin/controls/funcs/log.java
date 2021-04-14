package bin.controls.funcs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class log {
    private String password;

    public log(String password)
    {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = md5.digest(password.getBytes());
        StringBuilder bldr = new StringBuilder();
        for (byte b : salt)
        {
            bldr.append(String.format("%02x",b));
        }
        this.password = bldr.toString();
    }

    public String GetPass()
    {
        return password;
    }
}
