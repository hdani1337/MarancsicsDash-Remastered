package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.Gdx;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Actor.Background;

import static hu.hdani1337.marancsicsdash.Actor.Background.CSERNOBIL_TEXTURE;
import static hu.hdani1337.marancsicsdash.Actor.Background.OCEAN_TEXTURE;
import static hu.hdani1337.marancsicsdash.Actor.Background.SZAHARA_TEXTURE;
import static hu.hdani1337.marancsicsdash.Actor.Background.SZIBERIA_TEXTURE;
import static hu.hdani1337.marancsicsdash.Actor.Background.ZALA_TEXTURE;

public class ShopBackgroundPreview extends OneSpriteStaticActor {

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CSERNOBIL_TEXTURE);
        assetList.addTexture(SZIBERIA_TEXTURE);
        assetList.addTexture(ZALA_TEXTURE);
        assetList.addTexture(SZAHARA_TEXTURE);
        assetList.addTexture(OCEAN_TEXTURE);
    }

    public ShopBackgroundPreview(MyGame game) {
        super(game, CSERNOBIL_TEXTURE);
        setSize(400,225);
        id = 0;
    }

    public enum StepDirection{
        UP, DOWN
    }

    private byte id;

    public void step(StepDirection stepDirection){
        if(stepDirection == StepDirection.UP) id++;
        else if (stepDirection == StepDirection.DOWN) id--;

        switch (id % 5){
            case 0: sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_TEXTURE));
            case 1: sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE));
            case 2: sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_TEXTURE));
            case 3: sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE));
            case 4: sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_TEXTURE));
        }
    }

    public void step(byte id){
        switch (id % 5){
            case 0: sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_TEXTURE));
            case 1: sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE));
            case 2: sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_TEXTURE));
            case 3: sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE));
            case 4: sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_TEXTURE));
        }
    }

    public void step(Background.BackgroundType backgroundType){
        switch (backgroundType) {
            /**
             * HÁTTÉR TÍPUSÁTÓL FÜGGŐEN BEÁLLÍTJUK A TEXTÚRÁKAT ÉS A TALAJ Y KOORDINÁTÁJÁT
             * **/
            case CSERNOBIL: {
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_TEXTURE));
                break;
            }
            case SZIBERIA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE));
                break;
            }
            case ZALA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_TEXTURE));
                break;
            }
            case SZAHARA: {
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE));
                break;
            }
            case OCEAN: {
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_TEXTURE));
                break;
            }
            default: {
                Gdx.app.log("ShopBackgroundPreview", "Paraméterként megadott háttértípus érvénytelen, alapértelmezettként a Csernobil háttér kerül beállításra!");
            }
        }
    }
}
