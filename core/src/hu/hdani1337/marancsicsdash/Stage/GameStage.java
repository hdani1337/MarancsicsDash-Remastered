package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Mushroom;
import hu.hdani1337.marancsicsdash.Actor.Tank;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class GameStage extends Box2dStage implements StageInterface {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(Marancsics.class,assetList);
        assetList.collectAssetDescriptor(Tank.class,assetList);
        assetList.collectAssetDescriptor(Mushroom.class,assetList);
        assetList.collectAssetDescriptor(Coin.class,assetList);
        assetList.collectAssetDescriptor(MarancsicsBoss.class,assetList);
    }

    public boolean isShakeScreen;//Kamera megrázása
    final WorldBodyEditorLoader loader = new WorldBodyEditorLoader("bodies.json");

    public GameStage(MyGame game) {
        super(new ResponseViewport(9), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        isShakeScreen = false;
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(new Background(game, Background.BackgroundType.ZALA, world,getViewport()));
        addActor(new Zsolti(game, world, loader){
            @Override
            public void init() {
                super.init();
                setPosition(1.5f,3);
            }
        });
        addActor(new Marancsics(game, world, loader){
            @Override
            public void init() {
                setPosition(1,3);
            }
        });
        addActor(new Tank(game, world, loader){
            @Override
            public void init() {
                super.init();
                setPosition(5,0);
            }
        });
        addActor(new Coin(game, world, loader){
            @Override
            public void init() {
                super.init();
                setPosition(5,5);
            }
        });
        addActor(new Mushroom(game, world, loader){
            @Override
            public void init() {
                super.init();
                setPosition(7,5);
            }
        });
        addActor(new MarancsicsBoss(game, world, loader){
            @Override
            public void init() {
                super.init();
                setPosition(8,0);
            }
        });
    }

    private int offset = 2;
    private float pElapsed = elapsedTime;

    private void shakeScreen(){
        if(getViewport().getScreenX() < 20) {
            getViewport().setScreenX(getViewport().getScreenX() + Math.abs(offset * 4));
            getViewport().setScreenY(getViewport().getScreenY() + Math.abs(offset * 2));
            setCameraZoomSpeed(offset / 5.0f);
            if (elapsedTime > pElapsed + 0.2f) {
                offset *= -1;
                pElapsed = elapsedTime;
            }
        }
        else{
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
            setCameraZoomSpeed(0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isShakeScreen) {
            shakeScreen();
        }
        else{
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
            setCameraZoomSpeed(0);
        }
    }
}
