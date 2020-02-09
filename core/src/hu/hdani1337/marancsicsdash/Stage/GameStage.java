package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

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

    public Zsolti zsolti;
    public Tank tank;
    public ArrayList<Coin> coins;
    public Mushroom mushroom;
    public ArrayList<Background> backgrounds;

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
        zsolti = new Zsolti(game, world, loader);
        tank = new Tank(game,world,loader);
        mushroom = new Mushroom(game,world,loader);
        backgrounds = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            backgrounds.add(new Background(game, Background.BackgroundType.SZAHARA, world, getViewport()));
            backgrounds.get(i).setX(backgrounds.get(i).getWidth()*i);
            addActor(backgrounds.get(i));
            backgrounds.get(i).setZIndex(0);
        }
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {
        zsolti.setPosition(1.5f,3);
        tank.setPosition(5,0);
        mushroom.setPosition(7,5);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(zsolti);
        addActor(tank);
        addActor(mushroom);
    }

    private int offset = 1;

    private void shakeScreen(){
        getViewport().setScreenX((int) (Math.sin(offset)*15));
        getViewport().setScreenY((int) (Math.cos(offset)*15));
        offset++;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isShakeScreen) {
            shakeScreen();
        }
        else{
            offset = 1;
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
        }

        if(backgrounds.get(backgrounds.size()-2).getX() < backgrounds.get(backgrounds.size()-2).getWidth()*backgrounds.size()) {
            backgrounds.add(new Background(game, Background.BackgroundType.SZAHARA, world, getViewport()));
            backgrounds.get(backgrounds.size()-1).setX(backgrounds.get(backgrounds.size()-2).getX()+backgrounds.get(backgrounds.size()-2).getWidth());
            addActor(backgrounds.get(backgrounds.size()-1));
            backgrounds.get(backgrounds.size()-1).setZIndex(0);
        }
    }
}
