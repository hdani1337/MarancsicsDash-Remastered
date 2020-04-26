package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.MenuBackgroundStage;
import hu.hdani1337.marancsicsdash.Stage.MenuStage;

public class MenuScreen extends MyScreen {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(MenuStage.class, assetList);
        assetList.collectAssetDescriptor(MenuBackgroundStage.class, assetList);
    }
    //endregion
    //region Konstruktor
    public MenuScreen(MyGame game) {
        super(game);
    }
    //endregion
    //region Változók
    public MenuBackgroundStage menuBackgroundStage;
    //endregion
    //region Absztrakt metódusok
    @Override
    protected void afterAssetsLoaded() {
        menuBackgroundStage = new MenuBackgroundStage(game);
        addStage(menuBackgroundStage,0,false);
        addStage(new MenuStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
    //endregion
}
