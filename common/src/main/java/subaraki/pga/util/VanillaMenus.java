package subaraki.pga.util;

import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.ChatOptionsScreen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.inventory.StonecutterMenu;

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
        
        entry = new ScreenEntry(StatsScreen.class.getName(), "publicguiannouncement:textures/gui/stat_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(ShareToLanScreen.class.getName(), "publicguiannouncement:textures/gui/lan_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(SkinCustomizationScreen.class.getName(), "publicguiannouncement:textures/gui/skin_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(VideoSettingsScreen.class.getName(), "publicguiannouncement:textures/gui/video_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(LanguageSelectScreen.class.getName(), "publicguiannouncement:textures/gui/language_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(PackSelectionScreen.class.getName(), "publicguiannouncement:textures/gui/resource_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(SoundOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/music_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(ControlsScreen.class.getName(), "publicguiannouncement:textures/gui/controls_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(ChatOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/chat_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(AccessibilityOptionsScreen.class.getName(), "publicguiannouncement:textures/gui/accesibility_menu.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
        
        entry = new ScreenEntry(MouseSettingsScreen.class.getName(), "publicguiannouncement:textures/gui/mouse_settings.png", 255, 255, 255, 255);
        vanillaMenus.add(entry);
    }
}
