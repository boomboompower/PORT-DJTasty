package xyz.thebloodhound.djtasty.guiscreens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.thebloodhound.djtasty.DJTasty;
import xyz.thebloodhound.djtasty.render.RenderInfo;
import xyz.thebloodhound.djtasty.settings.FileHandler;
import xyz.thebloodhound.djtasty.utils.CapeUtils;

public class SettingsGui extends GuiScreen {

    private final RenderInfo render = new RenderInfo();
    public static final String[] COLOR_NAMES = { "Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White", "Chroma" };
    private GuiButton buttonCapeEnabled;
    private GuiButton buttonTextColor;
    private GuiButton buttonFPSEnabled;
    private GuiButton buttonInfoEnabled;

    public void initGui() {
        buttonCapeEnabled = new GuiButton(0, width / 2 - 70, height / 2 - 62, 150, 20, (DJTasty.capeEnabled ? "Disable" : "Enable") + " Cape");

        buttonList.add(buttonCapeEnabled);
        buttonTextColor = new GuiButton(1, width / 2 - 70, height / 2 - 40, 150, 20, "Text color: " + COLOR_NAMES[DJTasty.color]);

        buttonList.add(buttonTextColor);
        buttonFPSEnabled = new GuiButton(2, width / 2 - 70, height / 2 - 18, 150, 20, (DJTasty.fpsEnabled ? "Disable" : "Enable") + " FPS");

        buttonList.add(buttonFPSEnabled);
        buttonInfoEnabled = new GuiButton(3, width / 2 - 70, height / 2 + 4, 150, 20, (DJTasty.infoEnabled ? "Disable" : "Enable") + " Info");

        buttonList.add(buttonInfoEnabled);
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void drawScreen(int x, int y, float partialTicks)
    {
        super.drawDefaultBackground();
        if (DJTasty.infoEnabled) {
            render.renderInfo();
        }
        if (render.fpsOnly()) {
            render.renderFPS();
        }
        render.fix();

        super.drawScreen(x, y, partialTicks);
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                buttonCapeEnabled.displayString = (((DJTasty.capeEnabled = !DJTasty.capeEnabled) ? "Disable" : "Enable") + " Cape");

                CapeUtils.begin(Minecraft.getMinecraft().thePlayer, null);

                break;
            case 1:
                buttonTextColor.displayString = ("Text color: " + COLOR_NAMES[(DJTasty.color = DJTasty.color == 15 ? 0 : DJTasty.color + 1)]);
                break;
            case 2:
                buttonFPSEnabled.displayString = (((DJTasty.fpsEnabled = !DJTasty.fpsEnabled) ? "Disable" : "Enable") + " FPS");
                break;
            case 3:
                buttonInfoEnabled.displayString = (((DJTasty.infoEnabled = !DJTasty.infoEnabled) ? "Disable" : "Enable") + " Info");
        }
    }

    public void onGuiClosed() {
        FileHandler.saveSettings();
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
