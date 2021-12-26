package subaraki.pga.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import subaraki.pga.mod.CommonScreenMod;

public class ScreenCapability implements ICapabilitySerializable<CompoundTag> {
    
    /**
     * Unique key to identify the attached provider from others
     */
    public static final ResourceLocation KEY = new ResourceLocation(CommonScreenMod.MODID, "screenviewmod");
    
    public static Capability<ForgeScreenData> CAPABILITY = CapabilityManager.get(new CapabilityToken<ForgeScreenData>() {
    });
    /**
     * The instance that we are providing
     */
    final ForgeScreenData data = new ForgeScreenData();
    
    /**
     * Gets called before world is initiated. player.worldObj will return null here
     * !
     */
    public ScreenCapability(Player player) {
        
        data.setPlayer(player);
    }
    
    @Override
    public CompoundTag serializeNBT() {
        
        return new CompoundTag();
    }
    
    @Override
    public void deserializeNBT(CompoundTag nbt) {
    
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    
        if(cap == ScreenCapability.CAPABILITY) {
            return (LazyOptional<T>) LazyOptional.of(this::getImpl);
        }
        
        return LazyOptional.empty();
    }
    
    private ForgeScreenData getImpl() {
        
        if(data != null) {
            return data;
        }
        return new ForgeScreenData();
    }
    
}
