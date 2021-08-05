package subaraki.pga.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ClientReferences {

    public static Player getClientPlayer() {
        if (Minecraft.getInstance() == null)
            return null;

        return Minecraft.getInstance().player;
    }

    public static Level getClientWorld() {
        if (Minecraft.getInstance() == null)
            return null;

        return Minecraft.getInstance().level;
    }

    public static Player getClientPlayerByUUID(UUID uuid) {

        return Minecraft.getInstance().level.getPlayerByUUID(uuid);
    }

}
