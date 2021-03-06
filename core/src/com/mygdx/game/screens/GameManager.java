package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Library;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.drawable.Button;
import com.mygdx.game.drawable.Colors;
import com.mygdx.game.drawable.Rectangle;
import com.mygdx.game.drawable.Text;

public class GameManager implements InputProcessor {
    MyGdxGame main;
    Vector2 touchedDown;
    Vector2 mid;
    Vector2 position;
    int gridSize = 6;
    float offset; //calculate based on the grid size
    float padding = 0.1f;
    float paddingPixels;

    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    public Rectangle background;

    boolean started = false;
    boolean countingDown = false;

	double life = 0;

    Color backgroundColor = Colors.rgb(251, 254, 249);
	Text title;
	Text counter;

    Button testButton = new Button(width/2, height - height/3, width/3, width/10);
    Rectangle[] rectangles = new Rectangle[9];
    Color[] colors = {
    		Colors.Blue,
		    Colors.DarkBlue,
		    Colors.DarkPurple,
		    Colors.DarkRed,
		    Colors.DarkGreen,
		    Colors.Green,
		    Colors.MidnightBlue
//		    Colors.rgb(186, 63, 29)
    };
    Color goalColor = backgroundColor;

    boolean getFocused() {
    	return t;
    }
    	//((int)Library.random(0,100)%2) == 0; }

    //Level and game data variables
	int level = 1;
    int index = 0;
    int cleared = 0;
    double focusTime = 5;
    double currentFocusTime = 0;
	public boolean t = false;

    GameManager(MyGdxGame main) {
        this.main = main;
        Gdx.input.setInputProcessor(this);
        mid = new Vector2(width/2, height/2);
        paddingPixels = (padding * width);

        int gameSize = (int)(width * (1 - 2 * padding));
        offset = gameSize / (float)gridSize;
        position = new Vector2();

	    title = new Text(this.main.font50, "Focus", width/2, height - height*.11f);
	    title.color = Colors.rgb(42, 183, 202);
	    counter = new Text(this.main.font50, "3", width/2, height - height*.15f - 50);
	    counter.color = Colors.rgb(42, 183, 202);
	    counter.Visible = false;

	    background = new Rectangle(0, 0, width, height);
        background.color = backgroundColor;
        Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
		        startCountDown();
	        }
        };
        testButton.setRunnable(runnable);
//        int spacing = 50;
        int rectangleSize = (width - 100)/3;
        for (int i = 0; i < 9; i++) {
        	int x = i % 3;
        	int y = i / 3;
	        x = x*25 + x*rectangleSize + 25;
	        y = y*25 + y*rectangleSize + 50;

        	rectangles[i] = new Rectangle(x, y, rectangleSize, rectangleSize);
        	rectangles[i].radius = 15;
        	rectangles[i].Visible = false;
        	rectangles[i].Fade = false;
        }
        setRandomRectangleColors();
    }
    Color getRandomColor() {
    	int index = (int)Library.random(0, colors.length);
    	return colors[index];
    }
    void setRandomRectangleColors() {
    	for (int i = 0; i < 9; i++) {
    		rectangles[i].color = getRandomColor();
	    }
    }


    void updateRectangles(float delta) {
	    int focus;
	    if (getFocused()) {
		    focus = 1;
	    }   else {
			focus = 0;
	    }

	    currentFocusTime = currentFocusTime + focus * delta;

    	for (int i = 0; i < 9; i++) {
			rectangles[i].Update(focus * delta);
		    if (index == i) {
		    	if (!rectangles[i].Fade) {
		    		index++;
		    		if (index < 9) {
					    rectangles[index].Fade = true;
					    rectangles[index].FadeColor = backgroundColor;
					    rectangles[index].Life = 0;
					    rectangles[index].FadeTime = 5f;
				    }
			    }
		    }
	    }
    }

    void restart() {
    	started = false;
    	countingDown = false;
    	life = 0;
    	testButton.Visible = false;
	    //variable clear
	    level = 1;
	    index = 0;
	    cleared = 0;
	    focusTime = 5;
	    currentFocusTime = 0;
    }

    void startCountDown() {
    	countingDown = true;
    	counter.Visible = true;
    	testButton.Visible = false;
    	title.setText("Get Ready!");
    }

    void startGame() {
    	countingDown = false;
    	started = true;
    	counter.Visible = false;
    	title.setText("Focus");
	    for (int i = 0; i < 9; i++) {
		    rectangles[i].Visible = true;
	    }
	    rectangles[0].Fade = true;
	    rectangles[0].FadeColor = goalColor;

	    //restart();
    }

    void print(String title, Vector2 vector) {
        System.out.println(title + " x: " + vector.x + ", y: " + vector.y);
    }

    public void update(float delta) {
    	if (countingDown && !started) {
    		life += delta;
    		if (life < 4) {
    			counter.setText(""+(3 - (int)life));
		    } else {
    			startGame();
		    }
	    } else {
    		updateRectangles(delta);
	    }
    }

    public void renderText() {
        main.batch.begin();
        title.render(main.batch, true);
        counter.render(main.batch, true);
        main.batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        background.render(main.shapeRenderer, true);

	    testButton.render(main.shapeRenderer, true);
	    for (int i = 0; i < 9;i++) {
	    	rectangles[i].render(main.shapeRenderer, true);
	    }

        main.shapeRenderer.end();

        renderText();

        main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        main.shapeRenderer.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchedDown = new Vector2(screenX, screenY);
	    testButton.checkForClick(screenX, height - screenY);
//	    t = false;
	    return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        t = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}