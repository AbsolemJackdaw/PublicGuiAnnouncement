package subaraki.pga.util;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.render.layer.LayerScreen;

public class ClientReferences {

    public static Player getClientPlayer()
    {

        return Minecraft.getInstance().player;
    }

    public static Level getClientWorld()
    {

        return Minecraft.getInstance().level;
    }

    public static Player getClientPlayerByUUID(UUID uuid)
    {

        return Minecraft.getInstance().level.getPlayerByUUID(uuid);
    }

    public static void loadLayers()
    {

        String types[] = new String[] { "default", "slim" };
        ScreenMod.LOG.info(Minecraft.getInstance().getEntityRenderDispatcher());
        for (String type : types)
        {
            PlayerRenderer renderer = ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().get(type));
            renderer.addLayer(new LayerScreen(renderer));
        }
    }
}
