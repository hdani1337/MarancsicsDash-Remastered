package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.MarancsicsDash;

public class LoadingStage extends hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTextureAtlas(Marancsics.MARANCSICS_ATLAS).protect = true;
        assetList.addTextureAtlas(Marancsics.MARANCSICS_CORONA).protect = true;
        assetList.addTextureAtlas(Marancsics.MARANCSICS_BOX).protect = true;
        assetList.addTextureAtlas(Marancsics.MARANCSICS_CONSTRUCTOR).protect = true;
        assetList.addTextureAtlas(Zsolti.ZSOLTI_ATLAS).protect = true;
        assetList.addTextureAtlas(Zsolti.ZSOLTI_WARRIOR).protect = true;
        assetList.addTextureAtlas(Zsolti.SUPER_ZSOLTI_ATLAS).protect = true;
    }
    //endregion
    //region Konstruktor
    public LoadingStage(MyGame game) {
        super(new ResponseViewport(900), game);
        if(MarancsicsDash.needsLoading) {
            addActor(new OneSpriteAnimatedActor(game, getRandomHash()) {
                @Override
                public void init() {
                    super.init();
                    setFps(18);
                    setPosition(getViewport().getWorldWidth() / 2 - this.getWidth() / 2, getViewport().getWorldHeight() / 2 - this.getHeight() / 2);
                }

                @Override
                public void act(float delta) {
                    super.act(delta);
                    setRotation(getRotation() - 5);
                }
            });
        }
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    public AssetList getAssetList() {
        return assetList;
    }
    //endregion
    //region Random skin hash-t visszaadó metódus
    private String getRandomHash(){
        int random = (int)(Math.random()*6);
        switch (random){
            case 0:{
                return Marancsics.MARANCSICS_CORONA;
            }
            case 1:{
                return Marancsics.MARANCSICS_BOX;
            }
            case 2:{
                return Zsolti.ZSOLTI_WARRIOR;
            }
            case 3:{
                return Marancsics.MARANCSICS_CONSTRUCTOR;
            }
            case 4:{
                return Zsolti.SUPER_ZSOLTI_ATLAS;
            }
            case 5:{
                return Marancsics.MARANCSICS_ATLAS;
            }
            default:{
                return Zsolti.ZSOLTI_ATLAS;
            }
        }
    }
    //endregion
}
