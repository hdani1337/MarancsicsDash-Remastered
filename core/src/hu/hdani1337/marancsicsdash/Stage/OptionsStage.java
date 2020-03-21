package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.MenuStage.MENU_BG_TEXTURE;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtDesert;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtOcean;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtSiberia;
import static hu.hdani1337.marancsicsdash.Stage.ShopStage.boughtZala;

public class OptionsStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
    }

    public static int difficulty = preferences.getInteger("difficulty");;
    public static int gamemode = preferences.getInteger("gamemode");
    public static int selectedBackground = preferences.getInteger("selectedBackground");

    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    private OneSpriteStaticActor MenuBackground;

    private TextBox backButton;
    private TextBox muteButton;
    private TextBox gameModeButton;
    private TextBox backgroundTypeButton;
    private TextBox difficultyButton;

    private boolean setBack;

    @Override
    public void assignment() {
        setBack = false;
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        backButton = new TextBox(game,"Vissza a menübe");
        muteButton = new TextBox(game, "Némítás: -NULL-");
        gameModeButton = new TextBox(game, "Játékmód: -NULL-");
        backgroundTypeButton = new TextBox(game, "Háttér: -NULL-");
        difficultyButton = new TextBox(game, "Nehézség: -NULL-");
        setTexts();
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        muteButton.setPosition(75,200);
        gameModeButton.setPosition(75,350);
        backgroundTypeButton.setPosition(75,500);
        difficultyButton.setPosition(75,650);
        backButton.setPosition(getViewport().getWorldWidth() - (backButton.getWidth() + 45),50);
    }

    @Override
    public void addListeners() {
        backButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.putInteger("difficulty",difficulty);
                preferences.putInteger("gamemode",gamemode);
                preferences.putInteger("selectedBackground",selectedBackground);
                preferences.putBoolean("muted",muted);
                preferences.flush();
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
                setBack = true;
            }
        });

        muteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!muted){
                    muted = true;
                }
                else{
                    muted = false;
                }
                setTexts();
            }
        });

        gameModeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(gamemode == 1){
                    gamemode = 2;
                }
                else{
                    gamemode = 1;
                }
                setTexts();
            }
        });

        backgroundTypeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(selectedBackground < 4) selectedBackground++;
                else selectedBackground = 0;

                if(selectedBackground == 1 && !boughtSiberia) selectedBackground = 2;
                if(selectedBackground == 2 && !boughtZala) selectedBackground = 3;
                if(selectedBackground == 3 && !boughtDesert) selectedBackground = 4;
                if(selectedBackground == 4 && !boughtOcean) selectedBackground = 0;
                setTexts();
            }
        });

        difficultyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(difficulty == 3){
                    difficulty = 1;
                }
                else{
                    difficulty++;
                }
                setTexts();
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(MenuBackground);
        addActor(difficultyButton);
        addActor(backButton);
        addActor(muteButton);
        addActor(gameModeButton);
        if(boughtSiberia || boughtZala)
        {
            addActor(backgroundTypeButton);
        }
    }

    /**
     * Tudom hogy Switchel szebb és hatékonyabb, kuss
     * **/
    private void setTexts(){
        //Némítás
        if(muted){
            muteButton.setText("Némítás: Némítva");
            //music.stop();
        }
        else{
            muteButton.setText("Némítás: Nincs némítva");
        }

        //Nehézségek
        if(difficulty == 1){
            difficultyButton.setText("Nehézség: Könnyü");
        }
        else if(difficulty == 2){
            difficultyButton.setText("Nehézség: Normál");
        }
        else if(difficulty == 3){
            difficultyButton.setText("Nehézség: Nehéz");
        }
        else{
            System.out.println("A fejlesztő egy buzi.");
            difficultyButton.setText("Nehézség: Hiba");
        }

        //Játékmód
        if(gamemode == 1){
            gameModeButton.setText("Játékmód: Story");
        }
        else{
            gameModeButton.setText("Játékmód: Endless");
        }

        //Háttér
        if(selectedBackground == 0){
            backgroundTypeButton.setText("Háttér: Csernobil");
        }
        else if (selectedBackground == 1){
            backgroundTypeButton.setText("Háttér: Szibéria");
        }
        else if (selectedBackground == 2){
            backgroundTypeButton.setText("Háttér: Zala");
        }
        else if (selectedBackground == 3){
            backgroundTypeButton.setText("Háttér: Szahara");
        }
        else if (selectedBackground == 4){
            backgroundTypeButton.setText("Háttér: Atlanti-óceán");
        }
    }

    float alpha = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) alpha += 0.05;
            else alpha = 1;
            setAlpha();
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
            if (alpha > 0.05) {
                setAlpha();
                alpha -= 0.05;
            } else {
                //Ha már nem látszanak akkor megyünk vissza a menübe
                alpha = 0;
                setAlpha();
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
                addTimer(new TickTimer(1,false,new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        setBack = false;
                    }
                }));
            }
        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        backButton.setAlpha(alpha);
        muteButton.setAlpha(alpha);
        gameModeButton.setAlpha(alpha);
        backgroundTypeButton.setAlpha(alpha);
        difficultyButton.setAlpha(alpha);
    }
}
