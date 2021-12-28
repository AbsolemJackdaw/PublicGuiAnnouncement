package subaraki.pga.util;

import java.util.Objects;

public class ScreenEntry {
    
    private int sizeX = 175;
    private int sizeY = 165;
    private int texX = 255;
    private int texY = 255;
    
    private String resLoc = "textures/gui/container/inventory.png";
    private String refName = "net.minecraft.client.gui.screen.inventory.InventoryScreen";
    
    public ScreenEntry(String refName, String resLoc, int sizeX, int sizeY, int texX, int texY) {
        
        //returns the intermediate, mapped, mojmap (normally) name of the given bytcodeclass for fabric.
        //forge will just return the same string
        // this.refName = CommonScreenMod.resolve(refName);
        //PS : this experiment failed. i need to load in the mojmaps at run time for this to work.
        //decided on a quickfix and hardcoded the vanilla values, to which this is the only issue.
        
        this.refName = refName;
        this.resLoc = resLoc;
        
        if(sizeX > 0) {
            this.sizeX = sizeX;
        }
        if(sizeY > 0) {
            this.sizeY = sizeY;
        }
        
        if(texX > 0) {
            this.texX = texX;
        }
        if(texY > 0) {
            this.texY = texY;
        }
    }
    
    public int getSizeX() {
        
        return this.sizeX;
    }
    
    public int getSizeY() {
        
        return this.sizeY;
    }
    
    public int getTexX() {
        
        return texX;
    }
    
    public int getTexY() {
        
        return texY;
    }
    
    public String getRefName() {
        
        return this.refName;
    }
    
    public String getResLoc() {
        
        return this.resLoc;
    }
    
    @Override
    public boolean equals(Object o) {
        //only on ref name. cant register two textures to one screen
        return o instanceof ScreenEntry other && other.getRefName().equals(refName);
    }
    
    @Override
    public int hashCode() {
        //only on ref name. cant register two textures to one screen
        return Objects.hash(refName);
    }
    
}
