package uielements.inventory;

import gameobjects.GameObject;
import java.util.ArrayList;

/**
 * Represents a player's inventory
 */
public class Inventory
{
    private ArrayList<InventoryItem> items; // The list of items the player has

    /**
     * Creates an inventory
     */
    public Inventory()
    {
        items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory
     * @param object the object to add
     * @param amount the amount to add
     */
    public void addItem(GameObject object, int amount)
    {
        InventoryItem foundItem = getItem(object);
        if(amount == 0)
        {
            if(foundItem.getAmount() > 0)
            {
                items.remove(foundItem);
            }
        }
        else
        {
            if(foundItem.getAmount() == 0)
            {
                InventoryItem newItem = new InventoryItem(object, amount);
                items.add(newItem);
            }
            else
            {
                foundItem.addAmount(amount);
            }
        }
    }

    /**
     * Sets the amount of an object in the inventory
     * Adds item if it doesn't exist, removes if it becomes 0
     * @param object the object to set the amount for
     * @param amount the amount
     */
    public void setAmount(GameObject object, int amount)
    {
        InventoryItem foundItem = getItem(object);
        if(amount == 0)
        {
            if(foundItem != null)
            {
                items.remove(foundItem);
            }
        }
        else
        {
            if(foundItem == null)
            {
                InventoryItem newItem = new InventoryItem(object, amount);
                items.add(newItem);
            }
            else
            {
                foundItem.setAmount(amount);
            }
        }
    }

    /**
     * Gets if the item is in the inventory
     * @param object the object to check for
     * @return true if it is in the inventory, false otherwise
     */
    public boolean itemExists(GameObject object)
    {
        return getItemCount(object) > 0;
    }

    /**
     * Gets the inventory item associated with the object
     * @param object the object to get the information for
     * @return returns the inventory item, will never be null but can be 0-amount
     */
    public InventoryItem getItem(GameObject object)
    {
        for (InventoryItem item : items)
        {
            if(item.getItem().getClass().equals(object.getClass()))
            {
                return item;
            }
        }
        return new InventoryItem(object, 0);
    }

    /**
     * Gets how much of an item there is
     * @param object the object to get the amount for
     * @return the amount of items of the object in the inventory
     */
    public int getItemCount(GameObject object)
    {
        InventoryItem foundItem = getItem(object);
        if(foundItem == null)
        {
            return 0;
        }
        return foundItem.getAmount();
    }

    /**
     * Gets all items in the inventory
     * @return the items in the inventory
     */
    public ArrayList<InventoryItem> getItems()
    {
        return items;
    }

    public void clear()
    {
        items.clear();
    }
}
