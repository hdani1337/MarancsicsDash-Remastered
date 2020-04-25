package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Ground;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.HudActors.HealthBar;
import hu.hdani1337.marancsicsdash.HudActors.InstantBoss;
import hu.hdani1337.marancsicsdash.HudActors.Jump;
import hu.hdani1337.marancsicsdash.HudActors.Pause;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.backgroundType;
import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.gamemode;

public class HudStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Jump.class,assetList);
        assetList.collectAssetDescriptor(Pause.class,assetList);
        assetList.collectAssetDescriptor(InstantBoss.class,assetList);
    }

    public static MyStage stage;
    private Jump jump;
    private Pause pause;
    private TextBox scoreBoard;
    private Coin coin;
    private MyLabel coinLabel;
    private InstantBoss instantBoss;
    private HealthBar healthBar;
    public ArrayList<Ground> grounds;

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
        healthBar = new HealthBar(game,stage);
        jump = new Jump(game, stage);
        instantBoss = new InstantBoss(game);
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
        grounds = new ArrayList<>();
        generateBaseGrounds();
    }

    @Override
    public void setSizes() {
        instantBoss.setSize(160,160);
        pause.setSize(180,180);
        jump.setSize(180,180);
        coin.setSize(coin.getWidth()*0.7f,coin.getHeight()*0.7f);
    }

    @Override
    public void setPositions() {
        jump.setPosition(getViewport().getWorldWidth()-jump.getWidth()-15,15);
        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth()-15,getViewport().getWorldHeight()-pause.getHeight()-15);
        instantBoss.setPosition(getViewport().getWorldWidth()-instantBoss.getWidth()-15,getViewport().getWorldHeight()/2-instantBoss.getHeight()/2);
        scoreBoard.setPosition(getViewport().getWorldWidth()/2-scoreBoard.getWidth()/2,getViewport().getWorldHeight()-scoreBoard.getHeight()-15);
        coin.setPosition(15, getViewport().getWorldHeight()-15-coin.getHeight());
        coinLabel.setPosition(coin.getX() + coin.getWidth() + 10, coin.getY() + coin.getHeight()/4);
        healthBar.setPosition(15,pause.getY()+pause.getHeight()/2-healthBar.getHeight()/2);
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
        if(stage instanceof GameStage) {
            if (ShopStage.boughtInstantBoss && gamemode == 1) addActor(instantBoss);
            else instantBoss = null;
            addActor(scoreBoard);
            addActor(coin);
            addActor(coinLabel);
        }else if(stage instanceof BossStage){
            addActor(healthBar);
        }
    }

    private void generateBaseGrounds(){
        /**
         * KEZDETNEK 3 HÁTTÉR ELÉG
         * **/
        for (int i = 0; i < 3; i++){
            grounds.add(new Ground(game, backgroundType, getViewport()));
            grounds.get(i).setX(grounds.get(i).getWidth()*i);
            addActor(grounds.get(i));
            grounds.get(i).setZIndex(0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * PONTSZÁMKIJELZŐ FRISSÍTÉSE
         * **/
        if(getScreen() != null && getScreen() instanceof GameScreen){
            if(GameStage.isAct && !Zsolti.isDead) {
                if (!scoreBoard.text.equals(((GameScreen) getScreen()).gameStage.score)) {
                    scoreBoard.setText(((GameScreen) getScreen()).gameStage.score + "");
                    scoreBoard.setX(getViewport().getWorldWidth() / 2 - scoreBoard.getWidth() / 2);
                    if(((GameScreen)getScreen()).gameStage.score >= 25 && gamemode == 1) game.setScreenWithPreloadAssets(BossScreen.class,false, new LoadingStage(game));
                }
            }else{
                if(scoreBoard.isVisible()) scoreBoard.setVisible(false);
            }
        }

        /**
         * HA AZ UTOLSÓ ELŐTTI HÁTTÉR MÁR ÉPPENHOGY BEÉR A KÉPERNYŐRE, AKKOR RAKJUNK BE ÚJ HÁTTERET
         * */
        if (grounds.get(grounds.size() - 2).getX() < getViewport().getWorldWidth()) {
            grounds.add(new Ground(game, backgroundType, getViewport()));
            grounds.get(grounds.size() - 1).setX(grounds.get(grounds.size() - 2).getX() + grounds.get(grounds.size() - 2).getWidth());
            addActor(grounds.get(grounds.size() - 1));
            grounds.get(grounds.size() - 1).setZIndex(0);
        }

        /**
         * HA A TALAJ MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
         * **/
        for (Ground g : grounds) {
            if (g.getX() < -g.getWidth() * 2) {
                g.remove();
                grounds.remove(g);
                break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
            }
        }

        /**
         * PÉNZKIJELZŐ FRISSÍTÉSE
         * **/
        if(!coinLabel.getText().equals(Coin.coin))
            coinLabel.setText(""+Coin.coin);
    }
}
