package menu.menus;

import menu.elements.buttons.NextPageButton;
import menu.elements.buttons.PreviousPageButton;

import java.util.ArrayList;

/**
 * A collection of MenuLayouts that can be scrolled between
 */
public class PagedMenu
{
    private ArrayList<MenuLayout> pages;    // The layouts in the collection
    private int currentIndex;               // The current page index

    /**
     * Creates a menu with a starting index of 0
     * Don't try to get a page without adding them first!
     */
    public PagedMenu()
    {
        currentIndex = 0;
        pages = new ArrayList<>();
    }

    /**
     * Creates a menu with an initial starting page
     * @param firstPage the first page of the menu
     */
    public PagedMenu(MenuLayout firstPage)
    {
        currentIndex = 0;
        pages = new ArrayList<>();
        addPage(firstPage);
    }

    /**
     * Resets the menu back to the first page
     */
    public void resetPage()
    {
        currentIndex = 0;
    }

    /**
     * Add a page to the menu
     * @param newPage the new page to add
     */
    public void addPage(MenuLayout newPage)
    {
        if(pages.size() > 0)
        {
            MenuLayout previousPage = pages.get(pages.size() - 1);
            previousPage.addDrawable(new NextPageButton(700, 500));
            newPage.addDrawable(new PreviousPageButton(10, 500));
        }
        pages.add(newPage);
    }

    /**
     * Gets the next page or the current page if there is no next page
     * @return the next page
     */
    public MenuLayout nextPage()
    {
        if(currentIndex + 1 <= pages.size())
        {
            currentIndex++;
        }
        return pages.get(currentIndex);
    }

    /**
     * Gets the previous page or the current page if there is no previous page
     * @return the previous page
     */
    public MenuLayout previousPage()
    {
        if(currentIndex - 1 >= 0)
        {
            currentIndex--;
        }
        return pages.get(currentIndex);
    }

    /**
     * Gets the current page
     * @return the current page
     */
    public MenuLayout getCurrentPage()
    {
        return pages.get(currentIndex);
    }

    /**
     * Gets the list of all pages
     * @return all pages in the menu
     */
    public ArrayList<MenuLayout> getPages()
    {
        return pages;
    }
}
