package xyz.thebloodhound.djtasty.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import xyz.thebloodhound.djtasty.DJTasty;
import xyz.thebloodhound.djtasty.utils.CapeUtils;


public class RenderCape {

    @SubscribeEvent
    public void renderCape(RenderPlayerEvent.Pre event) {
        if ((event.entityPlayer.equals(Minecraft.getMinecraft().thePlayer)) && ((event.entityPlayer instanceof AbstractClientPlayer)) && (DJTasty.capeEnabled)) {
            CapeUtils.begin(Minecraft.getMinecraft().thePlayer, new ResourceLocation("DJTastyMod:textures/cape/" + xyz.thebloodhound.djtasty.guiscreens.DetailsGui.CAPES[DJTasty.cape] + ".png"));
        }
    }
}
