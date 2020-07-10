package com.thistestuser.farlands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config
{
	/**
	 * Should we re-enable the far lands?
	 */
	public boolean isFarLands = true;
	
	/**
	 * Should we extend the world border?
	 */
	public boolean extendWB = true;
	
	/**
	 * Should we offset the terrain? If we should, then offsetX and offsetZ will be used.
	 */
	public boolean offset = false;
	
	public int offsetX = 0;
	public int offsetZ = 0;
	
	/**
	 * The location of the config file. It is currently located at %mc_dir%/config/farlandsmod.cfg.
	 */
	private static final File config = new File("config/farlandsmod.cfg");
	private static final Logger LOGGER = LogManager.getLogger();
	public static Config instance;
	
	static
	{
		instance = new Config();
		instance.loadConfig();
	}
	
	/**
	 * Loads the config.
	 */
	private void loadConfig()
	{
		try
		{
			if(!config.exists())
			{
				config.getParentFile().mkdirs();
				config.createNewFile();
				writeConfig();
				return;
			}
			 BufferedReader reader = new BufferedReader(new FileReader(config));
			 String s;
			 while((s = reader.readLine()) != null)
			 {
				 String[] args = s.split(":");
				 if(args[0].equalsIgnoreCase("farlands"))
					 isFarLands = args.length == 1 ? true : args[1].equalsIgnoreCase("true");
				 else if(args[0].equalsIgnoreCase("extendwb"))
					 extendWB = args.length == 1 ? true : args[1].equalsIgnoreCase("true");
				 else if(args[0].equalsIgnoreCase("offset"))
					 offset = args.length == 1 ? false : args[1].equalsIgnoreCase("true");
				 else if(args[0].equalsIgnoreCase("offsetX"))
					 offsetX = args.length == 1 ? 0 : parseInt(args[1], "offsetX");
				 else if(args[0].equalsIgnoreCase("offsetZ"))
					 offsetZ = args.length == 1 ? 0 : parseInt(args[1], "offsetZ");
			 }
			 reader.close();
		}catch(Exception e)
		{
			LOGGER.error("[FarLands]: An error occured while trying to load config!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Uses PrintWriter to write the config.
	 */
	private void writeConfig()
	{
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(config));	
			writer.println("#Visit https://github.com/ThisTestUser/FarLandsMod/ for the source code");
			writer.println("#Should we bring back the far lands?");
			writer.println("farlands:" + isFarLands);
			writer.println("#Should we extend the world border?");
			writer.println("extendwb:" + extendWB);
			writer.println("#Should we offset the terrain?");
			writer.println("#offestX and offsetZ will not do anything if this is false");
			writer.println("#There will be small differences in some terrain features like ores");
			writer.println("offset:" + offset);
			writer.println("#Note: The offsets are written in chunk coordinates, so please divide the value you want by 16!");
			writer.println("#Example: If offsetX and offsetZ are both set to 100, the center of the map will generate terrain at 1600,1600.");
			writer.println("#Do not put values outside of the integer limit (-2147483648 to 2147483647)!");
			writer.println("offsetX:" + offsetX);
			writer.println("offsetZ:" + offsetZ);
			writer.close();
		}catch(Exception e)
		{
			LOGGER.error("[FarLands]: An error occured while trying to write config!");
			e.printStackTrace();
		}
	}
	
	private int parseInt(String s, String name)
	{
		try
		{
			return Integer.parseInt(s);
		}catch(NumberFormatException e)
		{
			LOGGER.error("[FarLands]: Invaild offset for " + name);
			return 0;
		}
	}
}
