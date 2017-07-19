package xyz.thebloodhound.djtasty.guiscreens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.thebloodhound.djtasty.DJTasty;
import xyz.thebloodhound.djtasty.render.RenderInfo;
import xyz.thebloodhound.djtasty.settings.FileHandler;

import java.io.IOException;

public class PositionGui extends GuiScreen {

    private final RenderInfo render = new RenderInfo();
    private boolean isDragging = false;
    private int lastX = 0;
    private int lastY = 0;

    public void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public void drawScreen(int x, int y, float partialTicks) {
        super.drawDefaultBackground();
        if (render.fpsOnly()) {
            render.renderFPS();
        } else {
            render.renderInfo();
        }
        super.drawScreen(x, y, partialTicks);
    }

    public void updateScreen() {}

    protected void keyTyped(char c, int key) {
        if (key == 1) {
            mc.displayGuiScreen(null);
        }
    }

    protected void mouseClicked(int x, int y, int time) {
        int minX = DJTasty.coordsX;
        int minY = DJTasty.coordsY;
        int maxX = render.right;
        int maxY = render.bottom;
        if ((x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY)) {
            isDragging = true;
            lastX = x;
            lastY = y;
        }try {
            super.mouseClicked(x, y, time);
        } catch (IOException ex) {
        }
    }

    protected void mouseReleased(int x, int y, int which) {
        if ((which == 0) && (isDragging)) {
            isDragging = false;
        }
        super.mouseReleased(x, y, which);
    }

    protected void mouseClickMove(int x, int y, int lastButtonClicked, long timeSinceClick) {
        if (isDragging)
        {
            render.fix();

            DJTasty.coordsX += x - lastX;
            DJTasty.coordsY += y - lastY;

            lastX = x;
            lastY = y;
        }
        super.mouseClickMove(x, y, lastButtonClicked, timeSinceClick);
    }

    public void onGuiClosed() {
        FileHandler.saveSettings();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
