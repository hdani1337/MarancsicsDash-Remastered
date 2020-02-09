package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Mushroom;
import hu.hdani1337.marancsicsdash.Actor.Tank;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class GameStage extends MyStage implements StageInterface {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(Marancsics.class,assetList);
        assetList.collectAssetDescriptor(Tank.class,assetList);
        assetList.collectAssetDescriptor(Mushroom.class,assetList);
        assetList.collectAssetDescriptor(Coin.class,assetList);
        assetList.collectAssetDescriptor(MarancsicsBoss.class,assetList);
    }

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
        addActor(new Marancsics(game));
        addActor(new Zsolti(game){
            @Override
            public void init() {
                super.init();
                setPosition(1.5f,0);
            }
        });
        addActor(new Tank(game){
            @Override
            public void init() {
                super.init();
                setPosition(5,0);
            }
        });
        addActor(new Coin(game){
            @Override
            public void init() {
                super.init();
                setPosition(5,5);
            }
        });
        addActor(new Mushroom(game){
            @Override
            public void init() {
                super.init();
                setPosition(7,5);
            }
        });
        addActor(new MarancsicsBoss(game){
            @Override
            public void init() {
                super.init();
                setPosition(8,0);
            }
        });
    }
}
