package view.custom.drawer;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class DrawerItem {
	 
    String ItemName;
    int imgResID;
    String title;
    boolean isFB;

    public DrawerItem(String itemName, int imgResID) {
        super();
        ItemName = itemName;
        this.imgResID = imgResID;
  }

    
      public DrawerItem(boolean isFB) {
          this(null, 0);
          this.isFB = isFB;
    }

    public DrawerItem(String title) {
          this(null, 0);
          this.title = title;
    }

    public String getTitle() {
          return title;
    }
    public void setTitle(String title) {
          this.title = title;
    }
    
    public int getImage(){
    	return imgResID;
    }
    
    public String getItemName(){
    	return this.ItemName;
    }

    public boolean isFB() {
          return isFB;
    }

}
