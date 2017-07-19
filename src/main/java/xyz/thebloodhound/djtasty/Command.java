package xyz.thebloodhound.djtasty;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import xyz.thebloodhound.djtasty.guiscreens.DetailsGui;
import xyz.thebloodhound.djtasty.guiscreens.PositionGui;
import xyz.thebloodhound.djtasty.guiscreens.SettingsGui;

import java.util.List;

public class Command extends CommandBase {

    public String getCommandName() {
        return "djtasty";
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "/djtasty position or /djtasty settings or /djtasty details";
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("settings")) {
                new SettingsGui().display();
            } else if (args[0].equalsIgnoreCase("position")) {
                new PositionGui().display();
            } else if (args[0].equalsIgnoreCase("details")) {
                new DetailsGui().display();
            } else {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[DJTasty] " + EnumChatFormatting.WHITE + "Please use correct arguments! Usage: " + getCommandUsage(sender)));
            }
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[DJTasty] " + EnumChatFormatting.WHITE + "You need arguments! Usage: " + getCommandUsage(sender)));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, "settings", "position", "details") : null;
    }
}
