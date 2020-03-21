package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.HudActors.Jump;
import hu.hdani1337.marancsicsdash.HudActors.Pause;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;

public class HudStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Jump.class,assetList);
    }

    public static GameStage stage;
    private Jump jump;
    private Pause pause;
    private TextBox scoreBoard;
    private Coin coin;
    private MyLabel coinLabel;

    public HudStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        setCameraMoveToXY(10000,10000,1);
    }

    @Override
    public void assignment() {
        jump = new Jump(game, stage);
        pause = new Pause(game);
        scoreBoard = new TextBox(game,"0");
        coin = new Coin(game, true);
        coinLabel = new MyLabel(game, "-NULL-", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {
                if(Coin.coin >= 0) setText(""+Coin.coin);
                else setText("0");
            }
        };
    }

    @Override
    public void setSizes() {
        jump.setSize(jump.getWidth()*1.2f,jump.getHeight()*1.2f);
        coin.setSize(coin.getWidth()*0.7f,coin.getHeight()*0.7f);
    }

    @Override
    public void setPositions() {
        jump.setPosition(getViewport().getWorldWidth()-jump.getWidth()-15,15);
        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth()-15,getViewport().getWorldHeight()-pause.getHeight()-15);
        scoreBoard.setPosition(getViewport().getWorldWidth()/2-scoreBoard.getWidth()/2,getViewport().getWorldHeight()-scoreBoard.getHeight()-15);
        coin.setPosition(15, getViewport().getWorldHeight()-15-coin.getHeight());
        coinLabel.setPosition(coin.getX() + coin.getWidth() + 10, coin.getY() + coin.getHeight()/4);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(jump);
        addActor(pause);
        addActor(scoreBoard);
        addActor(coin);
        addActor(coinLabel);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * PONTSZÁMKIJELZŐ FRISSÍTÉSE
         * **/
        if(getScreen() != null && getScreen() instanceof GameScreen){
            if(!scoreBoard.text.equals(((GameScreen)getScreen()).gameStage.score)) {
                scoreBoard.setText(((GameScreen)getScreen()).gameStage.score + "");
                scoreBoard.setX(getViewport().getWorldWidth()/2-scoreBoard.getWidth()/2);
            }
        }

        /**
         * PÉNZKIJELZŐ FRISSÍTÉSE
         * **/
        if(!coinLabel.getText().equals(Coin.coin))
            coinLabel.setText(""+Coin.coin);
    }
}
