package subaraki.pga.util;

import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.gui.screens.options.*;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.world.inventory.*;

import java.util.ArrayList;
import java.util.List;

public class VanillaMenus {

    public static List<ScreenEntry> getVanillaMenus() {

        return vanillaMenus;
    }

    private static List<ScreenEntry> vanillaMenus = new ArrayList<>();

    static {
        ScreenEntry entry;
        entry = new ScreenEntry(InventoryScreen.class.getName(), "minecraft:textures/gui/container/inventory.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(CreativeModeInventoryScreen.class.getName(), "minecraft:textures/gui/container/inventory.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(LoomMenu.class.getName(), "minecraft:textures/gui/container/loom.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(InventoryScreen.class.getName(), "minecraft:textures/gui/container/inventory.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(SmokerMenu.class.getName(), "minecraft:textures/gui/container/smoker.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ShulkerBoxMenu.class.getName(), "minecraft:textures/gui/container/shulker_box.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(StonecutterMenu.class.getName(), "minecraft:textures/gui/container/stonecutter.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(HorseInventoryMenu.class.getName(), "minecraft:textures/gui/container/horse.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(AnvilMenu.class.getName(), "minecraft:textures/gui/container/anvil.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(GrindstoneMenu.class.getName(), "minecraft:textures/gui/container/grindstone.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(FurnaceMenu.class.getName(), "minecraft:textures/gui/container/furnace.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(EnchantmentMenu.class.getName(), "minecraft:textures/gui/container/enchanting_table.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(DispenserMenu.class.getName(), "minecraft:textures/gui/container/dispenser.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(CraftingScreen.class.getName(), "minecraft:textures/gui/container/crafting_table.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(CartographyTableMenu.class.getName(), "minecraft:textures/gui/container/cartography_table.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(BrewingStandMenu.class.getName(), "minecraft:textures/gui/container/brewing_stand.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(BlastFurnaceMenu.class.getName(), "minecraft:textures/gui/container/blast_furnace.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ChatScreen.class.getName(), "publicguiannouncement:textures/gui/chat.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(SmithingMenu.class.getName(), "minecraft:textures/gui/container/smithing.png", 0, 0, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ChestMenu.class.getName(), "minecraft:textures/gui/container/generic_54.png", 176, 222, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(HopperMenu.class.getName(), "minecraft:textures/gui/container/hopper.png", 176, 133, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(BeaconMenu.class.getName(), "minecraft:textures/gui/container/beacon.png", 230, 216, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(BookViewScreen.class.getName(), "minecraft:textures/gui/book.png", 166, 181, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(LecternMenu.class.getName(), "minecraft:textures/gui/book.png", 166, 181, 0, 0);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(MerchantMenu.class.getName(), "minecraft:textures/gui/container/villager2.png", 276, 166, 512, 256);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(SignEditScreen.class.getName(), "publicguiannouncement:textures/gui/sign_edit.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(PauseScreen.class.getName(), "publicguiannouncement:textures/gui/pause_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(AdvancementsScreen.class.getName(), "publicguiannouncement:textures/gui/advancements_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ConfirmLinkScreen.class.getName(), "publicguiannouncement:textures/gui/link_ext_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(OptionsScreen.class.getName(), "publicguiannouncement:textures/gui/options_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(StatsScreen.class.getName(), "publicguiannouncement:textures/gui/stat_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ShareToLanScreen.class.getName(), "publicguiannouncement:textures/gui/lan_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(SkinCustomizationScreen.class.getName(), "publicguiannouncement:textures/gui/skin_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(VideoSettingsScreen.class.getName(), "publicguiannouncement:textures/gui/video_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(LanguageSelectScreen.class.getName(), "publicguiannouncement:textures/gui/language_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(PackSelectionScreen.class.getName(), "publicguiannouncement:textures/gui/resource_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(SoundOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/music_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ControlsScreen.class.getName(), "publicguiannouncement:textures/gui/controls_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(ChatOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/chat_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(AccessibilityOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/accesibility_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(MouseSettingsScreen.class.getName(), "publicguiannouncement:textures/gui/mouse_settings.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(KeyBindsScreen.class.getName(), "publicguiannouncement:textures/gui/keybinds_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(WinScreen.class.getName(), "publicguiannouncement:textures/gui/win_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

        entry = new ScreenEntry(CreditsAndAttributionScreen.class.getName(), "publicguiannouncement:textures/gui/creditsandattribution_menu.png", 255, 144, 255, 144);
        vanillaMenus.add(entry);

    }
}
