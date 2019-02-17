import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Filter {
    private String contents;
    private Path path;
    private Path to;
    private List<String> pieces = new ArrayList<>();
    public Filter(String name){
        path = FileSystems.getDefault().getPath("lab2/src/main/java/bgs99c/lab2/" + name + ".java").toAbsolutePath();
        to = FileSystems.getDefault().getPath("./"+name + ".java").toAbsolutePath();
        try {
            contents = Files.readAllLines(path).stream().reduce("", (a, b) -> a + "\n" + b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String[] hidden = {"start", "logger"};
    private String[] full = {"enum", "class", "interface"};

    private int readBlock(int start){
        int begin = start;
        int open = contents.indexOf('{', start) + 1;
        boolean skip = false, copy = false;

        skip = isSpecial(start, open, hidden);
        copy = isSpecial(start, open, full);

        start = contents.indexOf('{', start) + 1;
        int lvl = 1;
        while (lvl > 0) {
            switch (contents.charAt(start)){
                case '{':
                    lvl++;
                    break;
                case '}':
                    lvl--;
                default:
                    break;
            }
            start++;
        }
        start++;
        if(!skip){
            if(!copy) {
                pieces.add(contents.substring(begin, open) + "\nthrow new UnsupportedOperationException();\n}");
            } else {
                pieces.add(contents.substring(begin, start));
            }
        }
        return start;
    }

    private boolean isSpecial(int start, int open, String[] full) {
        for(String h : full){
            int i = contents.indexOf(h, start);
            if(i > 0 && i < open) {
                return true;
            }
        }
        return false;
    }

    public void filter(){
        if(contents.contains("interface") ||
                (contents.indexOf("Log") > 0 && contents.indexOf("Log") < contents.indexOf('{'))){
            pieces.add(contents);
            try {
                Files.write(to, pieces);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        int curPos = contents.indexOf('{');
        pieces.add(contents.substring(0, curPos+1));
        while (contents.indexOf("public", curPos) > 0 || contents.indexOf("protected", curPos) > 0) {
            int pu = contents.indexOf("public", curPos), pr = contents.indexOf("protected", curPos);
            curPos = pu >=0 && (pu < pr || pr < 0) ? pu : pr;
            try {
                int sc = contents.indexOf(';', curPos);
                int eq = contents.indexOf('=', curPos);
                int f = contents.indexOf('{', curPos);
                if (f < 0 || (sc >=0 && sc < f) || (eq >=0 && eq < f)) {
                    int end = contents.indexOf(';', curPos) + 1;
                    pieces.add(contents.substring(curPos, end));
                    curPos = end;
                } else {
                    curPos = readBlock(curPos);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(to.getFileName());
                System.out.println(contents.substring(curPos));
                return;
            }
        }
        pieces.add("}");
        try {
            Files.write(to, pieces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
