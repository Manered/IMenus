package dev.manere.imenus.item.parser;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import dev.manere.imenus.item.EnchantmentData;
import dev.manere.imenus.item.ItemBuilder;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class YAMLItemSerializer {
    /*
    @NotNull
    public ItemStack deserialize(final @NotNull ConfigurationSection section, final @NotNull TagResolver @NotNull ... resolvers) {
        @NotNull
        final Material type = Objects.requireNonNullElse(Material.matchMaterial(section.getString("type", "grass block")
            .toUpperCase()
            .replaceAll("_", " ")
            .replaceAll("minecraft:", "")
        ), Material.GRASS_BLOCK);

        final ItemBuilder item = ItemBuilder.empty()
            .type(type);

        @Nullable
        final Component displayName = section.getComponent("name", MiniMessage.builder().editTags(builder -> builder.resolvers(resolvers)).build(), null);

        item.name(displayName);

        final List<String> rawLore = section.getStringList("lore");
        final List<Component> lore = new ArrayList<>();

        for (final String line : rawLore) {
            lore.add(MiniMessage.miniMessage().deserialize(line, resolvers));
        }

        item.lore(lore);

        item.unbreakable(section.getBoolean("unbreakable", false));

        if (section.get("custom_model_data", null) instanceof Integer cmd) item.customModelData(cmd);

        final ConfigurationSection enchantmentsSection = section.getConfigurationSection("enchantments");
        if (enchantmentsSection != null) {
            for (final String key : enchantmentsSection.getKeys(false)) {
                final int level = enchantmentsSection.getInt(key, 1);

                final Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).getOrThrow(NamespacedKey.minecraft(key.toLowerCase()));

                item.enchant(EnchantmentData.data(enchantment, level));
            }
        }

        if (section.getBoolean("glow", false)) item.glow();
        if (section.getBoolean("shine", false)) item.glow();

        if (section.getBoolean("hide_flags", false)) item.meta(meta -> meta.addItemFlags(ItemFlag.values()));
        if (section.getBoolean("hide_all_flags", false)) item.meta(meta -> meta.addItemFlags(ItemFlag.values()));
        if (section.getBoolean("flags", false)) item.meta(meta -> meta.addItemFlags(ItemFlag.values()));

        final String skullName = section.getString("skull");

        if (skullName != null && type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD) {
            item.skull(skullName);
        }

        final ConfigurationSection armorTrimSection = section.getConfigurationSection("armor_trim");
        if (armorTrimSection != null) {
            item.meta(ArmorMeta.class, meta -> {
                final String rawMaterial = armorTrimSection.getString("material");
                final String rawPattern = armorTrimSection.getString("pattern");

                if (rawMaterial == null || rawPattern == null) return;

                final TrimMaterial material = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_MATERIAL).get(NamespacedKey.minecraft(rawMaterial.toLowerCase().replaceAll(" ", "_")));
                final TrimPattern pattern = RegistryAccess.registryAccess().getRegistry(RegistryKey.TRIM_PATTERN).get(NamespacedKey.minecraft(rawPattern.toLowerCase().replaceAll(" ", "_")));

                if (material == null || pattern == null) return;

                meta.setTrim(new ArmorTrim(material, pattern));
            });
        }

        final ConfigurationSection armorStandSection = section.getConfigurationSection("armor_stand");
        if (armorStandSection != null && type == Material.ARMOR_STAND) {
            item.meta(ArmorStandMeta.class, meta -> {
                final boolean invisible = armorStandSection.getBoolean("invisible", false);
                final boolean marker = armorStandSection.getBoolean("marker", false);
                final boolean small = armorStandSection.getBoolean("small", false);
                final boolean noBasePlate = armorStandSection.getBoolean("no_base_plate", false);
                final boolean showArms = armorStandSection.getBoolean("show_arms", false);

                meta.setInvisible(invisible);
                meta.setMarker(marker);
                meta.setSmall(small);
                meta.setNoBasePlate(noBasePlate);
                meta.setShowArms(showArms);
            });
        }

        final ConfigurationSection axolotlSection = section.getConfigurationSection("axolotl");
        if (axolotlSection != null && (type == Material.AXOLOTL_BUCKET || type == Material.AXOLOTL_SPAWN_EGG)) {
            item.meta(AxolotlBucketMeta.class, meta -> {
                final String raw = axolotlSection.getString("variant");
                if (raw == null) return;

                for (final Axolotl.Variant variant : Axolotl.Variant.values()) {
                    if (raw.equalsIgnoreCase(variant.name())) {
                        meta.setVariant(variant);
                    }
                }
            });
        }

        final ConfigurationSection blockDataSection = section.getConfigurationSection("block_data");
        if (blockDataSection != null) {
            item.meta(BlockDataMeta.class, meta -> {
                final String rawData = blockDataSection.getString("data");
                if (rawData == null) return;

                try {
                    final BlockData data = Bukkit.createBlockData(type, rawData);
                    meta.setBlockData(data);
                } catch (final Exception ignored) {}
            });
        }

        final ConfigurationSection bookSection = section.getConfigurationSection("book");
        if (bookSection != null && (type == Material.WRITTEN_BOOK || type == Material.WRITABLE_BOOK || type == Material.KNOWLEDGE_BOOK || type == Material.ENCHANTED_BOOK)) {
            item.meta(BookMeta.class, meta -> {
                final String rawTitle = bookSection.getString("title");
                final String rawAuthor = bookSection.getString("author");
                final String rawGeneration = bookSection.getString("generation");

                if (rawTitle != null) {
                    final Component title = MiniMessage.miniMessage().deserialize(rawTitle, resolvers);
                    meta.title(title);
                }

                if (rawAuthor != null) {
                    final Component author = MiniMessage.miniMessage().deserialize(rawAuthor, resolvers);
                    meta.author(author);
                }

                if (rawGeneration != null) {
                    for (final BookMeta.Generation generation : BookMeta.Generation.values()) {
                        if (rawGeneration.equalsIgnoreCase(generation.name().toLowerCase().replaceAll("_", " "))) {
                            meta.setGeneration(generation);
                            break;
                        }
                    }
                }

                final ConfigurationSection pagesSection = bookSection.getConfigurationSection("pages");
                if (pagesSection != null) {
                    final Map<Integer, Component> pages = new ConcurrentHashMap<>();

                    for (final String key : pagesSection.getKeys(false)) {
                        try {
                            final int parsedKey = Integer.parseInt(key);

                            final List<String> list = pagesSection.getStringList(key);
                            final List<Component> components = new ArrayList<>();

                            for (final String line : list) {
                                components.add(MiniMessage.miniMessage().deserialize(line, resolvers));
                            }

                            pages.put(parsedKey, Component.join(JoinConfiguration.newlines(), components));
                        } catch (final Exception ignored) {}
                    }

                    final Book book = meta.pages(pages.values().toArray(new Component[0]));
                }
            });
        }

        final ConfigurationSection bannerSection = section.getConfigurationSection("banner");
        if (bannerSection != null && (type.name().contains("BANNER") || type == Material.SHIELD)) {
            for (final String rawKey : bannerSection.getKeys(false)) {
                final String rawValue = bannerSection.getString(rawKey);
                if (rawValue == null) continue;

                DyeColor color = null;

                for (final DyeColor possible : DyeColor.values()) {
                    if (possible.name().toLowerCase().replaceAll("_", " ").equalsIgnoreCase(rawValue)) {
                        color = possible;
                    }
                }

                if (color == null) continue;

                PatternType pattern = null;

                for (final PatternType possible : RegistryAccess.registryAccess().getRegistry(RegistryKey.BANNER_PATTERN)) {
                    if (possible.key().value().toLowerCase().replaceAll("_", " ").equalsIgnoreCase(rawKey)) {
                        pattern = possible;
                    }
                }

                if (pattern == null) continue;

                if (type.name().contains("BANNER")) {
                    final DyeColor finalColor = color;
                    final PatternType finalPattern = pattern;

                    item.meta(BannerMeta.class, meta -> meta.addPattern(new Pattern(finalColor, finalPattern)));
                } else if (type == Material.SHIELD) {
                    final String rawBaseColor = bannerSection.getString("base_color");

                    DyeColor baseColor = null;

                    for (final DyeColor possible : DyeColor.values()) {
                        if (possible.name().toLowerCase().replaceAll("_", " ").equalsIgnoreCase(rawBaseColor)) {
                            baseColor = possible;
                        }
                    }

                    final DyeColor finalColor = color;
                    final PatternType finalPattern = pattern;
                    final DyeColor finalBaseColor = baseColor;

                    item.meta(ShieldMeta.class, meta -> {
                        meta.setBaseColor(finalBaseColor);
                        meta.addPattern(new Pattern(finalColor, finalPattern));
                    });
                }
            }
        }

        final ConfigurationSection bundleSection = section.getConfigurationSection("bundle");
        if (bundleSection != null) {
            // TODO
            throw new UnsupportedOperationException("Bundles can't be implemented currently.");
        }

        final ConfigurationSection compassSection = section.getConfigurationSection("compass");
        if (compassSection != null && (type == Material.COMPASS || type == Material.RECOVERY_COMPASS)) {
            item.meta(CompassMeta.class, meta -> {
                final boolean lodestoneTracked = compassSection.getBoolean("lodestone_tracked", false);
                final double x = compassSection.getDouble("lodestone.x");
                final double y = compassSection.getDouble("lodestone.y");
                final double z = compassSection.getDouble("lodestone.z");
                final String worldName = compassSection.getString("lodestone.world");

                meta.setLodestoneTracked(lodestoneTracked);

                if (worldName == null) return;

                final World world = Bukkit.getWorld(worldName);

                meta.setLodestone(new Location(world, x,y,z));
            });
        }

        final ConfigurationSection crossbowSection = section.getConfigurationSection("crossbow");
        if (crossbowSection != null && type == Material.CROSSBOW) {
            // TODO
            throw new UnsupportedOperationException("Crossbows can't be implemented currently.");
        }

        final ConfigurationSection damageableSection = section.getConfigurationSection("damagable");
        if (damageableSection != null) {
            item.meta(Damageable.class, damageable -> {
                final int damage = damageableSection.getInt("damage");
                damageable.setDamage(damage);
            });
        }

        final ConfigurationSection enchantmentStorageSection = section.getConfigurationSection("enchantment_storage");
        if (enchantmentStorageSection != null && type == Material.ENCHANTED_BOOK) {
            item.meta(EnchantmentStorageMeta.class, meta -> {
                for (final String key : enchantmentStorageSection.getKeys(false)) {
                    final int level = enchantmentStorageSection.getInt(key, 1);

                    final Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).getOrThrow(NamespacedKey.minecraft(key.toLowerCase()));

                    meta.addStoredEnchant(enchantment, level, true);
                }
            });
        }

        final ConfigurationSection fireworkEffectSection = section.getConfigurationSection("firework_effect");
        if (fireworkEffectSection != null && type == Material.FIREWORK_STAR) {
            item.meta(FireworkEffectMeta.class, meta -> {
                final FireworkEffect effect = fireworkEffectSection.getSerializable("effect", FireworkEffect.class, null);
                meta.setEffect(effect);
            });
        }

        final ConfigurationSection fireworkSection = section.getConfigurationSection("firework");
        if (fireworkSection != null && type == Material.FIREWORK_ROCKET) {
            item.meta(FireworkMeta.class, meta -> {
                final int power = fireworkSection.getInt("power", 1);

                //noinspection unchecked
                final List<FireworkEffect> effects = (List<FireworkEffect>) fireworkSection.getList("effects", new ArrayList<>());

                meta.setPower(power);
                meta.addEffects(effects);
            });
        }

        final ConfigurationSection knowledgeBookSection = section.getConfigurationSection("knowledge_book");
        if (knowledgeBookSection != null && type == Material.KNOWLEDGE_BOOK) {
            item.meta(KnowledgeBookMeta.class, meta -> {
                final List<String> strings = knowledgeBookSection.getStringList("recipes");
                final List<NamespacedKey> keys = new ArrayList<>();

                for (final String string : strings) {
                    final String[] split = string.split(":");

                    if (split.length == 2) {
                        final String key = split[0];
                        final String val = split[1];

                        keys.add(new NamespacedKey(key, val));
                    }
                }

                meta.setRecipes(keys);
            });
        }

        final ConfigurationSection leatherArmorSection = section.getConfigurationSection("leather_armor");
        if (leatherArmorSection != null && List.of(
            Material.LEATHER_BOOTS,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET,
            Material.LEATHER_HORSE_ARMOR,
            Material.WOLF_ARMOR
        ).contains(type)) {
            item.meta(LeatherArmorMeta.class, meta -> meta.setColor(leatherArmorSection.getColor("color")));
        }

        final ConfigurationSection mapSection = section.getConfigurationSection("map");
        if (mapSection != null && (type == Material.MAP || type == Material.FILLED_MAP)) {
            item.meta(MapMeta.class, meta -> {
                meta.setColor(mapSection.getColor("color"));
                if (mapSection.contains("scaling", true)) meta.setScaling(mapSection.getBoolean("scaling"));

                if (mapSection.contains("id", true)) {
                    final MapView view = Bukkit.getMap(mapSection.getInt("id"));
                    if (view != null) meta.setMapView(view);
                }
            });
        }

        final ConfigurationSection musicInstrumentSection = section.getConfigurationSection("music_instrument");
        if (musicInstrumentSection != null) {
            item.meta(MusicInstrumentMeta.class, meta -> {
                final String rawInstrument = musicInstrumentSection.getString("instrument");
                if (rawInstrument != null) meta.setInstrument(
                    RegistryAccess.registryAccess()
                        .getRegistry(RegistryKey.INSTRUMENT)
                        .get(NamespacedKey.minecraft(rawInstrument.replaceAll(" ", "_")))
                );
            });
        }

        final ConfigurationSection ominousBottleSection = section.getConfigurationSection("ominous_bottle");
        if (ominousBottleSection != null) item.meta(OminousBottleMeta.class, meta -> meta.setAmplifier(ominousBottleSection.getInt("amplifier", 1)));

        final ConfigurationSection potionSection = section.getConfigurationSection("potion");
        if (potionSection != null) {
            // TODO
            potionSection.getColor("");
        }

        return item.asItem();
    }

     */
}
