package menu.elements.buttons;

import gameobjects.GameObject;
import gameobjects.player.Character;
import gameobjects.player.hearts.Heart;
import gameobjects.powerups.Invincibility;
import gameobjects.powerups.JumpBoost;
import gameobjects.powerups.SpeedBoost;
import gameobjects.traps.StaticTrap;
import gameobjects.traps.shootingtraps.Cannon;
import gameobjects.winrequirements.Cog;
import gameobjects.winrequirements.coin.Coin;
import gameobjects.winrequirements.generator.Generator;
import menu.elements.gameobjects.ArrowKey;
import menu.elements.gameobjects.UIGameObject;
import menu.menus.ButtonMenu;
import menu.menus.MenuLayout;
import menu.menus.PagedMenu;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Text;

/**
 * Button that opens the tutorial screen
 */
public class TutorialButton extends Button
{
    private PagedMenu menuToSend;   // The menu that holds all the tutorial pages
    /**
     * Creates a button that launches the tutorial menu
     *
     * @param x         the x co-ord
     * @param y         the y co-ord
     * @param imagePath the button image
     */
    public TutorialButton(float x, float y, String imagePath)
    {
        super(x, y, imagePath);
        menuToSend = new PagedMenu();
        menuToSend.addPage(buildStoryPage());
        menuToSend.addPage(buildMovementPage());
        menuToSend.addPage(buildTrapsPage());
        menuToSend.addPage(buildHealthPage());
        menuToSend.addPage(buildPowerupsPage());
        menuToSend.addPage(buildGoalPage());
    }

    @Override
    public void onClick(ButtonMenu menu)
    {
        menu.setMenu(menuToSend);
    }

    /**
     * Creates the layout for displaying what the story is
     * @return the menu layout for the story page
     */
    private MenuLayout buildStoryPage()
    {
        MenuLayout storyLayout = new MenuLayout("Story", "/ui/blurBackground.png");
        Text storyTitle = GameObject.createText("The Story", Color.BLACK);
        storyTitle.setCharacterSize(48);
        storyTitle.setPosition(300, 40);
        Text introduction = GameObject.createText("You are Zeo, a CIA agent tasked with saving the planet!\nRobots are rising up " +
                "and want to overthrow us, \n" +
                "you must stop them before it's too late.", Color.BLACK);
        Text task = GameObject.createText("Somewhere, there is an antenna that is broadcasting\nthe signal telling the robots to attack." +
                " This antenna is\nextremely powerful and so requires a lot of energy.\nThe power comes from generators that have been built\naround the world." +
                "Your task is simple, disable the\ngenerators. Find cogs around the world and jam them\ninto the generators to break them!", Color.BLACK);
        introduction.setCharacterSize(28);
        introduction.setOrigin(0,0);
        introduction.setPosition(50, 130);
        task.setCharacterSize(28);
        task.setOrigin(0,0);
        task.setPosition(50, 250);
        UIGameObject player = new UIGameObject(new Character(500, 10, "/character/walk1.png"));
        player.setScale(1.2f);
        storyLayout.addDrawable(player);
        storyLayout.addDrawable(storyTitle);
        storyLayout.addDrawable(introduction);
        storyLayout.addDrawable(task);
        return storyLayout;
    }

