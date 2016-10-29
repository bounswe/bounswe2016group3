package bounswegroup3.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

public class AmazonClient implements ServiceClient {
    private AmazonS3 client;
    private String bucket;
    private SecureRandom random;
    
	public AmazonClient(String bucket, String accessKey, String secretKey) {
		client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
		client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
		
		this.bucket = bucket;
		
		random = new SecureRandom();
	}
	
	public String uploadFile(InputStream stream) {
		try {
			File file = File.createTempFile("amazon-s3-", "");
			file.deleteOnExit();
			
			IOUtils.copy(stream, new FileOutputStream(file));
			
			String filename = new BigInteger(130, random).toString(32);
			
			client.putObject(new PutObjectRequest(bucket, filename, file));
			
			return "https://s3.eu-central-1.amazonaws.com/" + bucket + "/" + filename;

		} catch (IOException e) {
			e.printStackTrace();
			
			return "";
		}
	}
	
	@Override
	public boolean checkValidity() {
		try {
			client.listBuckets();
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}

}
