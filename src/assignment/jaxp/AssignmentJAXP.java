package assignment.jaxp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.xpath.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

public class AssignmentJAXP {

    public static void main(String[] args) throws XPathExpressionException, FileNotFoundException, ParseException {

        XPathFactory factory = XPathFactory.newInstance();
        XPath path = factory.newXPath();

        XPathExpression xPathExpression = path.compile("//book[price>10]/*");

        File xmlDocument = new File("file.xml");
        InputSource inputSource = new InputSource(new FileInputStream(xmlDocument));

        Object result = xPathExpression.evaluate(inputSource, XPathConstants.NODESET);

        NodeList nodeList = (NodeList) result;

        for (int i = 0; i < nodeList.getLength(); i++) {

            boolean toShow = false;

            if (nodeList.item(i).getParentNode().getNodeName().equals("book")) {

                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                Date d1 = sdformat.parse("2005-12-31");
                if (nodeList.item(i).getNodeName().equals("publish_date")) {

                    if (sdformat.parse(nodeList.item(i).getFirstChild().getTextContent()).compareTo(d1) > 0) {

                        toShow = true;
                    }
                }

            }

            if (toShow) {
                for (int j = 0; j < nodeList.item(i).getParentNode().getChildNodes().getLength(); j++) {
                    listNodes(nodeList.item(i).getParentNode().getChildNodes().item(j));

                }

                System.out.print("\n");

            }

        }

    }

    private static void listNodes(Node node) {
        if (node instanceof Text) {
            String value = node.getNodeValue().trim();
            if (value.equals("")) {
                return;
            }
        }

        String nodeName = node.getNodeName().toUpperCase();
        System.out.println(nodeName + ": " + node.getTextContent());

    }

}
