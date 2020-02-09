package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.MyFixtureDef;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Background extends OneSpriteStaticActor {

    public static final String CSERNOBIL_TEXTURE = "pic/bg.png";
    public static final String SZIBERIA_TEXTURE = "pic/bg2.png";
    public static final String ZALA_TEXTURE = "pic/bg3.jpg";
    public static final String SZAHARA_TEXTURE = "pic/bg4.png";
    public static final String OCEAN_TEXTURE = "pic/bg5.jpg";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(CSERNOBIL_TEXTURE);
        assetList.addTexture(SZIBERIA_TEXTURE);
        assetList.addTexture(ZALA_TEXTURE);
        assetList.addTexture(SZAHARA_TEXTURE);
        assetList.addTexture(OCEAN_TEXTURE);
    }


    public enum BackgroundType{
        CSERNOBIL, SZIBERIA, ZALA, SZAHARA, OCEAN
    }

    private float ground;

    public Background(MyGame game, BackgroundType backgroundType, World world, Viewport viewport) {
        super(game, CSERNOBIL_TEXTURE);
        setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        switch (backgroundType){
            case CSERNOBIL:{
                sprite.setTexture(game.getMyAssetManager().getTexture(CSERNOBIL_TEXTURE));
                ground = 0.45f;
                break;
            }
            case SZIBERIA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE));
                //setSize(game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE).getWidth(),game.getMyAssetManager().getTexture(SZIBERIA_TEXTURE).getHeight());
                ground = 0.25f;
                break;
            }
            case ZALA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(ZALA_TEXTURE));
                //setSize(game.getMyAssetManager().getTexture(ZALA_TEXTURE).getWidth(),game.getMyAssetManager().getTexture(ZALA_TEXTURE).getHeight());
                ground = 0.65f;
                break;
            }
            case SZAHARA:{
                sprite.setTexture(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE));
                //setSize(game.getMyAssetManager().getTexture(SZAHARA_TEXTURE).getWidth(),game.getMyAssetManager().getTexture(SZAHARA_TEXTURE).getHeight());
                ground = 0.85f;
                break;
            }
            case OCEAN:{
                sprite.setTexture(game.getMyAssetManager().getTexture(OCEAN_TEXTURE));
                //setSize(game.getMyAssetManager().getTexture(OCEAN_TEXTURE).getWidth(),game.getMyAssetManager().getTexture(OCEAN_TEXTURE).getHeight());
                break;
            }
            default:{
                Gdx.app.log("Background", "Paraméterként megadott háttértípus érvénytelen, alapértelmezettként a Csernobil háttér kerül beállításra!");
            }
        }

        if(world != null){
            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.position.set(new Vector2(0, ground));
            Body groundBody = world.createBody(groundBodyDef);
            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox(16 * 2, ground);
            groundBody.createFixture(groundBox, 0.0f);
        }
    }
}
