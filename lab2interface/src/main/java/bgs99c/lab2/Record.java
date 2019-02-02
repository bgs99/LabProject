
package bgs99c.lab2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Record implements Serializable{
public static final long serialVersionUID = 42L;
public StringBuilder msgs = new StringBuilder();
public List<Log> logs = new ArrayList<>();
}
