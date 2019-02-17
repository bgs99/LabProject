package bgs99c.lab2.shared;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Types of Pokemon
 */
public enum Types {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK,
    GHOST, DRAGON, DARK, STEEL, FAIRY;

    private static double[][] chart = new double[Types.values().length][Types.values().length];

    static {
        Scanner br = new Scanner(Types.class.getResourceAsStream("/type_chart"));
        for (int i = 0; i < chart.length; i++) {
            String line = br.nextLine();
            for(int j = 0; j < chart.length; j++) {
                switch (line.charAt(j)){
                    case '+':
                        chart[i][j] = 2;
                        break;
                    case '-':
                        chart[i][j] = 0.5;
                        break;
                    case '0':
                        chart[i][j] = 0;
                    default:
                        chart[i][j] = 1;
                }
            }
        }
    }

    public double getEfficiency(Types b){
        int i = this.ordinal();
        int j = b.ordinal();
        return chart[i][j];
    }
}
