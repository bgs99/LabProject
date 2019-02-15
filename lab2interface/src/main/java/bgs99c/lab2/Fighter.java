
package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Fighter extends FighterInfo{
public final FighterStats getStats() {
throw new UnsupportedOperationException();
}
public final static int HEALTH = 50, DEFENCE = 10, ACCURACY = 20, EVASION = 10, POWER = 5;
public final void addType(Types t){
throw new UnsupportedOperationException();
}
public String image = "";
public final Move[] getMoves(){
throw new UnsupportedOperationException();
}
public final void addMove(Move m){
throw new UnsupportedOperationException();
}
public final int getAccuracy(){
throw new UnsupportedOperationException();
}
public final int getEvasion() {
throw new UnsupportedOperationException();
}
public final int getDefence() {
throw new UnsupportedOperationException();
}
public final int getPower() {
throw new UnsupportedOperationException();
}
public Fighter(String n){
throw new UnsupportedOperationException();
}
public final void increaseStat(int amount, Stats stat){
throw new UnsupportedOperationException();
}
public  String missedMessage(FighterInfo target) {
throw new UnsupportedOperationException();
}
public String dyingMessage(FighterInfo killer) {
throw new UnsupportedOperationException();
}
public String enterFightMessage() {
throw new UnsupportedOperationException();
}
public String evasionMessage(FighterInfo attacker){
throw new UnsupportedOperationException();
}
public String defendedMessage(FighterInfo attacker){
throw new UnsupportedOperationException();
}
public String successfulAttackMessage(FighterInfo target){
throw new UnsupportedOperationException();
}
public String unsuccessfulAttackMessage(FighterInfo target) {
throw new UnsupportedOperationException();
}
public final int getHealth(){
throw new UnsupportedOperationException();
}
public final double getHealthBar() {
throw new UnsupportedOperationException();
}
}
