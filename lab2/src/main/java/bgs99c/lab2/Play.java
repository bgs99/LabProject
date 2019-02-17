package bgs99c.lab2;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.stream.Stream;

public class Play {
    static ClassLoader cl;
    static URLClassLoader classLoader;
    static Method addUrl;
    static int f(){
        try{
            throw new Exception();
        }catch (Exception e){
            throw new RuntimeException();
        }finally {
        }
    }
    public static void main(String[] args) throws Exception {
        try {
            File folder = new File("checked/");
            Stream<String> fns = Arrays.stream(folder.list()).map(Play::getName);
            System.out.println(f());
            URL[] files = Arrays.stream(folder.listFiles()).map(Play::urlFromFile).toArray(URL[]::new);
            for(URL url : files) {
                System.out.println(url);
                classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
                addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrl.setAccessible(true);
                addUrl.invoke(classLoader, url);
            }
            if(args.length > 0)
            {
                File arg = new File(args[0]);
                addUrl.invoke(classLoader, arg.toURI().toURL());
                fns = Stream.concat(fns, Stream.of(getName(arg.getName())));
            }
            Player[] ps = fns.map(Play::loadClass).toArray(Player[]::new);
            Tournament t = new Tournament(ps);
            System.out.println(t.start() + " won the tournament!");
        }
        catch (Exception e){
            //System.out.println("Damaged class! Halting...");
            throw new Exception("Damaged class! Halting...");
        }
    }
    static Player loadClass(String name){
        try {
            System.out.println(name);
            return (Player) classLoader.loadClass(name).newInstance();
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    static String getName(String path){
        return path.substring(0, path.lastIndexOf('.'));
    }
    static URL urlFromFile(File f){
        try {
            return f.toURI().toURL();
        }
        catch (MalformedURLException e){
            return null;
        }
    }
}
