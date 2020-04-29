package pl.edu.agh.metal.spw.kfilesviewer;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;


public class Hexahedron extends MeshView {

    private static final float[] TEXES = {0, 0, 1, 0, 1, 1, 0, 1};
    private static final int[] FACES = {
            0, 0, 2, 2, 1, 1,
            2, 2, 0, 0, 3, 3,
            1, 0, 6, 2, 5, 1,
            6, 2, 1, 0, 2, 3,
            5, 0, 7, 2, 4, 1,
            7, 2, 5, 0, 6, 3,
            4, 0, 3, 2, 0, 1,
            3, 2, 4, 0, 7, 3,
            3, 0, 6, 2, 2, 1,
            6, 2, 3, 0, 7, 3,
            4, 0, 1, 2, 5, 1,
            1, 2, 4, 0, 0, 3
    };

    public Hexahedron(float[] points) {
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(TEXES);
        mesh.getFaces().addAll(FACES);
        setMesh(mesh);
    }

}
