package wllt.resSys.clustering;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class ClusterGenerator {

    static SimpleKMeans clusterer;
    static Instances data;

    public ClusterGenerator() throws Exception {
        this.clusterer = new SimpleKMeans();
        InstanceQuery query = new InstanceQuery();
        query.setUsername("nobody");
        query.setPassword("");
        query.setQuery("select * from whatsoever");
        // You can declare that your data set is sparse
        // query.setSparseData(true);
        this.data = query.retrieveInstances();

//        filter = new AddCluster();
//
//        try
//        {
//            clusterer.setMaxIterations(100);
//            clusterer.setNumClusters(20);
//            filter.setClusterer(clusterer);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public void buildCluster(String fileName)
//    {
//        try
//        {
//            DataSource source = new DataSource(fileName);
//            inst = source.getDataSet();
//            filter.setInputFormat(inst);
//            inst = AddCluster.useFilter(inst, filter);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
    }
}
