package captcha.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CaptchaMenu
{
    private final Material wrongItem;
    private Material correctItem;
    private Inventory inv;
    
    public CaptchaMenu()
    {
	this.wrongItem = randomItem();
	this.correctItem = randomItem();
	this.inv = getInv();
    }

    public Material getWrongItem()
    {
	return wrongItem;
    }

    public Material getCorrectItem()
    {
	return correctItem;
    }
    
    private Material randomItem()
    {
	return InventoryUtil.randomMaterial();
    }
    
    private Inventory getInv()
    {
	return Bukkit.createInventory(null, 27, "Choose the odd one out");
    }
    
    public Inventory getMenu()
    {
	return inv;
    }
    
    public Inventory populate()
    {
	while(getWrongItem() == getCorrectItem() || getWrongItem() == Material.AIR)
	    correctItem = randomItem();
	
	
	Integer[] numbers = {3,4,5,12,13,14,21,22,23};
	List<Integer> numList = Arrays.asList(numbers);
	ArrayList<Integer> index = new ArrayList<Integer>();
	
	index.addAll(numList);
	
	int random = InventoryUtil.getRandomInt(index);
	index.remove((Integer) random);
	
	ArrayList<Integer> indexCopy = (ArrayList<Integer>) index.clone();
	
	ItemStack correct = InventoryUtil.createItem(correctItem);
	inv.setItem(random, correct);
	
	for(Integer place : indexCopy)
	{
	    ItemStack wrong = InventoryUtil.createItem(wrongItem);
	    inv.setItem(place, wrong);
	    index.remove((Integer) place);
	}
	
	return inv;
    }
}
    


 // 3  4  5
 // 12 13 14
 // 21 22 23
