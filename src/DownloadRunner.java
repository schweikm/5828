import java.util.concurrent.ExecutionException;

public class DownloadRunner {
  public static void main(String [] args) {    
	  ParrallelDownloader p = new ParrallelDownloader();
	  String url = "http://87.media.v4.skyrock.net/music/87c/c67/87cc6741833cac7eaeda396895b355fc.mp3";
      String outputFile = "somebodyThatIUsedToKnow.mp3";
      int fileSize = 4903752;
	  try {
		p.download(url, outputFile, fileSize);
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	} catch (ExecutionException e1) {
		e1.printStackTrace();
	}
  }
}