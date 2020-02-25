package hu.csanyzeg.master.MyBaseClasses.Scene2D;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyShape;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.WorldHelper.WorldHelper;

public class MyGroup extends Group implements  IOriginChanged, InitableInterface, ITimer, IGame, IActorZindex, WorldActor, IActorUtil, IActorComplexCollision, IElapsedTime, IActorOverlaps{

    public MyGroup(MyGame game) {
        super();
        setGame(game);
        setDebug(game.debug);
    }


    @Override
    public void init() {

    }

    //region ITimer Code
    public final Array<Timer> timers = new Array<>();
    public Array<Timer> getTimers() {
        return timers;
    }
    //endregion

    //region IGame Code

    protected MyGame game;

    @Override
    public MyGame getGame() {
        return game;
    }

    @Override
    public void setGame(MyGame game) {
        this.game = game;
    }

    //endregion

    //region ActorZindex Code
    protected int zIndex = 0;

    @Override
    public boolean setZIndex(int index) {
        this.zIndex = index;
        return IActorZindex.super.setZIndex(index);
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }
    //endregion

    //region WorldHelper code
    WorldHelper<?, Actor> actorWorldHelper = null;

    public void setActorWorldHelper(WorldHelper<?, Actor> worldHelper){
        actorWorldHelper = worldHelper;
    }

    @Override
    public WorldHelper<?, Actor> getActorWorldHelper() {
        return actorWorldHelper;
    }

    //endregion

    //region IActorComplexCollision code
    @Deprecated
    protected final HashMap<String, MyShape> shapeMap = new HashMap<String, MyShape>();

    @Deprecated
    public HashMap<String, MyShape> getCollisionShapeMap(){
        return shapeMap;
    }
    //endregion

    //region ElapsedTime code
    protected float elapsedTime = 0;

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    //endregion



    @Override
    public void act(float delta) {
        super.act(delta);
        IElapsedTime.super.act(delta);
        ITimer.super.act(delta);
        WorldActor.super.act(delta);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        sizechangedWorldActor();
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        positionchangedComplexCollision();
        positionchangedWorldActor();
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        rotationchangedComplexCollision();
        rotationchangedWorldActor();
    }

    public void originChanged(){
        originchangedComplexCollision();
        originchangedWorldActor();
    }


    @Override
    public void setSize(float width, float height) {
        setsizeComplexCollision(getWidth(), getHeight(), width, height);
        setOrigin(getOriginX() * width / getWidth(), getOriginY() * height / getHeight());
        super.setSize(width, height);
    }


    @Override
    public void setOriginX(float originX) {
        setOrigin(originX, getOriginY());
        originChanged();
    }

    @Override
    public void setOriginY(float originY) {
        setOrigin(getOriginX(),originY);
        originChanged();
    }

    @Override
    public void setOrigin(float originX, float originY) {
        super.setOrigin(originX, originY);
        originChanged();
    }

    @Override
    public void setOrigin(int alignment) {
        super.setOrigin(alignment);
        originChanged();
    }

    protected void colorChanged(){
        colorchangedWorldActor();
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        colorChanged();
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        colorChanged();
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        setstageWorldActor();
    }

    @Override
    public boolean remove() {
        if (!WorldActor.super.remove()) {
            return super.remove();
        }
        return true;
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        drawComplexCollisionDebugBounds(shapes);
    }

    @Override
    public String toString() {
        return toStr();
    }


}
