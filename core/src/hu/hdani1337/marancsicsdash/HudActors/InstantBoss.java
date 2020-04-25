package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;
import hu.hdani1337.marancsicsdash.Stage.GameStage;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;
import hu.hdani1337.marancsicsdash.Stage.ShopStage;

public class InstantBoss extends OneSpriteStaticActor {

    public static final String INSTANTBOSS_TEXTURE = "pic/ui/instantBoss.png";

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
                if(getStage() != null && !(getStage() instanceof ShopStage) && !Zsolti.isDead) {
                    if(((MyStage)getStage()).getScreen() != null){
                        if(((MyStage)getStage()).getScreen() instanceof GameScreen){
                            if(GameStage.isAct) {
                                game.setScreenWithPreloadAssets(BossScreen.class, false, new LoadingStage(game));
                            }
                        }
                    }
                }
            }
        });
    }
}