    /**
     * Creates the layout for displaying what the movement controls are
     * @return the menu layout for the movement page
     */
    private MenuLayout buildMovementPage()
    {
        MenuLayout movementLayout = new MenuLayout("Movement", "/ui/blurBackground.png");
        Text title = GameObject.createText("Movement", Color.BLACK);
        title.setPosition(300, 40);
        title.setCharacterSize(48);
        ArrowKey upArrow = new ArrowKey(100, 200);
        Text upText = GameObject.createText("Jump! Watch your head!", Color.BLACK);
        upText.setOrigin(0,0);
        upText.setPosition(150, 186);
        upText.setCharacterSize(28);
        ArrowKey leftArrow = new ArrowKey(100, 300);
        Text leftText = GameObject.createText("Run left. Watch out for traps!", Color.BLACK);
        leftText.setOrigin(0,0);
        leftText.setPosition(150, 286);
        leftText.setCharacterSize(28);
        leftArrow.setRotation(270);
        ArrowKey rightArrow = new ArrowKey(100, 400);
        Text rightText = GameObject.createText("Run Right. Avoid the gaps!", Color.BLACK);
        rightText.setOrigin(0,0);
        rightText.setPosition(150, 386);
        rightText.setCharacterSize(28);
        rightArrow.setRotation(90);
        movementLayout.addDrawable(title);
        movementLayout.addDrawable(upArrow);
        movementLayout.addDrawable(upText);
        movementLayout.addDrawable(leftArrow);
        movementLayout.addDrawable(leftText);
        movementLayout.addDrawable(rightArrow);
        movementLayout.addDrawable(rightText);
        return movementLayout;
    }

    /**
     * Creates the layout for displaying what traps exist
     * @return the menu layout for the traps page
     */
    private MenuLayout buildTrapsPage()
    {
        MenuLayout tutorialLayout = new MenuLayout("Traps", "/ui/blurBackground.png");
        UIGameObject cannon = new UIGameObject(new Cannon(550, 250, 0));
        cannon.setScale(0.2f);
        UIGameObject spikes = new UIGameObject(new StaticTrap(50, 250, "/traps/spikes/3spikes.png", 1));
        spikes.setScale(1.5f);
        Text title = GameObject.createText("Traps", Color.BLACK);
        title.setPosition(300, 40);
        title.setCharacterSize(48);
        Text trapIntro = GameObject.createText("Traps are the robots' defences that have been placed to\n" +
                "stop you from succeeding. Be Careful. If a trap hits you,\n" +
                "you'll lose a life and will be teleported back to the last\nsafe location or a checkpoint if you lost all your lives.", Color.BLACK);
        trapIntro.setOrigin(0,0);
        trapIntro.setCharacterSize(28);
        trapIntro.setPosition(50, 100);
        Text spikesDesc = GameObject.createText("These are spikes,\nif you touch them,\nyou will lose a life.\n" +
                "best to avoid them\nor jump over them.", Color.BLACK);
        spikesDesc.setOrigin(0,0);
        spikesDesc.setCharacterSize(24);
        spikesDesc.setPosition(20, 320);
        Text cannonDesc = GameObject.createText("Cannons are ranged\nweapons that fire at you\nafter a delay.\nDon't get shot!", Color.BLACK);
        cannonDesc.setOrigin(0,0);
        cannonDesc.setPosition(500,320);
        cannonDesc.setCharacterSize(24);
        tutorialLayout.addDrawable(cannonDesc);
        tutorialLayout.addDrawable(title);
        tutorialLayout.addDrawable(cannon);
        tutorialLayout.addDrawable(spikes);
        tutorialLayout.addDrawable(spikesDesc);
        tutorialLayout.addDrawable(trapIntro);
        return tutorialLayout;
    }

    /**
     * Creates the layout for displaying how the health system works
     * @return the menu layout for the health page
     */
    private MenuLayout buildHealthPage()
    {
        MenuLayout healthPage = new MenuLayout("Health", "/ui/blurBackground.png");
        Text title = GameObject.createText("Health", Color.BLACK);
        title.setCharacterSize(48);
        title.setPosition(300, 40);
        Heart newHeart = new Heart(200, 100);
        UIGameObject heart = new UIGameObject(newHeart);
        UIGameObject heart2 = new UIGameObject(new Heart(newHeart.getX() + newHeart.getWidth() + 10, newHeart.getY()));
        UIGameObject heart3 = new UIGameObject(new Heart(newHeart.getX() + (newHeart.getWidth() + 10) * 2, newHeart.getY()));
        Text healthDesc = GameObject.createText("Health is lost when you get hurt by a trap or fall.\n" +
                "When you lose a heart you will be teleported back\nto your last safe location.\n" +
                "If you lose all your lives, you will teleport\nback to the most recent checkpoint.", Color.BLACK);
        healthDesc.setOrigin(0,0);
        healthDesc.setCharacterSize(28);
        healthDesc.setPosition(100, 200);
        healthPage.addDrawable(title);
        healthPage.addDrawable(heart);
        healthPage.addDrawable(heart2);
        healthPage.addDrawable(heart3);
        healthPage.addDrawable(healthDesc);
        return healthPage;
    }

