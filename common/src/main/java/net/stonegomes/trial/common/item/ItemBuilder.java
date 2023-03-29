package net.stonegomes.trial.common.item;

import com.google.common.collect.Lists;
import net.stonegomes.trial.common.color.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder type(Material material) {
        itemStack.setType(material);

        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(ColorUtil.translate(name));
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(ColorUtil.translate(lore));
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder customModelData(Integer data) {
        itemMeta.setCustomModelData(data);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(String... lore) {
        return lore(Lists.newArrayList(ColorUtil.translate(List.of(lore))));
    }

    public ItemBuilder addLore(String... lore) {
        List<String> actualLore = itemMeta.getLore();
        if (actualLore == null) {
            itemMeta.setLore(ColorUtil.translate(Arrays.asList(lore)));
        } else {
            actualLore.addAll(ColorUtil.translate(Arrays.asList(lore)));
            itemMeta.setLore(actualLore);
        }

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder owner(UUID owner) {
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));

        itemStack.setItemMeta(skullMeta);

        return this;
    }

    public ItemBuilder flag(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int value) {
        itemStack.addUnsafeEnchantment(enchantment, value);

        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

}