package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Actor.Background;

public class ShopBackgroundPreview extends OneSpriteStaticActor {
    //region AssetList
    public static final String CSERNOBIL_PREVIEW_TEXTURE = "pic/backgrounds/preview/bg.png";
    public static final String SZIBERIA_PREVIEW_TEXTURE = "pic/backgrounds/preview/bg2.png";
    public static final String ZALA_PREVIEW_TEXTURE = "pic/backgrounds/preview/bg3.jpg";
    public static final String SZAHARA_PREVIEW_TEXTURE = "pic/backgrounds/preview/bg4.png";
    public static final String OCEAN_PREVIEW_TEXTURE = "pic/backgrounds/preview/bg5.jpg";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CSERNOBIL_PREVIEW_TEXTURE);
        assetList.addTexture(SZIBERIA_PREVIEW_TEXTURE);
        assetList.addTexture(ZALA_PREVIEW_TEXTURE);
        assetList.addTexture(SZAHARA_PREVIEW_TEXTURE);
        assetList.addTexture(OCEAN_PREVIEW_TEXTURE);
    }
    //endregion
    //region Konstruktor
    public ShopBackgroundPreview(MyGame game) {
        //Alapesetben Csernobil textúra állítódik be
        super(game, CSERNOBIL_PREVIEW_TEXTURE);
        setSize(400,225);
    }
    //endregion
    //region Textúra változtató metódusok
    /**
     * Háttér változtatása konkrét ID alapján (0-4 között)
     * **/
    public void step(int id){
        switch (id % 5){
            case 0: {
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_PREVIEW_TEXTURE));
                break;
            }
            case 1: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_PREVIEW_TEXTURE));
                break;
            }
            case 2: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_PREVIEW_TEXTURE));
                break;
            }
            case 3: {
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_PREVIEW_TEXTURE));
                break;
            }
            case 4: {
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_PREVIEW_TEXTURE));
                break;
            }
        }
    }

    /**
     * Háttér változtatása BackgroundType enummal
     * **/
    public void step(Background.BackgroundType backgroundType){
        switch (backgroundType) {
            case CSERNOBIL: {
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_PREVIEW_TEXTURE));
                break;
            }
            case SZIBERIA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_PREVIEW_TEXTURE));
                break;
            }
            case ZALA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_PREVIEW_TEXTURE));
                break;
            }
            case SZAHARA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_PREVIEW_TEXTURE));
                break;
            }
            case OCEAN: {
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_PREVIEW_TEXTURE));
                break;
            }
            default: {
                Gdx.app.log("ShopBackgroundPreview", "Paraméterként megadott háttértípus érvénytelen, alapértelmezettként a Csernobil háttér kerül beállításra!");
            }
        }
    }
    //endregion
}
