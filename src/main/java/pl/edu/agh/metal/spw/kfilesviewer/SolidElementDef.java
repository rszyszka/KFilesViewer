package pl.edu.agh.metal.spw.kfilesviewer;

public class SolidElementDef {

    private int[] nodesCoordinatesIndices;

    public SolidElementDef(int[] nodesCoordinatesIndices) {

        this.nodesCoordinatesIndices = nodesCoordinatesIndices;
    }

    public int[] getNodesCoordinatesIndices() {
        return nodesCoordinatesIndices;
    }
}
