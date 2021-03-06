package trollogyadherent.offlineauth.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import trollogyadherent.offlineauth.OfflineAuth;
import trollogyadherent.offlineauth.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandGetMyUUID implements ICommand {
    private final List aliases;

    public CommandGetMyUUID()
    {
        aliases = new ArrayList();
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "myuuid";
    }

    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "/myuuid";
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] argString) {
        /*UUID uuid = Util.genRealUUID();
        try {
            OfflineAuth.varInstanceServer.uuidIdField.set(((EntityPlayerMP) sender).field_146106_i, uuid);
            OfflineAuth.varInstanceServer.uuidIdField2.set((sender), uuid);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
        sender.addChatMessage(new ChatComponentText(((EntityPlayerMP) sender).getUniqueID().toString()));
        OfflineAuth.info(sender.getCommandSenderName() + " issued myuuid command");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender var1)
    {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender var1, String[] var2)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] var1, int var2)
    {
        return false;
    }
}