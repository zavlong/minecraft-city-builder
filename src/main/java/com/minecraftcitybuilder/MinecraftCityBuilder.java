package com.minecraftcitybuilder;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MinecraftCityBuilder.MODID, name = MinecraftCityBuilder.NAME, version = MinecraftCityBuilder.VERSION)
public class MinecraftCityBuilder {
    public static final String MODID = "minecraftcitybuilder";
    public static final String NAME = "Minecraft City Builder";
    public static final String VERSION = "0.0.1";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // register config, resources
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // register commands, event handlers
    }
}
