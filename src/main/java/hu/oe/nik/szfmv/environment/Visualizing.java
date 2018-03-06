package hu.oe.nik.szfmv.environment;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Visualizing {

    /**
     * Creates elements that can be Visualized
     *
     * @param xmlToBuild  location of the xml containing WorldObjects
     */
    public static List<WorldObject> build(String xmlToBuild) throws ParserConfigurationException, IOException, SAXException {
        List<WorldObject> ObjectListToReturn = new List<WorldObject>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<WorldObject> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(WorldObject worldObject) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends WorldObject> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends WorldObject> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public WorldObject get(int index) {
                return null;
            }

            @Override
            public WorldObject set(int index, WorldObject element) {
                return null;
            }

            @Override
            public void add(int index, WorldObject element) {

            }

            @Override
            public WorldObject remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<WorldObject> listIterator() {
                return null;
            }

            @Override
            public ListIterator<WorldObject> listIterator(int index) {
                return null;
            }

            @Override
            public List<WorldObject> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        File inputFile = new File(xmlToBuild);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        NodeList elements = doc.getElementsByTagName("Object");
        for (int temp=0; temp<elements.getLength(); temp++){
            Node element = elements.item(temp);
            ObjectListToReturn.add(readValueFromXml(element));

        }
        return ObjectListToReturn;
    }

    private static WorldObject readValueFromXml(Node xmlElement) {
        return null;
    }
}
