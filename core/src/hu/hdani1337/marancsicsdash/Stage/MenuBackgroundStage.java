package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class MenuBackgroundStage extends PrettyStage {

    public static final String MENU_BG_TEXTURE = "pic/menuBg.jpg";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
    }

    private OneSpriteStaticActor MenuBackground;

    private Marancsics marancsics;
    private Zsolti zsolti;

    public MenuBackgroundStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void assignment() {
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        marancsics = new Marancsics(game);
        zsolti = new Zsolti(game);

        zsolti.setFps(30);
        marancsics.setFps(24);
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        marancsics.setX(-1000);
        zsolti.setX(-820);
        int random = (int)(Math.random()*350);
        marancsics.setY(random);
        zsolti.setY(random);
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
        addActor(marancsics);
        addActor(zsolti);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(marancsics.getX() < getViewport().getWorldWidth()+100) {
            marancsics.setX(marancsics.getX() + 12);
            zsolti.setX(zsolti.getX() + 14);
        }else{
            int random = (int)(Math.random()*2500+1500);
            marancsics.setX(-500-random);
            zsolti.setX(-320-random);
            random = (int)(Math.random()*350);
            marancsics.setY(random);
            zsolti.setY(random);
        }
    }
}
