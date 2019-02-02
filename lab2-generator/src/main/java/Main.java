import bgs99c.lab2.Tournament;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        String[] skip = {"Tournament", "Play", "OutputLogger"};
        String[] l = new File("lab2/src/main/java/bgs99c/lab2/").list();
        for(String g : l){
            String r = g.substring(0, g.indexOf('.'));
            boolean skipping = false;
            for(String s : skip){
                if(r.equals(s)){
                    skipping = true;
                    break;
                }
            }
            if(skipping)
                continue;

            System.out.println(r);

            Filter f = new Filter(r);
            f.filter();
        }
    }
}
