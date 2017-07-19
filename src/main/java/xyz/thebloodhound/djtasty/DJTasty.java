package xyz.thebloodhound.djtasty;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.thebloodhound.djtasty.render.RenderCape;
import xyz.thebloodhound.djtasty.render.RenderInfo;
import xyz.thebloodhound.djtasty.settings.FileHandler;

@Mod(modid="DJTastyMod", version="1.0", name="DJTastyMod")
public class DJTasty {

    public static int color = 11;
    public static int cape = 4;
    public static int separateColor = 6;
    public static boolean infoEnabled = true;
    public static boolean fpsEnabled = true;
    public static boolean separateColors = true;
    public static final int[] COLORS = { 0, 170, 43520, 43690, 11141120, 11141290, 16755200, 11184810, 5592405, 5592575, 5635925, 5636095, 16733525, 16733695, 16777045, 16777215 };
    public static final String MODID = "DJTastyMod";
    public static final String NAME = "DJTastyMod";
    public static final String VERSION = "1.0";
    private static DJTasty instance;
    private Logger logger;
    public static int coordsX;
    public static int coordsY;
    public static int head;
    public static boolean capeEnabled;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException {
        instance = this;
        logger = LogManager.getLogger("DJTastyMod");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        ClientCommandHandler.instance.registerCommand(new Command());
        MinecraftForge.EVENT_BUS.register(new RenderCape());
        MinecraftForge.EVENT_BUS.register(new RenderInfo());
        MinecraftForge.EVENT_BUS.register(this);
        FileHandler.loadSettings();
        logger.info("DJTastyMod 1.0 succesfully loaded!");
    }

    public static void debug(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[DJTasty Debug] " + EnumChatFormatting.WHITE + message));
    }

    public static DJTasty instance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
