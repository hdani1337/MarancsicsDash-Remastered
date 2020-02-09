package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class TextBox extends MyGroup implements StageInterface {

    public static final String TEXTBOX_TEXTURE = "pic/textBG.png";
    public static final String RETRO_FONT = "font/fontstyle.ttf";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(TEXTBOX_TEXTURE);
        assetList.addFont(RETRO_FONT, RETRO_FONT, 120, Color.WHITE, AssetList.CHARS);
    }

    public String text;

    private OneSpriteStaticActor textBackground;
    private MyLabel textLabel;
    private float scale;

    public TextBox(MyGame game, String text) {
        this(game,text,1);
    }

    public TextBox(MyGame game, String text, float scale){
        super(game);
        this.text = text;
        this.scale = scale;
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        textBackground = new OneSpriteStaticActor(game, TEXTBOX_TEXTURE);
        textLabel = new MyLabel(game, text, new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
    }

    @Override
    public void setSizes() {
        textBackground.setSize(textLabel.getWidth()*1.3f, textLabel.getHeight()*1.3f);
        setScales();
    }

    @Override
    public void setPositions() {
        textLabel.setAlignment(Align.center);
        textBackground.setPosition(0,0);
        textLabel.setPosition(textBackground.getX()+textBackground.getWidth()/2-textLabel.getWidth()/2,textBackground.getY()+textBackground.getHeight()/2-textLabel.getHeight()/2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(textBackground);
        addActor(textLabel);
    }

    private void setScales(){
        textBackground.setSize(textBackground.getWidth()*scale, textBackground.getHeight()*scale);
        textLabel.setFontScale(scale);
    }
}
