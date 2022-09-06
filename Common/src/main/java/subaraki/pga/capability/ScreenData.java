package subaraki.pga.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.util.ScreenEntry;
import subaraki.pga.util.ScreenPackReader;

public class ScreenData {

    public static final String CLOSE_SCREEN = "close_screen";
    private Player player;
    private ScreenEntry viewingScreen;
    private ResourceLocation cachedResLoc;
    private String serverData;

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player newPlayer) {

        this.player = newPlayer;
    }

    public ScreenEntry getClientScreen() {

        return viewingScreen;
    }

    public void setClientScreen(String simpleclassname) {

        String resolvedName = simpleclassname; //CommonScreenMod.resolve(simpleclassname);

        if (resolvedName.equals(CLOSE_SCREEN)) {
            this.viewingScreen = null;
            this.cachedResLoc = null;
        } else {
            //no need for a null check. screen can be null
            //wrong !!
            //null check needed for gui / container opening differentiation
            //1.18 update : mod compat : screen cannot be null, null instead is 'missing screen'
            //1.19.2 addition : check if the current screen is not the screen send. due to an event mismatch, we used a mixin to fix the issue, but now screen dont get set to 'empty' or 'null' if changed out
            ScreenEntry entry = ScreenPackReader.getEntryForSimpleClassName(resolvedName);
            if (viewingScreen == null || viewingScreen.equals(ScreenPackReader.MISSING_SCREEN) && !entry.equals(ScreenPackReader.MISSING_SCREEN) || !entry.getRefName().equals(simpleclassname)) {
                this.viewingScreen = entry;
            }
        }
    }

    public String getServerData() {

        return serverData == null ? "" : serverData;
    }

    public void setServerData(String ref) {
        //no need to obf here yet. all server data has to be communicated to client, where it will be changed
        this.serverData = ref;
    }

    public ResourceLocation lookupResloc() {

        if (viewingScreen != null) {
            if (cachedResLoc == null) {
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());
            } else if (!cachedResLoc.toString().equals(viewingScreen.getResLoc())) {
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());
            }

            return cachedResLoc;
        }

        return null;
    }


}
