package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.Stage.GameStage;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.SoundManager.powerUpSound;

public class Mushroom extends OneSpriteStaticActor implements CollectableItem {

    public static final String MUSHROOM_TEXTURE = "pic/ui/mushroom.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MUSHROOM_TEXTURE);
    }

    private boolean isAct;
    private boolean playing;
    private Zsolti zsolti;

    public Mushroom(MyGame game, GameStage stage) {
        super(game, MUSHROOM_TEXTURE);
        setSize(getWidth()*0.003f, getHeight()*0.003f);
        this.zsolti = stage.zsolti;
        this.isAct = true;
        this.playing = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.isAct && GameStage.isAct) {
            isCollected(zsolti);
        }
    }

    @Override
    public void collected() {
        zsolti.superTime = 8;
        if(!muted && !playing) {
            powerUpSound.play();
            playing = true;
            addTimer(new TickTimer(0.15f,false,new TickTimerListener(){
                @Override
                public void onStop(Timer sender) {
                    super.onStop(sender);
                    playing = false;
                }
            }));
        }
        newPosition();
    }

    public void newPosition(){
        setPosition((float)(72+Math.random()*168),(float)(Math.random()*6+Background.ground*2));
    }
}
