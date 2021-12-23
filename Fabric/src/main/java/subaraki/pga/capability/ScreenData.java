package subaraki.pga.capability;

import dev.onyxstudios.cca.api.v3.component.TransientComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.util.ScreenEntry;
import subaraki.pga.util.ScreenPackReader;

public class ScreenData implements TransientComponent, AutoSyncedComponent {
    
    public static final String CLOSE_SCREEN = "close_screen";
    private Player player;
    private ScreenEntry viewingScreen;
    private ResourceLocation cachedResLoc;
    
    public ScreenData(Player player) {
        
        setPlayer(player);
    }
    
    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        
        return false;
    }
    
    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        
        buf.writeUtf(viewingScreen.getRefName(), viewingScreen.getRefName().length() + 1);
    }
    
    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        
        setViewingScreen(buf.readUtf());
    }
    
    public Player getPlayer() {
        
        return player;
    }
    
    public void setPlayer(Player newPlayer) {
        
        this.player = newPlayer;
    }
    
    public ScreenEntry getViewingScreen() {
        
        return viewingScreen;
    }
    
    public void setViewingScreen(String simpleclassname) {
        
        if(simpleclassname.equals(CLOSE_SCREEN)) {
            this.viewingScreen = null;
            this.cachedResLoc = null;
        } else {
            if(ScreenPackReader.getEntryForSimpleClassName(simpleclassname) != null) {
                this.viewingScreen = ScreenPackReader.getEntryForSimpleClassName(simpleclassname);
            }
        }
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
