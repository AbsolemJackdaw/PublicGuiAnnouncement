package subaraki.pga.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToTrackingPlayers;
import subaraki.pga.util.ScreenEntry;
import subaraki.pga.util.ScreenPackReader;

public class ScreenData {

    public static final String CLOSE_SCREEN = "close_screen";
    private PlayerEntity player;

    private ScreenEntry viewingScreen;

    public PlayerEntity getPlayer() {

        return player;
    }

    public void setPlayer(PlayerEntity newPlayer) {

        this.player = newPlayer;
    }

    public static ScreenData get(PlayerEntity player) {

        return player.getCapability(ScreenCapability.CAPABILITY, null).orElse(null);
    }

    public void setViewingScreen(String simpleclassname) {

        if (simpleclassname.equals(CLOSE_SCREEN)) {
            this.viewingScreen = null;
            this.cachedResLoc = null;
        } else {
            if (ScreenPackReader.getEntryForSimpleClassName(simpleclassname) != null) {
                this.viewingScreen = ScreenPackReader.getEntryForSimpleClassName(simpleclassname);
            }
        }

        if (player != null) {
            if (!player.world.isRemote)
                NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                        new PacketSendScreenToTrackingPlayers(player.getUniqueID(), simpleclassname));

        }
    }

    public ScreenEntry getViewingScreen() {

        return viewingScreen;
    }

    private ResourceLocation cachedResLoc;

    public ResourceLocation lookupResloc(String simpleclassname) {

        if (viewingScreen != null) {
            if (cachedResLoc == null)
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());
            else if (!cachedResLoc.toString().equals(viewingScreen.getResLoc()))
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());

            return cachedResLoc;
        }

        return null;
    }
}