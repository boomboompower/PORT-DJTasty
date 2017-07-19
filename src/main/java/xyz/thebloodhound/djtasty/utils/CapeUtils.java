/*
 *     Copyright (C) 2017 boomboompower
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.thebloodhound.djtasty.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;

public class CapeUtils {

    private static final MethodHandle GET_PLAYER_INFO = ReflectUtils.findMethod(AbstractClientPlayer.class, new String[] {"getPlayerInfo", "func_175155_b"});

    public static void begin(AbstractClientPlayer player, ResourceLocation location) {
        Minecraft.getMinecraft().addScheduledTask(() -> replaceCape(player, location));
    }

    private static void replaceCape(AbstractClientPlayer player, ResourceLocation location) {
        NetworkPlayerInfo info = null;

        try {
            info = (NetworkPlayerInfo) GET_PLAYER_INFO.invoke(player);
        } catch (Throwable ex) {
            System.out.println("Could not get player info!");
        }

        if (info == null) {
            System.out.println("playerInfo null, stopping!");
            return;
        }

        try {
            ObfuscationReflectionHelper.setPrivateValue(NetworkPlayerInfo.class, info, location, "locationCape", "field_178862_f");
        } catch (Exception ex) {
            System.out.println("Could not set cape field!");
        }
    }
}
