package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class InstantBoss extends OneSpriteStaticActor {

    public static final String INSTANTBOSS_TEXTURE = "pic/instantBoss.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(INSTANTBOSS_TEXTURE);
    }

    public InstantBoss(MyGame game) {
        super(game, INSTANTBOSS_TEXTURE);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenWithPreloadAssets(BossScreen.class,new LoadingStage(game));
            }
        });
    }
}
