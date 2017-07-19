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

public class DetailsGui
        extends GuiScreen
{
    public static final String[] CAPES = { "2011", "2012", "2013", "2015", "2016" };
    private static final String[] HEADS = { "DJTasty's", "No" };
    private GuiButton capes;
    private GuiButton head;
    private GuiButton separateColors;
    private GuiButton colors;
    RenderInfo render = new RenderInfo();

    public void initGui() {
        capes = new GuiButton(0, width / 2 - 70, height / 2 - 62, 150, 20, "Current cape: " + CAPES[DJTasty.cape]);

        buttonList.add(capes);
        head = new GuiButton(1, width / 2 - 70, height / 2 - 40, 150, 20, HEADS[DJTasty.head] + " head");

        buttonList.add(head);
        separateColors = new GuiButton(2, width / 2 - 70, height / 2 - 18, 150, 20, (DJTasty.separateColors ? "Disable" : "Enable") + " Separate Colors");

        buttonList.add(separateColors);
        colors = new GuiButton(3, width / 2 - 70, height / 2 + 4, 150, 20, "XYZ Color: " + SettingsGui.COLOR_NAMES[DJTasty.separateColor]);
        if (DJTasty.separateColors) {
            buttonList.add(colors);
        }
    }

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
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

    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                DJTasty.cape = DJTasty.cape == 4 ? 0 : DJTasty.cape + 1;
                capes.displayString = ("Current cape: " + CAPES[DJTasty.cape]);
                return;
            case 1:
                DJTasty.head = DJTasty.head == 1 ? 0 : DJTasty.head + 1;
                head.displayString = (HEADS[DJTasty.head] + " head");
                return;
            case 2:
                DJTasty.separateColors = !DJTasty.separateColors;
                separateColors.displayString = ((DJTasty.separateColors ? "Disabe" : "Enable") + " Separate Colors");
                if (DJTasty.separateColors) {
                    buttonList.add(colors);
                } else {
                    buttonList.remove(colors);
                }
                return;
            case 3:
                DJTasty.separateColor = DJTasty.separateColor == 15 ? 0 : DJTasty.separateColor + 1;
                colors.displayString = ("XYZ Color: " + SettingsGui.COLOR_NAMES[DJTasty.separateColor]);
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
