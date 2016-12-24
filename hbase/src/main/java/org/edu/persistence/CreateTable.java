package org.edu.persistence;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import com.google.protobuf.ServiceException;

/**
 * Get a connection to HBase and create a table.
 * 
 * @author shivam.maharshi
 */
public class CreateTable {

	public static void main(String[] args) throws IOException, ServiceException {
		Configuration conf = HBaseConfiguration.create();
		conf.clear();
		conf.set("hbase.zookeeper.quorum", "hadoop.dlib.vt.edu");
		conf.setInt("hbase.zookeeper.property.clientPort", 10000);
		conf.set("hbase.master", "hadoop.dlib.vt.edu:60000");
		HBaseAdmin.checkHBaseAvailable(conf);
		Connection conn = ConnectionFactory.createConnection(conf);
		Admin admin = conn.getAdmin();
		// Table table = conn.getTable(TableName.valueOf("sample"));
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("sample1"));
		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		tableDescriptor.addFamily(new HColumnDescriptor("professional"));
		// Execute the table through admin
		admin.createTable(tableDescriptor);
		System.out.println(" Table created ");
		// table.close();
		conn.close();
	}

}
