package info.hellonico.iccube;

import org.olap4j.*;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.Member;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class App {
    
    public static String mdxFileToStringQuery(String resourceFile) throws IOException {
        URI sqlScriptUrl = URI.create(App.class.getResource(resourceFile).toExternalForm());
        return Files.readString(Path.of(sqlScriptUrl));
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        String url = "jdbc:xmla:Server=http://localhost:8282/icCube/xmla;Catalog=hello";
        Connection connection =
                DriverManager.getConnection(url, "admin", "admin");

        OlapConnection olapConnection = connection.unwrap(OlapConnection.class);
        CellSet cellSet = olapConnection.createStatement().executeOlapQuery(mdxFileToStringQuery("/airquality.mdx"));
        RectangularCellSetFormatter formatter = new RectangularCellSetFormatter(false);
        PrintWriter writer = new PrintWriter(System.out);
        formatter.format(cellSet, writer);
        writer.flush();

    }
}