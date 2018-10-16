package com.thistestuser.farlands;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

import org.apache.logging.log4j.Logger;

@IFMLLoadingPlugin.TransformerExclusions({"com.thistestuser.farlands"})
public class FarLandsCore implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass() 
    {
        return new String[]{FarLandsTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() 
    {
        return null;
    }

    @Override
    public String getSetupClass() 
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() 
    {
        return null;
    }
}
