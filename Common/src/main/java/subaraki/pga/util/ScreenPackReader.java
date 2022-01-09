package subaraki.pga.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import subaraki.pga.config.ConfigHandler;
import subaraki.pga.mod.CommonScreenMod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ScreenPackReader extends SimplePreparableReloadListener<ArrayList<JsonObject>> {

    private static final ScreenEntry MISSING_SCREEN = new ScreenEntry("missing.class", CommonScreenMod.MODID + ":textures/gui/missing.png", 0, 0, 0, 0);
    private static HashMap<String, ScreenEntry> mappedScreens = new HashMap<>();

    public static ScreenEntry getEntryForSimpleClassName(String simpleclassname) {

        if (mappedScreens.containsKey(simpleclassname)) {
            return mappedScreens.get(simpleclassname);
        }

        return MISSING_SCREEN;
    }

    @Override
    protected ArrayList<JsonObject> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        ArrayList<JsonObject> theJsonFiles = Lists.newArrayList();
        try {

            Collection<ResourceLocation> jsonfiles = resourceManager.listResources("load_screens", (filename) -> filename.endsWith(".json"));

            List<Resource> jsons = new ArrayList<>();

            for (ResourceLocation resLoc : jsonfiles) {
                jsons.addAll(Minecraft.getInstance().getResourceManager().getResources(resLoc));
            }

            Gson gson = new GsonBuilder().create();

            for (Resource res : jsons) {
                InputStream stream = res.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                JsonElement je = gson.fromJson(reader, JsonElement.class);
                JsonObject json = je.getAsJsonObject();

                if (json.has("screens")) {
                    theJsonFiles.add(json);
                }
            }
        } catch (IOException e) {
            CommonScreenMod.LOG.warn("************************************");
            CommonScreenMod.LOG.warn("!*!*!*!*!");
            CommonScreenMod.LOG.warn("No Screens Detected. You will not be able to use ");
            CommonScreenMod.LOG.warn("the Public Gui Announcement Mod correctly.");
            CommonScreenMod.LOG.warn("Make sure to select or set some in the resourcepack gui !");
            CommonScreenMod.LOG.warn("Or verify your painting json in assets/any_modid/load_screens  !");
            CommonScreenMod.LOG.warn("!*!*!*!*!");
            CommonScreenMod.LOG.warn("************************************");

            e.printStackTrace();
        }
        return theJsonFiles;
    }

    @Override
    protected void apply(ArrayList<JsonObject> o, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        //reload config for people to change values at runtime
        ConfigHandler.reload();
        //also, this shouldnt be here, but i'm not about to make an extra loader only for this line
        if (o != null && !o.isEmpty()) {

            Runnable run = () -> {
                for (JsonObject json : o) {
                    if (json.has("screens")) {
                        JsonArray array = json.getAsJsonArray("screens");
                        for (int i = 0; i < array.size(); i++) {

                            JsonObject jsonObject = array.get(i).getAsJsonObject();

                            String fullName = jsonObject.get("class").getAsString();

                            String path = jsonObject.get("texture").getAsString();

                            int sizeX = 0;
                            int sizeY = 0;
                            int texX = 0;
                            int texY = 0;

                            if (jsonObject.has("size")) {
                                JsonArray list = jsonObject.getAsJsonArray("size");
                                if (list.size() == 2) {
                                    sizeX = list.get(0).getAsInt();
                                    sizeY = list.get(1).getAsInt();
                                }
                            }

                            if (jsonObject.has("texSize")) {
                                JsonArray list = jsonObject.getAsJsonArray("texSize");
                                if (list.size() == 2) {
                                    texX = list.get(0).getAsInt();
                                    texY = list.get(1).getAsInt();
                                }
                            }

                            if (jsonObject.has("fullSize")) {
                                int size = jsonObject.get("fullSize").getAsInt();
                                sizeX = sizeY = texX = texY = size;
                            }

                            ScreenEntry entry = new ScreenEntry(fullName, path, sizeX, sizeY, texX, texY);
                            CommonScreenMod.LOG.info(String.format("Loaded %s for %s : file size %d x %d , tex size %d x %d", entry.getResLoc(), entry.getRefName(),
                                    entry.getTexX(), entry.getTexY(), entry.getSizeX(), entry.getSizeY()));

                            mappedScreens.put(entry.getRefName(), entry);

                        }
                    }
                }

                //ScreenEntry has a hash based on refname, so if we're on fabric, these will defer from the
                //ones being read in below, resulting in a bigger list.
                //in forge, they'll just skip over it
                VanillaMenus.getVanillaMenus().forEach(entry -> {
                    if (!mappedScreens.containsKey(entry.getRefName())) {
                        mappedScreens.put(entry.getRefName(), entry);
                    }
                });
            };
            run.run();
        }
    }

}
