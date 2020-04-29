package pl.edu.agh.metal.spw.kfilesviewer;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Shell extends MeshView {

    private static final float[] TEXES = {0, 0, 1, 0, 1, 1, 0, 1};
    private static final int[] FACES = {
            0, 0, 2, 2, 1, 1,
            2, 2, 0, 0, 3, 3
    };

    public Shell(float[] points) {
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(TEXES);
        mesh.getFaces().addAll(FACES);
        setMesh(mesh);
    }

}
