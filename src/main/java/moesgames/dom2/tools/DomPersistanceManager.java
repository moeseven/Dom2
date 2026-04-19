package moesgames.dom2.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import moesgames.dom2.models.PretenderConfiguration;

public class DomPersistanceManager {
	
	private static final Path pretendersDirectory = getSaveDirectory("pretenders");
	
	
	public static Path getSaveDirectory(String subFolder) {
		String gameName = "Dom2";
	    String os = System.getProperty("os.name").toLowerCase();
	    Path baseDir;

	    if (os.contains("win")) {
	        String appData = System.getenv("APPDATA");
	        baseDir = (appData != null)
	                ? Paths.get(appData)
	                : Paths.get(System.getProperty("user.home"));
	    } else if (os.contains("mac")) {
	        baseDir = Paths.get(System.getProperty("user.home"),
	                "Library", "Application Support");
	    } else {
	        baseDir = Paths.get(System.getProperty("user.home"),
	                ".local", "share");
	    }

	    Path saveDir = baseDir.resolve(gameName).resolve(subFolder);
	    try {
	        Files.createDirectories(saveDir);
	    } catch (IOException e) {
	        throw new RuntimeException("Could not create save directory", e);
	    }

	    return saveDir;
	}

	
	public static void savePretender(PretenderConfiguration pc) {
		if (!(pc instanceof PretenderConfiguration)) {
			System.out.println("failed to save pretender config");
			return;
		}
		try {
			FileOutputStream saveFile = new FileOutputStream(pretendersDirectory.resolve(pc.getName()+".sav").toFile());
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(pc);
			save.close();
		} catch (NotSerializableException e) {
			System.out.println("failed to save pretender config");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PretenderConfiguration tryloadPretender(String name) {
		File settingsFile = pretendersDirectory.resolve(name+".sav").toFile();
		PretenderConfiguration pretenderConfig = null;
		if (settingsFile.exists()) {
			try {
				FileInputStream saveFile = new FileInputStream(settingsFile);
				ObjectInputStream load = new ObjectInputStream(saveFile);
				try {
					pretenderConfig = (PretenderConfiguration) load.readObject();
				} catch (Exception e) {
					pretenderConfig = new PretenderConfiguration();
					e.printStackTrace();
				}
				load.close();
			} catch (IOException e) {
				pretenderConfig = new PretenderConfiguration();
			}
		}else {
			pretenderConfig = new PretenderConfiguration();
		}
		return pretenderConfig;
	}
	

}
