package captcha.inventory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtil
{

    public static ItemStack createItem(Material mat)
    {
	ItemStack item = new ItemStack(mat);
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(generateName());
	item.setItemMeta(meta);
	return item;
    }
    
    private static String generateName()
    {
	return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
    
    public static int getRandomInt(List<Integer> list)
    {
        int randomElement = list.get(new Random().nextInt(list.size()));
        return randomElement;
    }
    
    public static Material randomMaterial()
    {
	Material[] materials = {Material.STONE, Material.COBBLESTONE, Material.DIAMOND, Material.DIRT, Material.DIAMOND_BLOCK, Material.DARK_OAK_LOG, Material.SAND, Material.GOLD_BLOCK, Material.GOLD_INGOT};
	return materials[new Random().nextInt(materials.length)];
    }
    
}

