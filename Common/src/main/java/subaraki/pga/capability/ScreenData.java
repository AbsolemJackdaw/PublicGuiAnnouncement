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
        
        if(simpleclassname.equals(CLOSE_SCREEN)) {
            this.viewingScreen = null;
            this.cachedResLoc = null;
        } else if(ScreenPackReader.getEntryForSimpleClassName(simpleclassname) != null) {
            this.viewingScreen = ScreenPackReader.getEntryForSimpleClassName(simpleclassname);
        }
    }
    
    public String getServerData() {
        
        return serverData == null ? "" : serverData;
    }
    
    public void setServerData(String ref) {
        
        this.serverData = ref;
    }
    
    public ResourceLocation lookupResloc() {
        
        if(viewingScreen != null) {
            if(cachedResLoc == null) {
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());
            } else if(!cachedResLoc.toString().equals(viewingScreen.getResLoc())) {
                cachedResLoc = new ResourceLocation(viewingScreen.getResLoc());
            }
            
            return cachedResLoc;
        }
        
        return null;
    }
    
    
}
