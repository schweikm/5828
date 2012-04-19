import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParrallelDownloader {
  //just arbitrarily came up with this
  private final double blockingCoefficient = .9;

  public void  download(String targetUrl, String destinationFile, int size) throws InterruptedException, ExecutionException {
  
	final int numberOfCores = Runtime.getRuntime().availableProcessors();
	//right now I'm passing this in but
	//there should be a way to calculate 
	//it with an HTTP request though
    final int fileSize = size;
    final int poolSize = (int)(numberOfCores/ (1-blockingCoefficient));
    final String url = targetUrl;
    
    final List<Callable<byte[]>> partitions = new ArrayList<Callable<byte[]>>();
        
    //create a partition for each thread in our thread pool
    for(int i = 0; i<poolSize; i++) {
      final int j = i;
      final int start = (int)(Math.floor((double)fileSize/poolSize))*(j);
      final int end = (int)((Math.floor((double)fileSize/poolSize))*(j+1)-1);

      partitions.add(new Callable <byte[]> () {
        public byte[]  call() throws Exception {
          return Downloader.downloadChunk(start, end, url);
        }
      });
    }
    
    final int remainder = fileSize%poolSize;
    
    //Add one last partition if a remainder exists
    if (remainder != 0) {
      partitions.add(new Callable <byte[]> () {
        public byte[]  call() throws Exception {
          return Downloader.downloadChunk(fileSize-remainder, fileSize-1, url);
        }
      });
    }
    
    
    final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
    final List<Future<byte[]>> results = executorPool.invokeAll(partitions, 1000, TimeUnit.SECONDS);

    byte [] finalByteArray = new byte[0];
    byte [] finalTempByteArray;
    byte [] newByteArray;

    //when the threads finish combine their results
    //using fixed thread pool we are guaranteed to iterate 
    //through finals in order so we can just add them sequentially
    for(final Future<byte[]> result : results) {
      newByteArray = new byte[result.get().length];
      System.arraycopy(result.get(), 0, newByteArray, 0, result.get().length);
      finalTempByteArray = new byte[finalByteArray.length];
      
      System.arraycopy(finalByteArray, 0, finalTempByteArray, 0, finalByteArray.length);
      
      finalByteArray = new byte[newByteArray.length + finalTempByteArray.length];
      
	  System.arraycopy(finalTempByteArray, 0, finalByteArray, 0, finalTempByteArray.length);
      System.arraycopy(newByteArray, 0, finalByteArray, finalTempByteArray.length, newByteArray.length);
      executorPool.shutdown();
    } 
    
    try
    {
     FileOutputStream fos = new FileOutputStream(destinationFile);
      fos.write(finalByteArray);     
      fos.close();
    }
    catch(FileNotFoundException ex)
    {
     System.out.println("FileNotFoundException : " + ex);
    }
    catch(IOException ioe)
    {
     System.out.println("IOException : " + ioe);
    }

  }

}