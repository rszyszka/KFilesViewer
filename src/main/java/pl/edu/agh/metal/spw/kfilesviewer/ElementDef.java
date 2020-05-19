package pl.edu.agh.metal.spw.kfilesviewer;

public class ElementDef {

    private Type type;
    private int[] nodesCoordinatesIndices;

    public ElementDef(int[] nodesCoordinatesIndices, Type type) {
        this.type = type;
        this.nodesCoordinatesIndices = nodesCoordinatesIndices;
    }

    public int[] getNodesCoordinatesIndices() {
        return nodesCoordinatesIndices;
    }

    public Type getType() {
        return type;
    }

    enum Type {
        SOLID, SHELL
    }
}
