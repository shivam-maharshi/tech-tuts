package org.edu;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * Save and read an image into & from MongoDB Grid File System which is a
 * specification for storing BSON-document exceeding size limit of 16MB. It
 * stores bigger files in chunks of specified size. This allows to skip to the
 * portion of an audio or video file quickly. More on Grid file system.
 * 
 * Link: http://api.mongodb.org/java/current/com/mongodb/gridfs/GridFS.html
 * 
 * @author shivam.maharshi
 */
public class ImageHandling {

	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("imagedb");
			String newFileName = "virginiaTech";
			File imageFile = new File("C:\\Shivam\\Pics\\VirginiaTech.jpg");
			// create a "photo" namespace
			GridFS gfsPhoto = new GridFS(db, "photo");
			// get image file from local drive
			GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
			// set a new filename for identify purpose
			gfsFile.setFilename(newFileName);
			// save the image file into mongoDB
			gfsFile.save();
			DBCursor cursor = gfsPhoto.getFileList();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			// get image file by it's filename
			GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);
			// save it into a new image file
			imageForOutput.writeTo("C:\\Shivam\\Pics\\VirginiaTechNew.jpg");
			// remove the image file from mongoDB
			gfsPhoto.remove(gfsPhoto.findOne(newFileName));
			System.out.println("Done");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
