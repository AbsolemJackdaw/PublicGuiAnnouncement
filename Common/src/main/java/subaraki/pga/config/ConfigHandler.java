package subaraki.pga.config;


import subaraki.pga.mod.CommonScreenMod;

public class ConfigHandler {

    // using the lambda specified as the provider.
    private static SimpleConfig CONFIG = load();
    public static final boolean shouldRenderThink = CONFIG.getOrDefault("use.think.bubble", false);

    // Custom config provider, returns the default config content
    // if the custom provider is not specified SimpleConfig will create an empty file instead
    private static String provider(String filename) {
        StringBuilder builder = new StringBuilder();
        builder.append("#Wether to render the PGA in front of the player's face (default : false) or as a think bubble (true).\n");
        builder.append("use.think.bubble=false\n");
        builder.append("\n\n\n\n#This config can be reloaded in game after a change with F3+T");

        return builder.toString();
    }    // Load config 'config.properties', if it isn't present create one

    public static void reload() {
        CONFIG = load();
    }

    private static SimpleConfig load() {
        return SimpleConfig.of(CommonScreenMod.MODID).provider(ConfigHandler::provider).request();
    }

    public static boolean renderDefault() {
        return !CONFIG.getOrDefault("use.think.bubble", false);
    }


}
