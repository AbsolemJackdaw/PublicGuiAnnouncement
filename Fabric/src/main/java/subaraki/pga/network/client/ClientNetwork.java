package subaraki.pga.network.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import subaraki.pga.capability.FabricScreenData;
import subaraki.pga.mod.CommonScreenMod;

import java.util.UUID;

public class ClientNetwork {
    
    public static final ResourceLocation UPDATE_SELF = new ResourceLocation(CommonScreenMod.MODID, "update_self");
    public static final ResourceLocation UPDATE_TRACKING = new ResourceLocation(CommonScreenMod.MODID, "update_tracking");
    public static final ResourceLocation UPDATE_TRACKING_W_ID = new ResourceLocation(CommonScreenMod.MODID, "update_tracking_w_id");
    
    public static void register() {
        
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_SELF, (client, handler, buf, responseSender) -> {
            
            String ref = buf.readUtf();
            client.execute(() -> {
                FabricScreenData.get(client.player).ifPresent(data -> {
                    data.setClientScreen(ref);
                });
            });
        });
        
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_TRACKING, (client, handler, buf, responseSender) -> {
            String ref = buf.readUtf();
            UUID otherUUID = buf.readUUID();
            client.execute(() -> {
                if(client.level != null) {
                    Player other = client.level.getPlayerByUUID(otherUUID);
                    if(other != null) {
                        FabricScreenData.get(other).ifPresent(data -> {
                            data.setClientScreen(ref);
                        });
                    }
                }
            });
        });
        
        ClientPlayNetworking.registerGlobalReceiver(UPDATE_TRACKING_W_ID, (client, handler, buf, responseSender) -> {
            String ref = buf.readUtf();
            int id = buf.readInt();
            client.execute(() -> {
                if(client.level != null) {
                    Entity entity = client.level.getEntity(id);
                    if(entity instanceof LocalPlayer other) {
                        FabricScreenData.get(other).ifPresent(data -> {
                            data.setClientScreen(ref);
                        });
                    }
                }
            });
        });
    }
    
}
