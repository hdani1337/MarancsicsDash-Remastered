package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.InfoStage;

public class InfoScreen extends MyScreen {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(InfoStage.class, assetList);
    }
    //endregion
    //region Konstruktor
    public InfoScreen(MyGame game) {
        super(game);
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    protected void afterAssetsLoaded() {
        addStage(new InfoStage(game),1,true);
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
