package uielements.inventory;

import game.Game;
import gameobjects.GameObject;
import gameobjects.interfaces.Clickable;
import gameobjects.interfaces.Collider;

/**
 * An item in an inventory with a set amount
 */
public class InventoryItem extends GameObject implements Clickable
{
    private GameObject item;    // The item in the inventory
    private int amount;         // The amount of this item there is

    /**
     * Creates an item with the object and amount
     * @param object the object to add to the inventory
     * @param total how much of the object should be in the inventory
     */
    public InventoryItem(GameObject object, int total)
    {
        super(0, 0);
        item = object;
        amount = total;
    }

    /**
     * Gets the object associated with this item
     * @return the game object
     */
    public GameObject getItem()
    {
        return item;
    }

    /**
     * Gets the amount of the item that is in the inventory
     * @return the amount in the inventory
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * Adds the given amount to the inventory
     * @param amount the amount to add
     */
    public void addAmount(int amount)
    {
        this.amount += amount;
    }

    /**
     * Sets the amount of this item
     * @param amount the amount of this item that should be in the inventory
     */
    public void setAmount(int amount)
    {
        if(amount >= 0)
        {
            this.amount = amount;
        }
    }

    @Override
    public void onInteraction(Game game, GameObject interactor)
    {

    }

    @Override
    public Collider getCollider()
    {
        return new Collider(getSprite().getGlobalBounds());
    }

    @Override
    public void onClick(Game game)
    {

    }
}
