package agrawal.snehal.allwidgetexample.xmlparsersupport;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser {
    public XMLParser() {
    }

    public Document getDomElement(String xml) {
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        return doc;
    }

    private String getElementValue(Node elem) {
        Node child;
        if (elem != null)
            if (elem.hasChildNodes())
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE)
                        return child.getNodeValue();
                }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
}
