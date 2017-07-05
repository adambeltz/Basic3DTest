package com.mygdx.basic3dtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;

public class Main extends ApplicationAdapter {
	public PerspectiveCamera cam;
	public Model model;
	public ModelInstance instance;
	public ModelBatch modelBatch;
	public Environment environment;
	public CameraInputController camController;
	public G3dModelLoader g3dModelLoader;

	
	@Override
	public void create () {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


		modelBatch = new ModelBatch();
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0, 0);
		cam.near = 1f;
		cam.far = 1000f;
		cam.update();

		JsonReader jsonReader = new JsonReader();
		g3dModelLoader = new G3dModelLoader(jsonReader);
		model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("2x2.g3dj", Files.FileType.Internal));
		//model = loader.loadModel(Gdx.files.internal("ship.obj"));
		model.materials.get(0).set(new BlendingAttribute(false, 1.0f));

		instance = new ModelInstance(model);
		//instance.transform.scale(.001f, .001f, .001f);


		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

	}

	@Override
	public void render () {
		cam.update();
		camController.update();
		Gdx.gl.glClearColor(0.5f, 0.2f, 0.6f, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		modelBatch.render(instance,environment);
		modelBatch.end();

	}
	
	@Override
	public void dispose () {
		model.dispose();
		modelBatch.dispose();
	}
}
