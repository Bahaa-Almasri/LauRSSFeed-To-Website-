import java.io.*;
import java.net.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToHtml {
    public static void main(String[] args) throws Exception {

        URL url = new URL("https://news.lau.edu.lb/rss.xml");

        File outToFile = new File("output.html");

        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);

        StringBuilder sb = new StringBuilder();

        NodeList nodeList = document.getElementsByTagName("item");

        Node node;

        Element element;

        String title;

        String description;

        // Start HTML document
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<title>News</title>\n");
        sb.append("<style>\n");
        sb.append("body { font-family: Arial, sans-serif; }\n");
        sb.append("h2 { color: #333; }\n");
        sb.append("p { margin-bottom: 20px; }\n");
        sb.append("</style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");

        for (int i = 0; i < nodeList.getLength(); i++) {
            node = nodeList.item(i);
            element = (Element) node;

            Node titleNode = element.getElementsByTagName("title").item(0);
            if (titleNode != null) {
                title = titleNode.getTextContent();
            } else {
                title = "No title found"; // Handle the case where no title element is found
            }

            sb.append("<h2>Title: " + title + "</h2>\n");

            Node descriptionNode = element.getElementsByTagName("description").item(0);
            if (descriptionNode != null) {
                description = descriptionNode.getTextContent();
            } else {
                description = "No description found";
            }

            sb.append("<p>Description: " + description + "</p>\n\n");
        }

        // End HTML document
        sb.append("</body>\n");
        sb.append("</html>\n");

        // Write the HTML content to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outToFile))) {
            writer.write(sb.toString());
        }

        System.out.println("HTML content saved to output.html");
    }
}