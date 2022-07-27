package trollogyadherent.offlineauth.gui.skin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.matthiasmann.twl.utils.PNGDecoder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import trollogyadherent.offlineauth.OfflineAuth;
import trollogyadherent.offlineauth.skin.client.ClientSkinUtil;
import trollogyadherent.offlineauth.util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SideOnly(Side.CLIENT)
public class SkinListEntry {
    protected final String skinName;
    protected final Minecraft mc;

    protected final SkinManagmentGUI previous;
    //private static final ResourceLocation temp = new ResourceLocation("textures/gui/resource_packs.png");
    private /*static*/ ResourceLocation skinResourceLocation = new ResourceLocation("textures/gui/resource_packs.png");
    private String skinSize;
    public SkinListEntry(SkinManagmentGUI skinManagmentGUI, String skinName) {
        this.previous = skinManagmentGUI;
        this.skinName = skinName;
        this.mc = Minecraft.getMinecraft();
        skinSize = "null";

        File imageFile = ClientSkinUtil.getSkinFile(skinName);
        if (imageFile == null || !imageFile.exists()) {
            OfflineAuth.error("Error skin image does not exist: " + skinName);
            return;
        }
        if (Util.filesizeInMegaBytes(imageFile) >= 1) {
            skinSize = Math.floor(Util.filesizeInMegaBytes(imageFile) * 100) / 100 + " mb";
        } else if (Util.filesizeInKiloBytes(imageFile) >= 1) {
            skinSize = Math.floor(Util.filesizeInKiloBytes(imageFile) * 100) / 100 + " kb";
        } else {
            skinSize = Math.floor(Util.filesizeInBytes(imageFile) * 100) / 100 + " b";
        }
        if (!Util.pngIsSane(imageFile)) {
            OfflineAuth.error("Sussy error loading skin image, not sane: " + skinName);
            return;
        }
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            OfflineAuth.error("Error loading skin image: " + skinName);
            e.printStackTrace();
            return;
        }
        if (bufferedImage.getHeight() != bufferedImage.getWidth()) {
            //System.out.println("texture is not square, " + skinName);
            BufferedImage bufferedImageNew = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight() * 2, bufferedImage.getType());
            Graphics g = bufferedImageNew.getGraphics();
            g.drawImage(bufferedImage, 0, 0, null);
            g.dispose();
            bufferedImage = bufferedImageNew;
        }
        skinResourceLocation = new ResourceLocation("offlineauth", "skinlistentryskins/" + skinName);
        ClientSkinUtil.loadTexture(bufferedImage, skinResourceLocation);
    }

    public void drawEntry(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_)
    {
        this.bindIcon();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l2 = 8;
        int i3 = 8;

        Gui.func_152125_a(p_148279_2_, p_148279_3_, 8.0F, (float) l2, 8, i3, 32/*8*/, /*8*/32, 64.0F, 64.0F);
        int i2;

        if ((this.mc.gameSettings.touchscreen || p_148279_9_) && this.func_148310_d())
        {
            /*this.mc.getTextureManager().bindTexture(temp);
            Gui.drawRect(p_148279_2_, p_148279_3_, p_148279_2_ + 32, p_148279_3_ + 32, -1601138544);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int l1 = p_148279_7_ - p_148279_2_;
            i2 = p_148279_8_ - p_148279_3_;

            if (this.func_148309_e())
            {
                if (l1 < 32)
                {
                    Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                }
                else
                {
                    Gui.func_146110_a(p_148279_2_, p_148279_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }
            else
            {
                if (this.func_148308_f())
                {
                    if (l1 < 16)
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (this.func_148314_g())
                {
                    if (l1 < 32 && l1 > 16 && i2 < 16)
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }

                if (this.func_148307_h())
                {
                    if (l1 < 32 && l1 > 16 && i2 > 16)
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    }
                    else
                    {
                        Gui.func_146110_a(p_148279_2_, p_148279_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                }
            } */
        }

        String s = this.getSkinName();
        i2 = this.mc.fontRenderer.getStringWidth(s);

        if (i2 > 157)
        {
            s = this.mc.fontRenderer.trimStringToWidth(s, 157 - this.mc.fontRenderer.getStringWidth("...")) + "...";
        }

        this.mc.fontRenderer.drawStringWithShadow(s, p_148279_2_ + 32 + 2, p_148279_3_ + 1, 16777215);
        List list = this.mc.fontRenderer.listFormattedStringToWidth(this.getSkinDescription(), 157);

        for (int j2 = 0; j2 < 2 && j2 < list.size(); ++j2)
        {
            this.mc.fontRenderer.drawStringWithShadow((String)list.get(j2), p_148279_2_ + 32 + 2, p_148279_3_ + 12 + 10 * j2, 8421504);
        }
    }

    protected String getSkinDescription() {
        return skinSize;
    }

    protected String getSkinName() {
        return this.skinName;
    }

    protected void bindIcon() {
        this.mc.getTextureManager().bindTexture(skinResourceLocation); //bindTexturePackIcon(this.field_148317_a.getTextureManager());
    }

    protected boolean func_148310_d()
    {
        return true;
    }

    protected boolean func_148309_e()
    {
        return !this.previous.hasSkinEntry(this);
    }

    protected boolean func_148308_f()
    {
        return this.previous.hasSkinEntry(this);
    }

    protected boolean func_148314_g()
    {
        List list = this.previous.probablyToRemove(this);
        int i = list.indexOf(this);
        return i > 0 && ((SkinListEntry)list.get(i - 1)).func_148310_d();
    }

    protected boolean func_148307_h()
    {
        List list = this.previous.probablyToRemove(this);
        int i = list.indexOf(this);
        return i >= 0 && i < list.size() - 1 && ((SkinListEntry)list.get(i + 1)).func_148310_d();
    }

    /**
     * Returns true if the mouse has been pressed on this control.
     */
    public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_)
    {
        if (this.func_148310_d() /*&& p_148278_5_ <= 32 */)
        {
            return true;
        }

        return false;
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {}
}