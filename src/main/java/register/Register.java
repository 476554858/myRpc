package register;

import framework.URL;

import java.io.*;
import java.util.*;

public class Register {
    private static Map<String,Map<URL,Class>> REGISTER = new HashMap<String, Map<URL, Class>>();

    public static void regist(URL url, String interfaceName, Class implClass){
        Map<URL,Class> map = new HashMap<URL, Class>();
        map.put(url,implClass);
        REGISTER.put(interfaceName,map);
        saveFile();//保存到文件中
    }

    public static Class get(URL url,String interfaceName){
        return REGISTER.get(interfaceName).get(url);
    }

    public static URL random(String interfaceName){
        REGISTER = getFile();//从文件中取
        List<URL> urls = new ArrayList<URL>(REGISTER.get(interfaceName).keySet());
        Random random = new Random();
        int index = random.nextInt(urls.size());
        return urls.get(index);
    }

    public static void saveFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("c://temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(REGISTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,Map<URL,Class>> getFile(){
        try {
            FileInputStream fileInputStream = new FileInputStream("c://temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, Map<URL, Class>>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
