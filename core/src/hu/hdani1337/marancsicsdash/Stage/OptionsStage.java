package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

import static hu.hdani1337.marancsicsdash.Stage.MenuStage.MENU_BG_TEXTURE;

public class OptionsStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
    }

    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    private OneSpriteStaticActor MenuBackground;

    @Override
    public void assignment() {
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(MenuBackground);
    }
}
