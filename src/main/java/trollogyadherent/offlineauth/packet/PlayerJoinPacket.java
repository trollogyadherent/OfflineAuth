package trollogyadherent.offlineauth.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import trollogyadherent.offlineauth.Config;
import trollogyadherent.offlineauth.OfflineAuth;
import trollogyadherent.offlineauth.database.Database;
import trollogyadherent.offlineauth.rest.OAServerData;
import trollogyadherent.offlineauth.util.Util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PlayerJoinPacket implements IMessageHandler<PlayerJoinPacket.SimpleMessage, IMessage>
{
    /* Server to Client: give me your password */
    /* Client to Server; *sends password*. If the hash does not match, player kicked */
    /* If the palyer is not kicked, the server tells what skin to load, place to implement default server skins TODO */
    @Override
    public IMessage onMessage(SimpleMessage message, MessageContext ctx)
    {
        if (ctx.side.isClient() && message.exchangecode == 0)
        {
            System.out.println("PlayerJoinPacket onMessage triggered, code 0 (from server)");
            OAServerData oasd = Util.getOAServerDatabyIP(Util.getIP(OfflineAuth.varInstanceClient.selectedServerData), Util.getPort(OfflineAuth.varInstanceClient.selectedServerData));
            if (oasd != null) {
                message.password = oasd.getPassword();
            } else {
                message.password = "";
            }
            message.exchangecode = 1;

            return message;
        }

        EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
        try {
            if (ctx.side.isServer() && message.exchangecode == 1)
            {
                System.out.println("PlayerJoinPacket onMessage triggered, code 1 (from client)");
                try {
                    if (Database.playerValid(entityPlayerMP.getDisplayName(), message.password)) {
                        OfflineAuth.info("User " + entityPlayerMP.getDisplayName() + " successfully logged in");
                    } else {
                        entityPlayerMP.playerNetServerHandler.kickPlayerFromServer(Config.kickMessage);
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    entityPlayerMP.playerNetServerHandler.kickPlayerFromServer(Config.kickMessage);
                    OfflineAuth.error(e.getMessage());
                    e.printStackTrace();
                }
                return null;
            } else {
                entityPlayerMP.playerNetServerHandler.kickPlayerFromServer(Config.kickMessage);
            }
        } catch (Exception e) {
            entityPlayerMP.playerNetServerHandler.kickPlayerFromServer(Config.kickMessage);
        }


        return null;
    }

    public static class SimpleMessage implements IMessage
    {
        private int exchangecode;  // When server queries for password, it's 0. When client responds, it's 1
        private String password;
        private String dataString;

        // this constructor is required otherwise you'll get errors (used somewhere in fml through reflection)
        public SimpleMessage() {}

        public SimpleMessage(int exchangecode, String password)
        {
            this.exchangecode = exchangecode;
            this.password = password;
            this.dataString = this.exchangecode + this.password;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.dataString = ByteBufUtils.readUTF8String(buf);
            this.exchangecode = Integer.parseInt(String.valueOf(this.dataString.charAt(0)));
            this.password = this.dataString.substring(1);
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            ByteBufUtils.writeUTF8String(buf, this.exchangecode + this.password);
        }
    }
}