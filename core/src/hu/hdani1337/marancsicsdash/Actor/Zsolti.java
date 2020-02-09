package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.physics.box2d.World;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Zsolti extends OneSpriteAnimatedActor {

    public static final String ZSOLTI_ATLAS = "atlas/zsolti.atlas";
    public static final String SUPER_ZSOLTI_ATLAS = "atlas/superZsolti.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(ZSOLTI_ATLAS);
        assetList.addTextureAtlas(SUPER_ZSOLTI_ATLAS);
    }

    public Zsolti(MyGame game) {
        super(game, ZSOLTI_ATLAS);
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(elapsedTime % 5.0 < 2) setTextureAtlas(game.getMyAssetManager().getTextureAtlas(SUPER_ZSOLTI_ATLAS));
        else setTextureAtlas(game.getMyAssetManager().getTextureAtlas(ZSOLTI_ATLAS));
    }
}