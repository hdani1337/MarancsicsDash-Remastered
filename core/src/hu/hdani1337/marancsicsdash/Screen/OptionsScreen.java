package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.OptionsStage;

public class OptionsScreen extends MyScreen {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(OptionsStage.class, assetList);
    }
    //endregion
    //region Konstruktor
    public OptionsScreen(MyGame game) {
        super(game);
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    protected void afterAssetsLoaded() {
        addStage(new OptionsStage(game),1,true);
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
