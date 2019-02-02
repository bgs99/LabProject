
package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FighterInfo {
public final int hashCode() {
throw new UnsupportedOperationException();
}
public Types[] getTypes(){
throw new UnsupportedOperationException();
}
public int getEvolutionLevel(){
throw new UnsupportedOperationException();
}
protected List<Types> types;
protected int typePoints = 0;
public String toString(){
throw new UnsupportedOperationException();
}
public abstract int getHealth();
}
