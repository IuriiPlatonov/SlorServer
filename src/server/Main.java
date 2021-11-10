package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

import lombok.SneakyThrows;


public class Main
{

    @SneakyThrows
    public static void main(String... args)
    {

        HttpSolrClient solr = new HttpSolrClient.Builder(Сonstant.solrCoreUrl)
            .build();
        solr.setParser(new XMLResponseParser());

        Class.forName("oracle.jdbc.driver.OracleDriver");

        try (Connection connection = DriverManager.getConnection(Сonstant.url,
            Сonstant.username, Сonstant.password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement
                    .executeQuery(Сonstant.sqlQuery);)
        {

            ExecutorService executor = Executors.newWorkStealingPool();
            Set<Callable<String>> callables = new HashSet<>();

            while (resultSet.next())
            {
                SolrInputDocument document = getSolrDoc(resultSet);

                callables.add(() -> {

                    solr.add(document);
                    solr.commit();

                    return Thread.currentThread().getName().concat(" - Ok");
                });

            }

            List<Future<String>> futures = executor.invokeAll(callables);
            for (Future<String> future : futures)
            {
                System.out.println(future.get());
            }

            executor.shutdown();

        }

    }

    @SneakyThrows
    private static SolrInputDocument getSolrDoc(ResultSet resultSet)
    {
        SolrInputDocument document = new SolrInputDocument();

        document.addField(Сonstant.catDccrdkndstIdPath, resultSet.getString(1));
        document.addField(Сonstant.catRoot, resultSet.getString(2));
        document.addField(Сonstant.catDccrdkndstId, resultSet.getString(3));
        document.addField(Сonstant.catName, resultSet.getString(4));
        document.addField(Сonstant.catIdentifier, resultSet.getString(5));
        document.addField(Сonstant.catDescription, resultSet.getString(6));
        document.addField(Сonstant.catOrderNumber, resultSet.getLong(7));
        document.addField(Сonstant.catKindId, resultSet.getString(8));
        document.addField(Сonstant.catParentId, resultSet.getString(9));
        document.addField(Сonstant.catBaseId, resultSet.getString(10));
        document.addField(Сonstant.catMaximumInstances, resultSet.getLong(11));
        document.addField(Сonstant.catGroupsInd, resultSet.getString(12));
        document.addField(Сonstant.catGroupsId, resultSet.getString(13));
        document.addField(Сonstant.catGroupsName, resultSet.getString(14));
        document.addField(Сonstant.catKindsInd, resultSet.getString(15));
        document.addField(Сonstant.catKindsId, resultSet.getString(16));
        document.addField(Сonstant.catKindsName, resultSet.getString(17));

        return document;
    }

}
