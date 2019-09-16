package subaraki.pga.util;

public class ScreenEntry {

    private int sizeX = 175;
    private int sizeY = 165;
    private int texX = 255;
    private int texY = 255;

    private String resLoc = "textures/gui/container/inventory.png";
    private String refName;

    public ScreenEntry(String refName, String resLoc, int sizeX, int sizeY, int texX, int texY) {
        
        this.refName = refName;
        this.resLoc = resLoc;
        
        if(sizeX > 0)
            this.sizeX = sizeX;
        if(sizeY > 0)
            this.sizeY = sizeY;
        
        if(texX > 0)
            this.texX = texX;
        if(texY > 0)
            this.texY = texY;
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

}