    /**
     * Creates the layout for displaying what the powerups do
     * @return the menu layout for the powerups page
     */
    private MenuLayout buildPowerupsPage()
    {
        MenuLayout powerupLayout = new MenuLayout("Powerups", "/ui/blurBackground.png");
        Text title = GameObject.createText("Powerups", Color.BLACK);
        title.setCharacterSize(48);
        title.setPosition(300, 40);
        UIGameObject jumpBoost = new UIGameObject(new JumpBoost(50, 100, 0, null));
        Text jumpText = GameObject.createText("Jump Boost. You'll jump very high!", Color.BLACK);
        jumpText.setOrigin(0,0);
        jumpText.setCharacterSize(28);
        jumpText.setPosition(150, 130);
        UIGameObject invincibility = new UIGameObject(new Invincibility(50, 250, 0, null));
        invincibility.setScale(0.13f);
        Text invincibilityText = GameObject.createText("Invincibility. You'll take no damage!", Color.BLACK);
        invincibilityText.setOrigin(0,0);
        invincibilityText.setCharacterSize(28);
        invincibilityText.setPosition(150, 300);
        UIGameObject speedBoost = new UIGameObject(new SpeedBoost(50, 400, 0, null, 0));
        speedBoost.setScale(0.13f);
        Text speedText = GameObject.createText("Speed Boost. You'll run very fast!", Color.BLACK);
        speedText.setOrigin(0,0);
        speedText.setCharacterSize(28);
        speedText.setPosition(150, 450);
        powerupLayout.addDrawable(title);
        powerupLayout.addDrawable(jumpBoost);
        powerupLayout.addDrawable(jumpText);
        powerupLayout.addDrawable(invincibility);
        powerupLayout.addDrawable(invincibilityText);
        powerupLayout.addDrawable(speedBoost);
        powerupLayout.addDrawable(speedText);
        return powerupLayout;
    }

    /**
     * Creates the layout for displaying what the goal of the game is
     * @return the menu layout for the goal page
     */
    private MenuLayout buildGoalPage()
    {
        MenuLayout goalLayout = new MenuLayout("The Goal", "/ui/blurBackground.png");
        Text title = GameObject.createText("The Goal", Color.BLACK);
        title.setCharacterSize(48);
        title.setPosition(300, 40);
        Generator generator = new Generator(20, 100, 2);
        generator.setDisplayText("", Color.BLACK);
        generator.getSprite().setScale(3f, 3f);
        UIGameObject uiGenerator = new UIGameObject(generator);
        UIGameObject coin = new UIGameObject(new Coin(620, 100));
        coin.setScale(1.5f);
        UIGameObject cog = new UIGameObject(new Cog(300, 100));
        cog.setScale(1.5f);
        Text generatorDesc = GameObject.createText("The generators are active!\nStop them! Collect cogs dotted\naround the levels" +
                        " and deliver\nthem to the generators\n to break them and end the power source." +
                        "\nSome generators will require more\ncogs to break than others", Color.BLACK);
        generatorDesc.setOrigin(0,0);
        generatorDesc.setCharacterSize(24);
        generatorDesc.setPosition(20, 250);
        Text coinText = GameObject.createText("This operation is expensive,\nmake sure to pick up the gold coins\nto help fund our operations!", Color.BLACK);
        coinText.setOrigin(0,0);
        coinText.setCharacterSize(24);
        coinText.setPosition(400, 250);
        goalLayout.addDrawable(uiGenerator);
        goalLayout.addDrawable(title);
        goalLayout.addDrawable(coin);
        goalLayout.addDrawable(cog);
        goalLayout.addDrawable(generatorDesc);
        goalLayout.addDrawable(coinText);
        return goalLayout;
    }
}
