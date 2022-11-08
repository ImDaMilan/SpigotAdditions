package com.imdamilan.spigotadditions.serealize;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemSerealizer implements JSONSerealizer<ItemStack> {

    @Override
    public String toJson(ItemStack item) {
        Gson gson = new Gson();
        JsonObject itemJSON = new JsonObject();
        itemJSON.addProperty("type", item.getType().name());
        itemJSON.addProperty("amount", item.getAmount());
        if (item.hasItemMeta() && item.getItemMeta() != null) {
            JsonObject itemMetaJSON = new JsonObject();
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                itemMetaJSON.addProperty("name", meta.getDisplayName());
            }
            if (meta.hasLore() && meta.getLore() != null) {
                JsonArray lore = new JsonArray();
                meta.getLore().forEach(lore::add);
                itemMetaJSON.add("lore", lore);
            }
            if (meta.hasEnchants() && !meta.getEnchants().isEmpty()) {
                JsonArray enchants = new JsonArray();
                meta.getEnchants().forEach((enchantment, level) -> enchants.add(new JsonPrimitive(enchantment.getKey().getKey() + ":" + level)));
                itemMetaJSON.add("enchants", enchants);
            }
            if (!meta.getItemFlags().isEmpty()) {
                JsonArray flags = new JsonArray();
                meta.getItemFlags().forEach(flag -> flags.add(new JsonPrimitive(flag.name())));
                itemMetaJSON.add("flags", flags);
            }
            if (meta.hasCustomModelData()) {
                itemMetaJSON.addProperty("modeldata", meta.getCustomModelData());
            }
            if (meta.hasLocalizedName()) {
                itemMetaJSON.addProperty("localname", meta.getLocalizedName());
            }
            itemMetaJSON.addProperty("unbreakable", meta.isUnbreakable());
            if (meta.hasAttributeModifiers() && meta.getAttributeModifiers() != null && !meta.getAttributeModifiers().isEmpty()) {
                JsonArray attributes = new JsonArray();
                meta.getAttributeModifiers().forEach((attribute, modifier) -> {
                    JsonObject attributeJSON = new JsonObject();
                    attributeJSON.addProperty("name", attribute.name());
                    attributeJSON.addProperty("operation", modifier.getOperation().name());
                    attributeJSON.addProperty("amount", modifier.getAmount());
                    attributeJSON.addProperty("uuid", modifier.getUniqueId().toString());
                    if (modifier.getSlot() != null) {
                        attributeJSON.addProperty("slot", modifier.getSlot().name());
                    }
                    attributes.add(attributeJSON);
                });
                itemMetaJSON.add("attributes", attributes);
            }
            if (!meta.getPersistentDataContainer().isEmpty()) {
                JsonArray persistentData = new JsonArray();
                meta.getPersistentDataContainer().getKeys().forEach(key -> {
                    JsonObject data = new JsonObject();
                    data.addProperty("key", key.getKey());
                    data.addProperty("namespace", key.getNamespace());
                    data.addProperty("value", meta.getPersistentDataContainer().get(key, PersistentDataType.STRING));
                    persistentData.add(data);
                });
                itemMetaJSON.add("persistentdata", persistentData);
            }
            if (meta instanceof Damageable) {
                itemMetaJSON.addProperty("durability", ((Damageable) meta).getDamage());
            }
            if (meta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
                JsonObject leatherArmorMetaJSON = new JsonObject();
                leatherArmorMetaJSON.addProperty("color", leatherArmorMeta.getColor().asRGB());
                itemMetaJSON.add("leatherarmor", leatherArmorMetaJSON);
            }
            if (meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                JsonObject skullMetaJSON = new JsonObject();
                if (skullMeta.hasOwner() && skullMeta.getOwningPlayer() != null) {
                    skullMetaJSON.addProperty("owner", skullMeta.getOwningPlayer().getUniqueId().toString());
                }
                itemMetaJSON.add("skull", skullMetaJSON);
            }
            if (meta instanceof BannerMeta) {
                BannerMeta bannerMeta = (BannerMeta) meta;
                JsonObject bannerMetaJSON = new JsonObject();
                if (!bannerMeta.getPatterns().isEmpty()) {
                    JsonArray patterns = new JsonArray();
                    bannerMeta.getPatterns().forEach(pattern -> {
                        JsonObject patternJSON = new JsonObject();
                        patternJSON.addProperty("color", pattern.getColor().name());
                        patternJSON.addProperty("pattern", pattern.getPattern().name());
                        patterns.add(patternJSON);
                    });
                    bannerMetaJSON.add("patterns", patterns);
                }
                itemMetaJSON.add("banner", bannerMetaJSON);
            }
            if (meta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) meta;
                JsonObject bookMetaJSON = new JsonObject();
                if (bookMeta.hasAuthor()) {
                    bookMetaJSON.addProperty("author", bookMeta.getAuthor());
                }
                if (bookMeta.hasTitle()) {
                    bookMetaJSON.addProperty("title", bookMeta.getTitle());
                }
                if (bookMeta.hasPages() && !bookMeta.getPages().isEmpty()) {
                    JsonArray pages = new JsonArray();
                    bookMeta.getPages().forEach(pages::add);
                    bookMetaJSON.add("pages", pages);
                }
                itemMetaJSON.add("book", bookMetaJSON);
            }
            if (meta instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) meta;
                JsonObject enchantmentStorageMetaJSON = new JsonObject();
                if (!enchantmentStorageMeta.getStoredEnchants().isEmpty()) {
                    JsonArray enchants = new JsonArray();
                    enchantmentStorageMeta.getStoredEnchants().forEach((enchantment, level) -> enchants.add(new JsonPrimitive(enchantment.getKey().getKey() + ":" + level)));
                    enchantmentStorageMetaJSON.add("enchants", enchants);
                }
                itemMetaJSON.add("enchantmentstorage", enchantmentStorageMetaJSON);
            }
            if (meta instanceof FireworkMeta) {
                FireworkMeta fireworkMeta = (FireworkMeta) meta;
                JsonObject fireworkMetaJSON = new JsonObject();
                if (fireworkMeta.hasEffects() && !fireworkMeta.getEffects().isEmpty()) {
                    JsonArray effects = new JsonArray();
                    fireworkMeta.getEffects().forEach(effect -> {
                        JsonObject effectJSON = new JsonObject();
                        effectJSON.addProperty("flicker", effect.hasFlicker());
                        effectJSON.addProperty("trail", effect.hasTrail());
                        effectJSON.addProperty("type", effect.getType().name());
                        JsonArray colors = new JsonArray();
                        effect.getColors().forEach(color -> colors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
                        effectJSON.add("colors", colors);
                        JsonArray fadeColors = new JsonArray();
                        effect.getFadeColors().forEach(color -> fadeColors.add(new JsonPrimitive(Integer.toHexString(color.asRGB()))));
                        effectJSON.add("fadecolors", fadeColors);
                        effects.add(effectJSON);
                    });
                    fireworkMetaJSON.add("effects", effects);
                }
                fireworkMetaJSON.addProperty("power", fireworkMeta.getPower());
                itemMetaJSON.add("firework", fireworkMetaJSON);
            }
            if (meta instanceof MapMeta) {
                MapMeta mapMeta = (MapMeta) meta;
                JsonObject mapMetaJSON = new JsonObject();
                if (mapMeta.hasLocationName()) {
                    mapMetaJSON.addProperty("locationname", mapMeta.getLocationName());
                }
                if (mapMeta.hasMapView() && mapMeta.getMapView() != null) {
                    mapMetaJSON.addProperty("mapview", mapMeta.getMapView().getId());
                }
                mapMetaJSON.addProperty("scaling", mapMeta.isScaling());
                itemMetaJSON.add("map", mapMetaJSON);
            }
            if (meta instanceof PotionMeta) {
                PotionMeta potionMeta = (PotionMeta) meta;
                JsonObject potionMetaJSON = new JsonObject();
                if (potionMeta.hasCustomEffects() && !potionMeta.getCustomEffects().isEmpty()) {
                    JsonArray effects = new JsonArray();
                    potionMeta.getCustomEffects().forEach(effect -> {
                        JsonObject effectJSON = new JsonObject();
                        effectJSON.addProperty("amplifier", effect.getAmplifier());
                        effectJSON.addProperty("duration", effect.getDuration());
                        effectJSON.addProperty("type", effect.getType().getName());
                        effectJSON.addProperty("ambient", effect.isAmbient());
                        effectJSON.addProperty("particles", effect.hasParticles());
                        effectJSON.addProperty("icon", effect.hasIcon());
                        effects.add(effectJSON);
                    });
                    potionMetaJSON.add("effects", effects);
                }
                potionMetaJSON.addProperty("color", potionMeta.getColor() != null ? Integer.toHexString(potionMeta.getColor().asRGB()) : null);
                potionMetaJSON.addProperty("base", potionMeta.getBasePotionData().getType().name());
                potionMetaJSON.addProperty("extended", potionMeta.getBasePotionData().isExtended());
                potionMetaJSON.addProperty("upgraded", potionMeta.getBasePotionData().isUpgraded());
                potionMetaJSON.addProperty("type", potionMeta.getBasePotionData().getType().name());
                itemMetaJSON.add("potion", potionMetaJSON);
            }
            itemJSON.add("meta", itemMetaJSON);
        }
        return gson.toJson(itemJSON);
    }

    @Override
    public ItemStack fromJson(String string, Plugin plugin) {
        Gson gson = new Gson();
        JsonObject itemJSON = gson.fromJson(string, JsonObject.class);
        ItemStack item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(itemJSON.get("type").getAsString())), itemJSON.get("amount").getAsInt());
        if (itemJSON.has("meta")) {
            JsonObject itemMetaJSON = itemJSON.get("meta").getAsJsonObject();
            ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
            assert meta != null;
            if (itemMetaJSON.has("name")) {
                meta.setDisplayName(itemMetaJSON.get("name").getAsString());
            }
            if (itemMetaJSON.has("lore")) {
                JsonArray lore = itemMetaJSON.get("lore").getAsJsonArray();
                List<String> loreList = new ArrayList<>();
                lore.forEach(line -> loreList.add(line.getAsString()));
                meta.setLore(loreList);
            }
            if (itemMetaJSON.has("enchants")) {
                JsonArray enchants = itemMetaJSON.get("enchants").getAsJsonArray();
                enchants.forEach(enchant -> {
                    String[] enchantSplit = enchant.getAsString().split(":");
                    meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(enchantSplit[0]))), Integer.parseInt(enchantSplit[1]), true);
                });
            }
            if (itemMetaJSON.has("flags")) {
                JsonArray flags = itemMetaJSON.get("flags").getAsJsonArray();
                flags.forEach(flag -> meta.addItemFlags(ItemFlag.valueOf(flag.getAsString())));
            }
            if (itemMetaJSON.has("unbreakable")) {
                meta.setUnbreakable(itemMetaJSON.get("unbreakable").getAsBoolean());
            }
            if (itemMetaJSON.has("modeldata")) {
                meta.setCustomModelData(itemMetaJSON.get("modeldata").getAsInt());
            }
            if (itemMetaJSON.has("persistentdata")) {
                JsonArray persistentData = itemMetaJSON.get("persistentdata").getAsJsonArray();
                persistentData.forEach(data -> {
                    JsonObject dataJSON = data.getAsJsonObject();
                    NamespacedKey key = new NamespacedKey(plugin, dataJSON.get("key").getAsString());
                    String value = dataJSON.get("value").getAsString();
                    meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
                });
            }
            if (itemMetaJSON.has("durability")) {
                ((Damageable) meta).setDamage(itemMetaJSON.get("durability").getAsInt());
            }
            if (itemMetaJSON.has("skull")) {
                JsonObject skullMetaJSON = itemMetaJSON.get("skull").getAsJsonObject();
                if (skullMetaJSON.has("owner")) {
                    ((SkullMeta) meta).setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(skullMetaJSON.get("owner").getAsString())));
                }
            }
            if (itemMetaJSON.has("banner")) {
                JsonObject bannerMetaJSON = itemMetaJSON.get("banner").getAsJsonObject();
                if (bannerMetaJSON.has("patterns")) {
                    JsonArray patterns = bannerMetaJSON.get("patterns").getAsJsonArray();
                    patterns.forEach(pattern -> {
                        JsonObject patternJSON = pattern.getAsJsonObject();
                        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.valueOf(patternJSON.get("color").getAsString()), PatternType.valueOf(patternJSON.get("pattern").getAsString())));
                    });
                }
            }
            if (itemMetaJSON.has("book")) {
                JsonObject bookMetaJSON = itemMetaJSON.get("book").getAsJsonObject();
                if (bookMetaJSON.has("author")) {
                    ((BookMeta) meta).setAuthor(bookMetaJSON.get("author").getAsString());
                }
                if (bookMetaJSON.has("title")) {
                    ((BookMeta) meta).setTitle(bookMetaJSON.get("title").getAsString());
                }
                if (bookMetaJSON.has("pages")) {
                    JsonArray pages = bookMetaJSON.get("pages").getAsJsonArray();
                    List<String> pagesList = new ArrayList<>();
                    pages.forEach(page -> pagesList.add(page.getAsString()));
                    ((BookMeta) meta).setPages(pagesList);
                }
            }
            if (itemMetaJSON.has("firework")) {
                JsonObject fireworkMetaJSON = itemMetaJSON.get("firework").getAsJsonObject();
                if (fireworkMetaJSON.has("power")) {
                    ((FireworkMeta) meta).setPower(fireworkMetaJSON.get("power").getAsInt());
                }
                if (fireworkMetaJSON.has("effects")) {
                    JsonArray effects = fireworkMetaJSON.get("effects").getAsJsonArray();
                    effects.forEach(effect -> {
                        JsonObject effectJSON = effect.getAsJsonObject();
                        FireworkEffect.Builder builder = FireworkEffect.builder();
                        if (effectJSON.has("flicker")) {
                            builder.flicker(effectJSON.get("flicker").getAsBoolean());
                        }
                        if (effectJSON.has("trail")) {
                            builder.flicker(effectJSON.get("trail").getAsBoolean());
                        }
                        if (effectJSON.has("type")) {
                            builder.with(FireworkEffect.Type.valueOf(effectJSON.get("type").getAsString()));
                        }
                        if (effectJSON.has("colors")) {
                            JsonArray colors = effectJSON.get("colors").getAsJsonArray();
                            colors.forEach(color -> builder.withColor(Color.fromRGB(Integer.parseInt(color.getAsString(), 16))));
                        }
                        if (effectJSON.has("fadecolors")) {
                            JsonArray fade = effectJSON.get("fadecolors").getAsJsonArray();
                            fade.forEach(color -> builder.withFade(Color.fromRGB(Integer.parseInt(color.getAsString(), 16))));
                        }
                        ((FireworkMeta) meta).addEffect(builder.build());
                    });
                }
            }
            if (itemMetaJSON.has("leatherarmor")) {
                JsonObject leatherArmorMetaJSON = itemMetaJSON.get("leatherarmor").getAsJsonObject();
                if (leatherArmorMetaJSON.has("color")) {
                    ((LeatherArmorMeta) meta).setColor(Color.fromRGB(Integer.parseInt(leatherArmorMetaJSON.get("color").getAsString(), 16)));
                }
            }
            if (itemMetaJSON.has("map")) {
                JsonObject mapMetaJSON = itemMetaJSON.get("map").getAsJsonObject();
                if (mapMetaJSON.has("locationname")) {
                    ((MapMeta) meta).setLocationName(mapMetaJSON.get("locationname").getAsString());
                }
                if (mapMetaJSON.has("scaling")) {
                    ((MapMeta) meta).setScaling(mapMetaJSON.get("scaling").getAsBoolean());
                }
                if (mapMetaJSON.has("mapview")) {
                    ((MapMeta) meta).setMapView(Bukkit.getMap(mapMetaJSON.get("mapview").getAsInt()));
                }
            }
            if (itemMetaJSON.has("potion")) {
                JsonObject potionMetaJSON = itemMetaJSON.get("potion").getAsJsonObject();
                if (potionMetaJSON.has("extended") && potionMetaJSON.has("upgraded") && potionMetaJSON.has("type")) {
                    ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.valueOf(potionMetaJSON.get("type").getAsString()), potionMetaJSON.get("extended").getAsBoolean(), potionMetaJSON.get("upgraded").getAsBoolean()));
                }
                if (potionMetaJSON.has("effects")) {
                    JsonArray customEffects = potionMetaJSON.get("effects").getAsJsonArray();
                    customEffects.forEach(customEffect -> {
                        JsonObject customEffectJSON = customEffect.getAsJsonObject();
                        PotionEffectType type = PotionEffectType.getByName(customEffectJSON.get("type").getAsString());
                        int duration = customEffectJSON.get("duration").getAsInt();
                        int amplifier = customEffectJSON.get("amplifier").getAsInt();
                        boolean ambient = customEffectJSON.get("ambient").getAsBoolean();
                        boolean particles = customEffectJSON.get("particles").getAsBoolean();
                        boolean icon = customEffectJSON.get("icon").getAsBoolean();
                        if (type != null) ((PotionMeta) meta).addCustomEffect(new PotionEffect(type, duration, amplifier, ambient, particles, icon), true);
                    });
                }
                if (potionMetaJSON.has("color")) {
                    ((PotionMeta) meta).setColor(Color.fromRGB(Integer.parseInt(potionMetaJSON.get("color").getAsString(), 16)));
                }
            }
            item.setItemMeta(meta);
        }
        return item;
    }
}
