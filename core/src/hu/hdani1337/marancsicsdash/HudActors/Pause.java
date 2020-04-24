package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

public class Pause extends OneSpriteStaticActor {

    public static final String PAUSE_TEXTURE = "pic/ui/pause.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(PAUSE_TEXTURE);
    }

    public Pause(MyGame game) {
        super(game, PAUSE_TEXTURE);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(getStage() != null && getStage() instanceof MyStage) {
                    if (((MyStage) getStage()).getScreen() != null) {
                        if (((MyStage) getStage()).getScreen() instanceof GameScreen)
                            GameStage.isAct = false;
                        else if (((MyStage) getStage()).getScreen() instanceof BossScreen)
                            BossStage.isAct = false;
                    }
                }
            }
        });
    }
}
