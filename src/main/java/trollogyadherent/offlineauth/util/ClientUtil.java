package trollogyadherent.offlineauth.util;

import net.minecraft.server.MinecraftServer;
import trollogyadherent.offlineauth.OfflineAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class ClientUtil {
    public static boolean isSinglePlayer()
    {
        try
        {
            if(MinecraftServer.getServer() != null && MinecraftServer.getServer().isServerRunning())
            {
                return MinecraftServer.getServer().isSinglePlayer();
            }
            return false;
        }
        catch( Exception e ) // Server is null, not started
        {
            return false;
        }
    }

    public static String getServerKeyPath(String ip, String port) {
        return OfflineAuth.varInstanceClient.keyCachePath + File.separator + ip + "_" + port + ".key";
    }
    public static PublicKey getServerPublicKey(String ip, String port) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File keyFile = new File(getServerKeyPath(ip, port));
        if (!keyFile.exists()) {
            return null;
        }
        return RsaKeyUtil.loadPublicKey(keyFile.getAbsolutePath());
    }

    public static void SaveServerPublicKeyToCache(PublicKey pubKey, String ip, String port) throws IOException {


        File f = new File(getServerKeyPath(ip, port));
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(pubKey.getEncoded());
        fos.flush();
        fos.close();
    }
}
