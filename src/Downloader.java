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

  public static byte[] downloadChunk(final int start,
                                     final int end,
                                     final String url,
                                     final int chunkIndex)
  throws IOException {

    final DefaultHttpClient httpClient = getThreadSafeClient();
    final HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader("Range", "bytes="+start+"-"+end);

    //try to execute the httpGet request
    try {
      final HttpResponse httpResponse = httpClient.execute(httpGet);

      //save the content into a byte array	
      byte [] tempArray = IOUtils.toByteArray(httpResponse.getEntity().getContent());
      byte [] returnArray = new byte[tempArray.length];
      System.arraycopy(tempArray, 0, returnArray, 0, tempArray.length);

      //clean up and shut stuff down...
      httpClient.getConnectionManager().shutdown();

      //:MAINTENANCE
      // I'm not sure how we are going to accurately track progress here ...
      ProgressPanel.getInstance().updateProgress(chunkIndex, 100);

      return returnArray;

    } catch (final ClientProtocolException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  //have to use this to get a thread safe client instead of just 
  //making a call to DefaultHttpClient constructor
  public static DefaultHttpClient getThreadSafeClient() {
    final DefaultHttpClient dummyClient = new DefaultHttpClient();
    final ClientConnectionManager mgr = dummyClient.getConnectionManager();
    final HttpParams params = dummyClient.getParams();
    final DefaultHttpClient client =
      new DefaultHttpClient(new ThreadSafeClientConnManager(mgr.getSchemeRegistry()), params);
    return client;
  }
}
