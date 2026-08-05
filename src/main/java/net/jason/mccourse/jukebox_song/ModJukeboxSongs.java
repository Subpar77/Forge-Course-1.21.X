package net.jason.mccourse.jukebox_song;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {
    public static final ResourceKey<JukeboxSong> BAR_BRAWL = ResourceKey.create(Registries.JUKEBOX_SONG,
            ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "bar_brawl"));

    private ModJukeboxSongs() {

    }
}
