import java.io.InputStream;
import java.io.IOException;

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
      final int totalSize = (end - start) + 1;

      // the maximum transmission unit (MTU) of Ethernet v2
      final int bufSize = 1500;

      final byte[] buffer = new byte[bufSize];
      final byte[] returnArray = new byte[totalSize];
      final InputStream urlStream = httpResponse.getEntity().getContent();

      int bytesRead = 0;
      int position = 0;
      while((bytesRead = urlStream.read(buffer)) != -1) {
        System.arraycopy(buffer, 0, returnArray, position, bytesRead);
        position += bytesRead;

        final double progress = (double)position / (double)totalSize;
        final int percent = (int)(progress * 100);

        ProgressPanel.getInstance().updateProgress(chunkIndex, percent);
      }

      //clean up and shut down
      httpClient.getConnectionManager().shutdown();

      // make sure the bar is filled - account for rounding error
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
  private static DefaultHttpClient getThreadSafeClient() {
    final DefaultHttpClient dummyClient = new DefaultHttpClient();
    final ClientConnectionManager mgr = dummyClient.getConnectionManager();
    final HttpParams params = dummyClient.getParams();
    final DefaultHttpClient client =
      new DefaultHttpClient(new ThreadSafeClientConnManager(mgr.getSchemeRegistry()), params);
    return client;
  }
}
