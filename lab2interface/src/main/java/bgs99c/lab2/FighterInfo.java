
package bgs99c.lab2;
import bgs99c.lab2.shared.*;

import java.util.*;

public abstract class FighterInfo {
public final int hashCode() {
throw new UnsupportedOperationException();
}
public final Types[] getTypes(){
throw new UnsupportedOperationException();
}
public final int getEvolutionLevel(){
throw new UnsupportedOperationException();
}
protected List<Types> types;
protected int typePoints = 0;
public final String toString(){
throw new UnsupportedOperationException();
}
public abstract double getHealthBar();
}
