import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;


public class Downloader{
  
  public static byte[] downloadChunk(int start, int end, String url) throws IOException {
	DefaultHttpClient httpClient = getThreadSafeClient();
	HttpGet httpGet = new HttpGet(url);
	httpGet.addHeader("Range", "bytes="+start+"-"+end);
	HttpResponse httpResponse;
	
	//try to execute the httpGet request
	try {
	  httpResponse = httpClient.execute(httpGet);

	  //save the content into a byte array	
	  byte [] tempArray = IOUtils.toByteArray(httpResponse.getEntity().getContent());
	  byte [] returnArray = new byte[tempArray.length];
      System.arraycopy(tempArray, 0, returnArray, 0, tempArray.length);

	  //clean up and shut stuff down...
      //causing bugs right now, I'll refactor this later to prevent any sort of mem leak
	  //httpGet.abort();
	  //httpClient.getConnectionManager().shutdown(); 
	    
      return returnArray;
      
	} catch (ClientProtocolException e) {
	  e.printStackTrace();
	} catch (IOException e) {
	  e.printStackTrace();
	}

	  return null;
  }
  
  //have to use this to get a thread safe client instead of just 
  //making a call to DefaultHttpClient constructor
  public static DefaultHttpClient getThreadSafeClient()  {
	    DefaultHttpClient client = new DefaultHttpClient();
	    ClientConnectionManager mgr = client.getConnectionManager();
	    HttpParams params = client.getParams();
	    client = new DefaultHttpClient(new ThreadSafeClientConnManager(mgr.getSchemeRegistry()), params);
	    return client;
	}
}
