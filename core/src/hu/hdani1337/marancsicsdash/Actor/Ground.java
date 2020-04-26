package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;
import hu.hdani1337.marancsicsdash.Stage.HudStage;

import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public class Ground extends OneSpriteStaticActor {
    //region AssetList
    public static final String CSERNOBIL_GROUND_TEXTURE = "pic/backgrounds/ground/bg_talaj.png";
    public static final String SZIBERIA_GROUND_TEXTURE = "pic/backgrounds/ground/bg2_talaj.png";
    public static final String ZALA_GROUND_TEXTURE = "pic/backgrounds/ground/bg3_talaj.png";
    public static final String SZAHARA_GROUND_TEXTURE = "pic/backgrounds/ground/bg4_talaj.png";
    public static final String OCEAN_GROUND_TEXTURE = "pic/backgrounds/ground/bg5_viz.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CSERNOBIL_GROUND_TEXTURE);
        assetList.addTexture(SZIBERIA_GROUND_TEXTURE);
        assetList.addTexture(ZALA_GROUND_TEXTURE);
        assetList.addTexture(SZAHARA_GROUND_TEXTURE);
        assetList.addTexture(OCEAN_GROUND_TEXTURE);
    }
    //endregion
    //region Változók
    private Background.BackgroundType backgroundType;
    //endregion
    //region Konstruktor
    public Ground(MyGame game, Background.BackgroundType backgroundType, Viewport viewport) {
        super(game, CSERNOBIL_GROUND_TEXTURE);
        this.backgroundType = backgroundType;
        setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        switch (backgroundType){
            /**
             * HÁTTÉR TÍPUSÁTÓL FÜGGŐEN BEÁLLÍTJUK A TEXTÚRÁKAT ÉS A TALAJ Y KOORDINÁTÁJÁT
             * **/
            case CSERNOBIL:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_GROUND_TEXTURE));
                break;
            }
            case SZIBERIA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_GROUND_TEXTURE));
                break;
            }
            case ZALA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_GROUND_TEXTURE));
                break;
            }
            case SZAHARA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_GROUND_TEXTURE));
                break;
            }
            case OCEAN:{
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_GROUND_TEXTURE));
                break;
            }
            default:{
                Gdx.app.log("Background", "Paraméterként megadott háttértípus érvénytelen, alapértelmezettként a Csernobil háttér kerül beállításra!");
            }
        }
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        if(getStage() != null){
            /**
             * Azért kell két külön metódus, mert a HudStagen nagyobb sebességgel kell mozgatni a talajt,
             * mert neki jelentősen nagyobb a Viewportja, mint egy világ stagenek
             * **/
            if(getStage() instanceof HudStage)
                moveOnHudStage();
            else if(getStage() instanceof GameStage || getStage() instanceof BossStage)
                moveInWorld();
        }
    }

    /**
     * HudStage-n mozgatás
     * **/
    private void moveOnHudStage(){
        if (((MyStage) getStage()).getScreen() instanceof GameScreen) {
            if (GameStage.isAct)
                setX(getX() - (difficulty * 12));//HÁTTÉR FOLYAMATOS MOZGATÁSA
        } else if (((MyStage) getStage()).getScreen() instanceof BossScreen) {
            if (BossStage.isAct)
                setX(getX() - (difficulty * 12));//HÁTTÉR FOLYAMATOS MOZGATÁSA
        }
    }

    /**
     * Világban mozgatás
     * **/
    private void moveInWorld(){
        if(backgroundType == Background.BackgroundType.SZIBERIA || backgroundType == Background.BackgroundType.CSERNOBIL) setVisible(false);
        else {
            if (((MyStage) getStage()).getScreen() instanceof GameScreen) {
                if (GameStage.isAct)
                    setX(getX() - (difficulty * 0.04f));//HÁTTÉR FOLYAMATOS MOZGATÁSA
            } else if (((MyStage) getStage()).getScreen() instanceof BossScreen) {
                if (BossStage.isAct)
                    setX(getX() - (difficulty * 0.04f));//HÁTTÉR FOLYAMATOS MOZGATÁSA
            }
        }
    }
    //endregion
}
