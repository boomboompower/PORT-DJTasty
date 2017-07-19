package xyz.thebloodhound.djtasty.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;

import org.apache.logging.log4j.LogManager;

import xyz.thebloodhound.djtasty.DJTasty;

public class FileHandler {

    private static final File configFile = new File(new File(Minecraft.getMinecraft().mcDataDir.getPath() + "/mods/DJTastyMod"), "djtastymod.settings");

    private static JsonObject config = new JsonObject();

    public static boolean configExists() {
        return exists(configFile.getPath());
    }

    public static void loadSettings() {
        if (configExists()) {
            log("Config file exists! Reading...");
            try {
                FileReader reader = new FileReader(configFile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();

                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    builder.append(currentLine);
                }
                String complete = builder.toString();

                config = new JsonParser().parse(complete).getAsJsonObject();
            } catch (Exception ex) {
                log("Could not write config! Saving...");
                saveSettings();
            }
            DJTasty.cape = config.has("cape") ? config.get("cape").getAsInt() : 0;
            DJTasty.color = config.has("color") ? config.get("color").getAsInt() : 0;
            DJTasty.coordsX = config.has("coordsX") ? config.get("coordsX").getAsInt() : 0;
            DJTasty.coordsY = config.has("coordsY") ? config.get("coordsY").getAsInt() : 0;
            DJTasty.fpsEnabled = config.has("fpsEnabled") && config.get("fpsEnabled").getAsBoolean();
            DJTasty.capeEnabled = config.has("capeEnabled") && config.get("capeEnabled").getAsBoolean();
            DJTasty.infoEnabled = config.has("infoEnabled") && config.get("infoEnabled").getAsBoolean();
            DJTasty.separateColor = config.has("separateColor") ? config.get("separateColor").getAsInt() : 0;
            DJTasty.separateColors = config.has("separateColors") && config.get("separateColors").getAsBoolean();
        } else {
            log("Config does not exist! Saving...");
            saveSettings();
        }
    }

    public static void saveSettings() {
        config = new JsonObject();
        try {
            configFile.createNewFile();
            FileWriter writer = new FileWriter(configFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            config.addProperty("coordsX", DJTasty.coordsX);
            config.addProperty("coordsY", DJTasty.coordsY);
            config.addProperty("capeEnabled", DJTasty.capeEnabled);
            config.addProperty("color", DJTasty.color);
            config.addProperty("infoEnabled", DJTasty.infoEnabled);
            config.addProperty("fpsEnabled", DJTasty.fpsEnabled);
            config.addProperty("cape", DJTasty.cape);
            config.addProperty("separateColors", DJTasty.separateColors);
            config.addProperty("separateColor", DJTasty.separateColor);

            bufferedWriter.write(config.toString());
            bufferedWriter.close();
            writer.close();
        } catch (Exception ex) {
            log("Could not save config!");
            ex.printStackTrace();
        }
    }

    private static boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    private static void log(Object message, String... replacements) {
        LogManager.getLogger("FileUtils").info(String.format(String.valueOf(message), (Object) replacements));
    }
}
