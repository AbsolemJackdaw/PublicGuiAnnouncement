package subaraki.pga.util;

public class ScreenEntry {

    private int sizeX = 176;
    private int sizeY = 166;
    private int texX = 255;
    private int texY = 255;

    private String resLoc = "textures/gui/container/inventory.png";
    private String refName;

    public ScreenEntry(String[] args) {

        switch (args.length) {
        case 6:
            this.texY = Integer.valueOf(args[5]);
        case 5:
            this.texX = Integer.valueOf(args[4]);
        case 4:
            this.sizeY = Integer.valueOf(args[3]);
        case 3:
            this.sizeX = Integer.valueOf(args[2]);
        case 2:
            this.resLoc = args[1];
        case 1:
            this.refName = args[0];
            break;
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

}
