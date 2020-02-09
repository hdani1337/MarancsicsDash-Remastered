package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.hdani1337.marancsicsdash.HudActors.Jump;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

public class MenuStage extends MyStage implements StageInterface {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Logo.class, assetList);
        assetList.collectAssetDescriptor(TextBox.class, assetList);
        assetList.addTexture("pic/menuBg.jpg");
    }

    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    private OneSpriteStaticActor MenuBackground;
    private Logo logo;
    private TextBox start;
    private TextBox info;
    private TextBox shop;
    private TextBox options;
    private TextBox exit;
    private TextBox version;

    @Override
    public void assignment() {
        MenuBackground = new OneSpriteStaticActor(game,"pic/menuBg.jpg");
        logo = new Logo(game, Logo.LogoType.MENU);
        start = new TextBox(game ,"A játék indítása",1.25f);
        info = new TextBox(game, "A játékról",1.25f);
        shop = new TextBox(game, "Bolt",1.25f);
        options = new TextBox(game, "Beállítások",1.25f);
        exit = new TextBox(game, "Kilépés",1.25f);
        version = new TextBox(game, "Verzió: 2.0 Epsilon");
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
        System.out.println(getViewport().getWorldWidth());
        version.setWidth(version.getWidth()*0.9f);
        start.setWidth(start.getWidth()*0.95f);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);

        logo.setPosition(getViewport().getWorldWidth()/2-logo.getWidth()/2,getViewport().getWorldHeight()-logo.getHeight()*1.25f);

        start.setX(getViewport().getWorldWidth()/2 - start.getWidth()/2);
        start.setY(getViewport().getWorldHeight()*0.55f - start.getHeight()/2);

        info.setY(start.getY() - info.getHeight()*1.5f);
        info.setX((getViewport().getWorldWidth()/2 - info.getWidth()/2));

        shop.setY(info.getY() - shop.getHeight()*1.5f);
        shop.setX((getViewport().getWorldWidth()/2 - shop.getWidth()/2));

        options.setY(shop.getY() - options.getHeight()*1.5f);
        options.setX((getViewport().getWorldWidth()/2 - options.getWidth()/2));

        exit.setY(options.getY() - exit.getHeight()*1.5f);
        exit.setX((getViewport().getWorldWidth()/2 - exit.getWidth()/2));

        version.setX(40);
        version.setY(30);
    }

    @Override
    public void addListeners() {
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreenWithPreloadAssets(GameScreen.class, new LoadingStage(game));
            }
        });

        info.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        shop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(MenuBackground);
        addActor(logo);
        addActor(start);
        addActor(info);
        addActor(shop);
        addActor(options);
        addActor(exit);
        addActor(version);
    }
}
