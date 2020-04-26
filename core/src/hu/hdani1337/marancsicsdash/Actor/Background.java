package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public class Background extends OneSpriteStaticActor {
    //region Háttér típus Enum
    /**
     * HÁTTÉR TÍPUSAINAK ENUM
     * EGYSZERŰBB MINT INTEGEREKKEL SZERENCSÉTLENKEDNI
     * **/
    public enum BackgroundType{
        CSERNOBIL, SZAHARA, SZIBERIA, ZALA, OCEAN
    }
    //endregion
    //region AssetList
    public static final String CSERNOBIL_TEXTURE = "pic/backgrounds/back/bg.png";
    public static final String SZIBERIA_TEXTURE = "pic/backgrounds/back/bg2.png";
    public static final String ZALA_TEXTURE = "pic/backgrounds/back/bg3.jpg";
    public static final String SZAHARA_TEXTURE = "pic/backgrounds/back/bg4.png";
    public static final String OCEAN_TEXTURE = "pic/backgrounds/back/bg5.jpg";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CSERNOBIL_TEXTURE);
        assetList.addTexture(SZIBERIA_TEXTURE);
        assetList.addTexture(ZALA_TEXTURE);
        assetList.addTexture(SZAHARA_TEXTURE);
        assetList.addTexture(OCEAN_TEXTURE);
    }
    //endregion
    //region Változók
    public static float ground;//TALAJ Y KOORDINÁTÁJA
    //endregion
    //region Konstruktor
    public Background(MyGame game, BackgroundType backgroundType, World world, Viewport viewport) {
        super(game, CSERNOBIL_TEXTURE);
        setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        setTexture(backgroundType);
        /**
         * NULLKEZELÉS
         * AKI NEM CSINÁL NULLKEZELÉST AZ BUZI ÉS MÉG MEG IS ÜTÖM
         * **/
        if(world != null){
            makeGround(world);
            makeCeiling(world);
        }
    }
    //endregion
    //region Textúra beállító metódus
    /**
     * HÁTTÉR TÍPUSÁTÓL FÜGGŐEN BEÁLLÍTJUK A TEXTÚRÁKAT ÉS A TALAJ Y KOORDINÁTÁJÁT
     * **/
    private void setTexture(BackgroundType backgroundType){
        switch (backgroundType){
            case CSERNOBIL:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_TEXTURE));
                ground = 0.42f;
                break;
            }
            case SZIBERIA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE));
                ground = 0.25f;
                break;
            }
            case ZALA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_TEXTURE));
                ground = 0.6f;
                break;
            }
            case SZAHARA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE));
                ground = 0.84f;
                break;
            }
            case OCEAN:{
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_TEXTURE));
                ground = 0.9f;
                break;
            }
            default:{
                Gdx.app.log("Background", "Paraméterként megadott háttértípus érvénytelen, alapértelmezettként a Csernobil háttér kerül beállításra!");
            }
        }
    }
    //endregion
    //region Világ metódusai
    /**
     * Talaj készítése a világba
     * **/
    private void makeGround(World world){
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, ground));
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(16 * 2, ground);
        groundBody.createFixture(groundBox, 0.0f);
    }

    /**
     * Plafon készítése a világba, hogy Zsolti ne repülhessen a végtelenbe
     * **/
    private void makeCeiling(World world){
        BodyDef groundBodyDef2 = new BodyDef();
        groundBodyDef2.position.set(new Vector2(0, 15));
        Body groundBody2 = world.createBody(groundBodyDef2);
        PolygonShape groundBox2 = new PolygonShape();
        groundBox2.setAsBox(16 * 2, ground);
        groundBody2.createFixture(groundBox2, 0.0f);
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        move();
    }

    /**
     * Csak a GameStagen és BossStagen mozogjon
     * Sebesség a világ méretéhez igazítva
     * **/
    private void move(){
        if(getStage() != null){
            if(getStage() instanceof GameStage){
                if(GameStage.isAct) setX(getX()-(difficulty*0.005f));//HÁTTÉR FOLYAMATOS MOZGATÁSA
            }else if(getStage() instanceof BossStage){
                if(BossStage.isAct) setX(getX()-(difficulty*0.005f));//HÁTTÉR FOLYAMATOS MOZGATÁSA
            }
        }
    }
    //endregion
}
