package xyz.thebloodhound.djtasty.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import xyz.thebloodhound.djtasty.DJTasty;

public class RenderInfo extends Gui {

    Minecraft mc = Minecraft.getMinecraft();
    private final String[] directions = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };
    private final int background = 1140850688;
    public int right;
    public int bottom;
    public int width;
    public int height;

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event)
    {
        if ((!DJTasty.infoEnabled) && (!DJTasty.fpsEnabled)) {
            return;
        }
        if ((event.isCancelable()) || (event.type != RenderGameOverlayEvent.ElementType.HOTBAR) || (mc.gameSettings.showDebugInfo)) {
            return;
        }
        if (DJTasty.infoEnabled) {
            renderInfo();
        } else if (fpsOnly()) {
            renderFPS();
        }
        fix();
    }

    public void renderFPS()
    {
        drawRect(DJTasty.coordsX, DJTasty.coordsY, DJTasty.coordsX + mc.fontRendererObj

                .getStringWidth(mc.debug
                        .split(" ")[0] + " FPS") + 4, DJTasty.coordsY + 12, 1140850688);

        mc.fontRendererObj.drawString(mc.debug.split(" ")[0] + " FPS", DJTasty.coordsX + 3, DJTasty.coordsY + 2,

                getColor(DJTasty.color));

        right = (DJTasty.coordsX + mc.fontRendererObj.getStringWidth(mc.debug.split(" ")[0] + " FPS") + 4);

        bottom = (DJTasty.coordsY + 12);
        width = (mc.fontRendererObj.getStringWidth(mc.debug.split(" ")[0] + " FPS") + 4);

        height = 12;
    }

    public void renderInfo()
    {
        int xLine = DJTasty.coordsY + 1;
        int yLine = xLine + 10;
        int zLine = yLine + 10;
        int fpsLine = zLine + 10;
        if (DJTasty.head == 0)
        {
            drawRect(DJTasty.coordsX, DJTasty.coordsY, DJTasty.coordsX + 76, DJTasty.coordsY + (DJTasty.fpsEnabled ? 40 : 30) + 1, 1140850688);

            drawFace(DJTasty.coordsX + 63, yLine - 1);
            right = (DJTasty.coordsX + 76);
            width = 76;
        }
        else
        {
            drawRect(DJTasty.coordsX, DJTasty.coordsY, DJTasty.coordsX + 62, DJTasty.coordsY + (DJTasty.fpsEnabled ? 40 : 30) + 1, 1140850688);

            right = (DJTasty.coordsX + 62);
            width = 62;
        }
        if (DJTasty.separateColors)
        {
            mc.fontRendererObj.drawString("x: ", DJTasty.coordsX + 1, xLine,
                    getColor(DJTasty.separateColor));
            mc.fontRendererObj.drawString("y: ", DJTasty.coordsX + 1, yLine,
                    getColor(DJTasty.separateColor));
            mc.fontRendererObj.drawString("z: ", DJTasty.coordsX + 1, zLine,
                    getColor(DJTasty.separateColor));
            mc.fontRendererObj.drawString(
                    Long.toString(Math.round(mc.thePlayer.posX)), DJTasty.coordsX + 1 + mc.fontRendererObj

                            .getStringWidth("x: "), xLine,
                    getColor(DJTasty.color));
            mc.fontRendererObj.drawString(
                    Long.toString(Math.round(mc.thePlayer.posY - 1.0D)), DJTasty.coordsX + 1 + mc.fontRendererObj

                            .getStringWidth("y: "), yLine,
                    getColor(DJTasty.color));
            mc.fontRendererObj.drawString(
                    Long.toString(Math.round(mc.thePlayer.posZ)), DJTasty.coordsX + 1 + mc.fontRendererObj

                            .getStringWidth("z: "), zLine,
                    getColor(DJTasty.color));
        }
        else
        {
            mc.fontRendererObj.drawString("x: " +
                            Math.round(mc.thePlayer.posX), DJTasty.coordsX + 1, xLine,
                    getColor(DJTasty.color));
            mc.fontRendererObj.drawString("y: " +
                            (int)Math.floor(mc.thePlayer.posY - 1.0D), DJTasty.coordsX + 1, yLine,
                    getColor(DJTasty.color));
            mc.fontRendererObj.drawString("z: " +
                            Math.round(mc.thePlayer.posZ), DJTasty.coordsX + 1, zLine,
                    getColor(DJTasty.color));
        }
        if (DJTasty.fpsEnabled) {
            mc.fontRendererObj.drawString(mc.debug.split(" ")[0] + " FPS", DJTasty.coordsX + 1, fpsLine,

                    getColor(DJTasty.color));
        }
        mc.fontRendererObj.drawString(directions[
                        getPlayerDirection()], DJTasty.coordsX + 60 - mc.fontRendererObj

                        .getStringWidth(directions[getPlayerDirection()]), yLine,
                getColor(DJTasty.color));

        bottom = (DJTasty.fpsEnabled ? DJTasty.coordsY + 41 : DJTasty.coordsY + 31);

        height = (DJTasty.fpsEnabled ? 40 : 30);
    }

    private void drawFace(int x, int y)
    {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(255.0F, 255.0F, 255.0F, 255.0F);

        x = (int)(x / 0.036D);
        y = (int)(y / 0.036D);

        GL11.glPushMatrix();
        GL11.glDisable(2896);
        GL11.glScalef(0.036F, 0.036F, 0.036F);
        if (DJTasty.head == 0) {
            mc.renderEngine.bindTexture(new ResourceLocation("DJTastyMod:textures/gui/DJTasty.png"));
        }
        drawTexturedModalRect(x, y, 0, 0, 256, 256);

        GL11.glPopMatrix();

        GL11.glDisable(3042);
    }

    private int getPlayerDirection()
    {
        double myPoint = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) + 180.0D;
        myPoint += 22.5D;
        myPoint %= 360.0D;
        myPoint /= 45.0D;
        return MathHelper.floor_double(myPoint);
    }

    public boolean fpsOnly()
    {
        if ((!DJTasty.infoEnabled) && (DJTasty.fpsEnabled)) {
            return true;
        }
        return false;
    }

    public void fix()
    {
        ScaledResolution res = new ScaledResolution(mc);
        if (bottom >= res.getScaledHeight()) {
            DJTasty.coordsY = res.getScaledHeight() - height;
        }
        if (right >= res.getScaledWidth()) {
            DJTasty.coordsX = res.getScaledWidth() - width;
        }
        if (DJTasty.coordsX <= 0) {
            DJTasty.coordsX = 0;
        }
        if (DJTasty.coordsY <= 0) {
            DJTasty.coordsY = 0;
        }
    }

    private int getColor(int index) {
        if (index == 16) {
            return Color.HSBtoRGB(
                    (float)(System.currentTimeMillis() % 1000L) / 1000.0F, 0.8F, 0.8F);
        }
        return DJTasty.COLORS[index];
    }
}
