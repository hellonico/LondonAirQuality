package info.hellonico.iccube;

import org.olap4j.*;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.Member;

import java.io.PrintWriter;
import java.sql.*;

public class App {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.olap4j.driver.xmla.XmlaOlap4jDriver");

        final Connection connection = DriverManager.getConnection(

                "jdbc:xmla:Server=http://localhost:8282/icCube/xmla;Catalog=hello",
                "admin",
                "admin"

        );

        final OlapConnection olapConnection = connection.unwrap(OlapConnection.class);

        String query =
                "SELECT {[Measures].[London Mean Background PM2.5 Particulate (ug/m3)]} ON \"value\" [DATE].[DATE].[Year].allmembers ON \"axis\" FROM [cube]";

        final CellSet cellSet = olapConnection.createStatement().executeOlapQuery(query);

        final RectangularCellSetFormatter formatter = new RectangularCellSetFormatter(false);

        final PrintWriter writer = new PrintWriter(System.out);
        formatter.format(cellSet, writer);
        writer.flush();

    }
}