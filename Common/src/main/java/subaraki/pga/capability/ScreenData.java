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
        
        if(resolvedName.equals(CLOSE_SCREEN)) {
            this.viewingScreen = null;
            this.cachedResLoc = null;
        } else {
            //no need for a null check. screen can be null
            //wrong !!
            //null check needed for gui / container opening differentiation
            ScreenEntry entry = ScreenPackReader.getEntryForSimpleClassName(resolvedName);
            if(entry != null) {
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
