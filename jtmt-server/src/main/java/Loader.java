import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Stream;

import bgs99c.lab2.Player;
import bgs99c.shared.FighterStats;
import bgs99c.lab2.Fighter;


public class Loader {
	private static Map<String, URLClassLoader> classLoaders = new HashMap<String, URLClassLoader>();
	
	public static List<FighterStats> teamInfo(String name){
		List<FighterStats> res = new ArrayList<>();
		try {
			URLClassLoader cl = classLoaders.get(name+".jar");
			JarInputStream jarFile = new JarInputStream(new FileInputStream(PD + name+".jar"));
	        JarEntry jarEntry;
	        List<String> classNames = new ArrayList<>();
	        while (true) {
	            jarEntry = jarFile.getNextJarEntry();
	            if (jarEntry == null) {
	                break;
	            }
	            String cn = jarEntry.getName();
	            if (jarEntry.getName().endsWith(".class")) {
	                classNames.add(cn.substring(0, cn.lastIndexOf('.')));
	            }
	        }
	        jarFile.close();
	        
	        List<Class<?>> classes = new ArrayList<>();
	        for(String s : classNames) {
	        	Class<?> t = cl.loadClass(s);
	        	if(Fighter.class.isAssignableFrom(t))
	        		classes.add(t);
	        }
			for(Class<?> c : classes){
				Constructor<?> constructor = c.getDeclaredConstructor();
				constructor.setAccessible(true);
				Fighter b = (Fighter)constructor.newInstance();
				FighterStats fs = new FighterStats();
				fs.name = c.getName();
				fs.accuracy = b.getAccuracy();
				fs.defence = b.getDefence();
				fs.evasion = b.getEvasion();
				fs.health = b.getHealth();
				fs.image = b.image;
				fs.power = b.getPower();
				fs.types = b.getTypes();
				res.add(fs);
			}
		} catch(Exception e) {
			System.out.println("Can't get fighter info of " + name);
		}
		return res;
	}
	
	
	public static Player loadPlayer(String name) {
		try {
			URLClassLoader cl = classLoaders.get(name+".jar");
			Class<?> c = cl.loadClass(name);
			Constructor<?> constructor = c.getDeclaredConstructor();
			constructor.setAccessible(true);
			Player b = (Player)constructor.newInstance();
			return b;
		} catch (Exception e) {
			System.err.println("[WARNING] Couldn't create player " + name);
			e.printStackTrace();
			return null;
		}
	}
	
	public static void loadAll() {
		try (Stream<Path> paths = Files.walk(Paths.get(PD))) {
		    paths
		        .filter(Files::isRegularFile)
		        .map(Path::getFileName)
		        .map(Path::toString)
		        .forEach(Loader::load);
		} catch(Exception  e) {
			System.err.println("[ERROR] Couldn't read players' jars");
		}
	}
	/**
	 * Loads given jar file
	 * @param name Name of the loaded jar file with extension
	 */
	public static void load(String name) {
		try {
			if(classLoaders.containsKey(name))
				classLoaders.get(name).close();
			
			URLClassLoader cl = new URLClassLoader(new URL[] {
					new URL("file:"+PD+name),
					//new URL("file:" + IN)
					}, Loader.class.getClassLoader());
			classLoaders.put(name, cl);
			System.out.println(name);
		} catch (Exception e) {
			System.err.println("[WARNING] " + name + " cannot be loaded"); 
			e.printStackTrace();
		}
	}

	static {
		Properties config = new Properties();
		try {
			config.load(Loader.class.getResourceAsStream("/storage.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		PD = Paths.get(config.getProperty("dir")).toAbsolutePath().toString() + "/";
	}
	/**
	 * Path to players' jars
	 */
	public static String PD;
}
