package gameobjects.interfaces;

import game.Game;
import gameobjects.GameObject;

public interface Interactable
{
    void onInteraction(Game game, GameObject interactor);

    Collider getCollider();
}
