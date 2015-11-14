package com.estebes.gravisuitereloaded;

import com.estebes.gravisuitereloaded.init.ItemInit;
import com.estebes.gravisuitereloaded.init.ItemInitClassic;
import com.estebes.gravisuitereloaded.init.RecipeInit;
import com.estebes.gravisuitereloaded.init.RecipeInitClassic;
import com.estebes.gravisuitereloaded.proxy.ServerProxy;
import com.estebes.gravisuitereloaded.reference.Reference;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, dependencies = "required-after:IC2", version = Reference.VERSION)
public class GraviSuiteReloaded {
    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
    public static ServerProxy proxy;

    @Mod.Instance(Reference.MOD_ID)
    public static GraviSuiteReloaded instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent preinit) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent init) {
        if(Loader.isModLoaded("IC2-Classic-Spmod")) {
            ItemInitClassic.init();
        }
        else {
            ItemInit.init();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent postinit) {
        if(Loader.isModLoaded("IC2-Classic-Spmod")) {
            RecipeInitClassic.init();
        }
        else {
            RecipeInit.init();
        }
    }
}