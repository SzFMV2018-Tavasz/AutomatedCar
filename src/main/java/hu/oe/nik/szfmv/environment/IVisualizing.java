package hu.oe.nik.szfmv.environment;

import java.util.List;

public interface IVisualizing {
    /**
     * Creates a marker for the elements that can be Visualized,
     * has to be implemented in subclass
     *
     * @param xmlToBuild  location of the xml containing WorldObjects
     */
    static List<WorldObject> build(String xmlToBuild){return null;}

}
