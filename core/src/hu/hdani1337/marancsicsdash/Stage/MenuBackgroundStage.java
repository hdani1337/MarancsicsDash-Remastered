package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class MenuBackgroundStage extends PrettyStage {
    //region AssetList
    public static final String MENU_BG_TEXTURE = "pic/backgrounds/menuBg.jpg";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
    }
    //endregion
    //region Változók
    private OneSpriteStaticActor MenuBackground;

    public Marancsics marancsics;
    public Zsolti zsolti;
    //endregion
    //region Konstruktor
    public MenuBackgroundStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    public void assignment() {
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        marancsics = new Marancsics(game, getRandomMarancsics());
        zsolti = new Zsolti(game, getRandomZsolti());

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
        int random = (int)(Math.random()*350);
        marancsics.setY(random);
        zsolti.setY(random);
        marancsics.setX(-1000-random*3);
        zsolti.setX(-820-random*3);
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

    private void removeActors(){
        zsolti.remove();
        marancsics.remove();
        MenuBackground.remove();
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        if(marancsics.getX() < getViewport().getWorldWidth()*2) {
            marancsics.setX(marancsics.getX() + 12);
            zsolti.setX(zsolti.getX() + 14);
        }else{
            removeActors();
            assignment();
            setPositions();
            setSizes();
            addActors();
        }
    }
    //endregion
    //region Random skineket visszaadó metódusok
    private Marancsics.MarancsicsType getRandomMarancsics(){
        int random = (int)(Math.random()*4);
        switch (random){
            case 1:{
                return Marancsics.MarancsicsType.BOX;
            }
            case 2:{
                return Marancsics.MarancsicsType.CONSTRUCTOR;
            }
            case 3:{
                return Marancsics.MarancsicsType.CORONA;
            }
            default:{
                return Marancsics.MarancsicsType.MARANCSICS;
            }
        }
    }

    private Zsolti.ZsoltiType getRandomZsolti(){
        double random = Math.random();
        if(random<0.5)
            return Zsolti.ZsoltiType.WARRIOR;
        else
            return Zsolti.ZsoltiType.ZSOLTI;
    }
    //endregion
}
