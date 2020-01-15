package jsonParsing;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SparkSession.Builder;
import org.apache.spark.sql.hive.HiveContext;

public class Application {
	
	public static void main(String[] args) {
		
		//String warehouseLocation = new File("spark-warehouse").getAbsolutePath();
		/*SparkSession spark = new SparkSession.Builder()
				.appName("JSON Parsing to Hive")
				//.master("local[*]")
				//.config("spark.sql.warehouse.dir",warehouseLocation)
				.config("hive.metastore.uris", "thrift://instance-1.us-east1-b.c.central-binder-262323.internal:9083,")
				.enableHiveSupport()
				.getOrCreate();
		
	
		Dataset<Row> jsontest = spark.read().option("inferSchema", true)
				.json("hdfs://instance-1.us-east1-b.c.central-binder-262323.internal/user/cloudera/sample.json").cache();
		
		jsontest.createOrReplaceGlobalTempView("jsontest1");
		Dataset<Row> showall = spark.sql("SELECT * FROM global_temp.jsontest1");
		showall.show();
		spark.stop();
		*/
		
		
		    SparkConf sparkConf = new SparkConf().setAppName("workload_name");
	        JavaSparkContext sc = new JavaSparkContext(sparkConf);
	        @SuppressWarnings("deprecation")
			HiveContext hiveontext = new HiveContext(sc);	
	        hiveontext.setConf("hive.metastore.uris", "thrift://instance-1.us-east1-b.c.central-binder-262323.internal:9083");
	        
	        Dataset<Row> jsonDF = hiveontext.read().option("inferSchema", true)
					.json("hdfs://instance-1.us-east1-b.c.central-binder-262323.internal/user/cloudera/sample.json").cache();
	        jsonDF.printSchema();
	        jsonDF.show(3);
	        HashMap<String,String> pathmap = new HashMap<String,String>();
	        pathmap.put("path", "hdfs://instance-1.us-east1-b.c.central-binder-262323.internal//warehouse/tablespace/external/hive/bdp_db");
	        jsonDF.write().options(pathmap).format("orc").mode(SaveMode.Append).saveAsTable("bdp_dbjsonTest2");
	}
	        

}
