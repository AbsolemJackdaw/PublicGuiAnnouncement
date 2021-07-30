package subaraki.pga.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.pga.mod.ScreenMod;

@EventBusSubscriber(modid = ScreenMod.MODID, bus = Bus.FORGE)
public class ScreenPackReader {

    private static HashMap<String, ScreenEntry> mappedScreens = new HashMap<>();

    @SubscribeEvent
    public static void registerreloadListener(AddReloadListenerEvent event)
    {

        event.addListener((ResourceManagerReloadListener) ((resourceManager) -> {
            loadFromJson();
        }));

    }

    public ScreenPackReader init()
    {

        loadFromJson();
        return this;
    }

    private static void loadFromJson()
    {

        try
        {

            Collection<ResourceLocation> jsonfiles = Minecraft.getInstance().getResourceManager().listResources("load_screens", (filename) -> {
                return filename.endsWith(".json");
            });

            List<Resource> jsons = new ArrayList<Resource>();

            for (ResourceLocation resLoc : jsonfiles)
            {
                jsons.addAll(Minecraft.getInstance().getResourceManager().getResources(resLoc));
            }

            Gson gson = new GsonBuilder().create();

            for (Resource res : jsons)
            {
                InputStream stream = res.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                JsonElement je = gson.fromJson(reader, JsonElement.class);
                JsonObject json = je.getAsJsonObject();

                if (json.has("screens"))
                {
                    JsonArray array = json.getAsJsonArray("screens");
                    for (int i = 0; i < array.size(); i++)
                    {

                        JsonObject jsonObject = array.get(i).getAsJsonObject();

                        String fullName = jsonObject.get("class").getAsString();

                        String path = jsonObject.get("texture").getAsString();

                        int sizeX = 0;
                        int sizeY = 0;
                        int texX = 0;
                        int texY = 0;

                        if (jsonObject.has("size"))
                        {
                            JsonArray list = jsonObject.getAsJsonArray("size");
                            if (list.size() == 2)
                            {
                                sizeX = list.get(0).getAsInt();
                                sizeY = list.get(1).getAsInt();
                            }
                        }

                        if (jsonObject.has("texSize"))
                        {
                            JsonArray list = jsonObject.getAsJsonArray("texSize");
                            if (list.size() == 2)
                            {
                                texX = list.get(0).getAsInt();
                                texY = list.get(1).getAsInt();
                            }
                        }

                        if (jsonObject.has("fullSize"))
                        {
                            int size = jsonObject.get("fullSize").getAsInt();
                            sizeX = sizeY = texX = texY = size;
                        }

                        ScreenEntry entry = new ScreenEntry(fullName, path, sizeX, sizeY, texX, texY);
                        ScreenMod.LOG.debug(String.format("Loaded %s for %s : file size %d x %d , tex size %d x %d", entry.getResLoc(), entry.getRefName(),
                                entry.getTexX(), entry.getTexY(), entry.getSizeX(), entry.getSizeY()));
                        mappedScreens.put(entry.getRefName(), entry);

                    }
                }
            }
        }
        catch (IOException e)
        {
            ScreenMod.LOG.warn("************************************");
            ScreenMod.LOG.warn("!*!*!*!*!");
            ScreenMod.LOG.warn("No Screens Detected. You will not be able to use ");
            ScreenMod.LOG.warn("the Public Gui Announcement Mod correctly.");
            ScreenMod.LOG.warn("Make sure to select or set some in the resourcepack gui !");
            ScreenMod.LOG.warn("Or verify your painting json in assets/any_modid/load_screens  !");
            ScreenMod.LOG.warn("!*!*!*!*!");
            ScreenMod.LOG.warn("************************************");

            e.printStackTrace();
        }
    }

    public static ScreenEntry getEntryForSimpleClassName(String simpleclassname)
    {

        if (mappedScreens.containsKey(simpleclassname))
            return mappedScreens.get(simpleclassname);

        return null;
    }
}
