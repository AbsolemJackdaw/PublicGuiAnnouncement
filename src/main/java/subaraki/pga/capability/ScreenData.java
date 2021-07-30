package subaraki.pga.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import subaraki.pga.mod.ScreenMod;
import subaraki.pga.network.NetworkHandler;
import subaraki.pga.network.packet_server.PacketSendScreenToTrackingPlayers;
import subaraki.pga.util.ScreenEntry;
import subaraki.pga.util.ScreenPackReader;

public class ScreenData {

    public static final String CLOSE_SCREEN = "close_screen";
    private Player player;

    private ScreenEntry viewingScreen;

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player newPlayer) {

        this.player = newPlayer;
    }

    public static LazyOptional<ScreenData> get(Player player) {

        return player.getCapability(ScreenCapability.CAPABILITY, null);
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
            if (!player.level.isClientSide)
                NetworkHandler.NETWORK.send(PacketDistributor.TRACKING_ENTITY.with(() -> player),
                        new PacketSendScreenToTrackingPlayers(player.getUUID(), simpleclassname));

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