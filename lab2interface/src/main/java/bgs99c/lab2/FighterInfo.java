package bgs99c.lab2;

import bgs99c.lab2.shared.*;


public abstract class FighterInfo{
    /**
     * Returns this fighter's types.
     * @return Returns this fighter's types. Can be empty.
     */
    public Types[] getTypes(){
        throw new UnsupportedOperationException();
    }

    /**
     * Evolution level is the depth of inheritance from Fighter class
     * @return Evolution level
     */
    public int getEvolutionLevel(){
        throw new UnsupportedOperationException();
    }
    public String toString(){
        throw new UnsupportedOperationException();
    }
    /**
     * Returns current health in a battle
     * @return current health
     */
    public abstract int getHealth();
}
