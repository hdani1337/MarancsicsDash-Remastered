package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.SoundManager;
import hu.hdani1337.marancsicsdash.Stage.MenuStage;

public class MenuScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(MenuStage.class, assetList);
    }

    public MenuScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new MenuStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
