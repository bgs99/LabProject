
package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Fighter extends FighterInfo{
public FighterStats getStats() {
throw new UnsupportedOperationException();
}
public final static int HEALTH = 50, DEFENCE = 10, ACCURACY = 20, EVASION = 10, POWER = 5;
public void addType(Types t){
throw new UnsupportedOperationException();
}
public String image = "";
public Move[] getMoves(){
throw new UnsupportedOperationException();
}
public void addMove(Move m){
throw new UnsupportedOperationException();
}
public int getAccuracy(){
throw new UnsupportedOperationException();
}
public int getEvasion() {
throw new UnsupportedOperationException();
}
public int getDefence() {
throw new UnsupportedOperationException();
}
public int getPower() {
throw new UnsupportedOperationException();
}
public Fighter(String n){
throw new UnsupportedOperationException();
}
public void changeAbility(int amount, Stats stat){
throw new UnsupportedOperationException();
}
public int getHealth(){
throw new UnsupportedOperationException();
}
}
