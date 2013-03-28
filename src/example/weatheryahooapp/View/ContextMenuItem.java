package example.weatheryahooapp.View;

import example.weatheryahooapp.Controller.R;

public class ContextMenuItem {
	/** Invalid id of menu item */
	public static final int INVALID_ID = -1;

	/** Invalid menu item name */
	public static final int INVALID_NAME = R.string.defalt_menu_name;

	/** Invalid image resource id */
	public static final int INVALID_IMG_RES = R.drawable.default_menu_item;

	/** Item id of context menu */
	private int m_ItemID;

	/** Name of menu item */
	private int m_NameID;

	/** Image icon of menu item */
	private int m_nImageID;

	public ContextMenuItem() {
		this(INVALID_ID, INVALID_NAME, INVALID_IMG_RES);
	}

	public ContextMenuItem(int nID, int nNameID, int imgID) {
		this.m_ItemID = nID;
		this.m_NameID = nNameID;
		this.m_nImageID = imgID;
	}

	public void setItemName(int nameID) {
		this.m_NameID = nameID;
	}

	public int getItemName() {
		return this.m_NameID;
	}

	public void setItemImage(int nResID) {
		this.m_nImageID = nResID;
	}

	public int getItemImage() {
		return this.m_nImageID;
	}

	public void setItemID(int nID) {
		this.m_ItemID = nID;
	}

	public int getItemID() {
		return this.m_ItemID;
	}
}
