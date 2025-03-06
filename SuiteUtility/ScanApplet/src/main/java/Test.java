import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
//		// TODO Auto-generated method stub
//		 String ipAddress = "portaleservizi.eng.it";
//		    InetAddress inet = InetAddress.getByName(ipAddress);
//
//		    System.out.println("Sending Ping Request to " + ipAddress);
//		    System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
//
//		    ipAddress = "172.27.1.139:8081";
//		    inet = InetAddress.getByName(ipAddress);
//
//		    System.out.println("Sending Ping Request to " + ipAddress);
//		    System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		 HttpURLConnection connection = null;
		    try {
		        URL u = new URL("https://portaledasdasdfservizi.eng.it/AurigaWeb-0.0.1-SNAPSHOT/uploaddascanner");
		        connection = (HttpURLConnection) u.openConnection();
		        connection.setRequestMethod("HEAD");
		        int code = connection.getResponseCode();
		        System.out.println("" + code);
		        // You can determine on HTTP return code received. 200 is success.
		    } catch (MalformedURLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } finally {
		        if (connection != null) {
		            connection.disconnect();
		        }
		    }
		}

}
