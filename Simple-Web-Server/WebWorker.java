/**
 * Programmer: Kathleen Near
 * Course: CS371 (Software Development)
 * Date: 09/15/17
 * 
* Web worker: an object of this class executes in its own new thread
* to receive and respond to a single HTTP request. After the constructor
* the object executes on its "run" method, and leaves when it is done.
*
* One WebWorker object is only responsible for one client connection. 
* This code uses Java threads to parallelize the handling of clients:
* each WebWorker runs in its own thread. This means that you can essentially
* just think about what is happening on one client at a time, ignoring 
* the fact that the entirety of the webserver execution might be handling
* other clients, too. 
*
* This WebWorker class (i.e., an object of this class) is where all the
* client interaction is done. The "run()" method is the beginning -- think
* of it as the "main()" for a client interaction. It does three things in
* a row, invoking three methods in this class: it reads the incoming HTTP
* request; it writes out an HTTP header to begin its response, and then it
* writes out some HTML content for the response content. HTTP requests and
* responses are just lines of text (in a very particular format). 
*
**/

import java.net.Socket;
import java.lang.Runnable;
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.TimeZone;

public class WebWorker implements Runnable {
 
/**
* Global Variables
**/
String serverName = "Kat's WebServer";
String streamStatus = "HTTP/1.1 200 OK\n";
String filePath = "";
String contentType = "";
String HTMLcontent = "";
byte[] imageContent;
boolean noFileRequested = false;
boolean Windows = false;
private Socket socket;
 
/**
* Constructor: must have a valid open socket
**/
public WebWorker(Socket s) {
 socket = s;
}
 
/**
* Worker thread starting point. Each worker handles just one HTTP 
* request and then returns, which destroys the thread. This method
* assumes that whoever created the worker created it with a valid
* open socket object.
**/
public void run() {
 System.err.println("Handling connection...");
 try {
    InputStream  is = socket.getInputStream();
    OutputStream os = socket.getOutputStream();
    readHTTPRequest(is);
    postHTTPResponse(os);
    writeHTTPHeader(os,contentType);
    writeContent(os);
    os.flush();
    socket.close();
 } catch (Exception e) {
    System.err.println("Output error: "+e);
 }
 System.err.println("Done handling connection.");
 return;
}
 
 /****************************/
/* BEGIN PATH MANIPULATIONS */
/****************************/
 
/**
* Parses out the path to the file requested
* @param fullRequest is the user-initiated file request
* null is returned if there is no file request made
**/
private String parseFileRequest(String fullRequest) {
String splitRequest[] = new String[] {" ", " ", " "};
splitRequest = fullRequest.split(" ");
String fileRequest = splitRequest[1];
 
//If requested path is just "/" or "", don't return the path
if(fileRequest.length() <= 1) {
noFileRequested = true;
return null;
}
else return fileRequest;
}
 
/**
* Appends the request to the root directory path, creating a complete path to the desired file
* @param fullRequest is the user-initiated file request
* The complete path to the file is returned
**/
private String toCompletePath(String relativePath) {
//If using my Windows machine, the path is different than the school Linux machines
String filePath;
if (Windows)
filePath = "C:/Users/Michelle/eclipse-workspace/P2" + relativePath;
else
filePath = "." + relativePath;
return filePath;
}
 
/**
* Extracts the file extension from the URL file request
* @param requestPath is the user-initiated file request
* The file extension "text," "jpg," etc. is returned
**/
private String getFileExtension(String requestPath) {
String fileExtension = requestPath.substring(requestPath.lastIndexOf("."));
return fileExtension;
}
 
/**
* Determines the content type of the extension
* @param the user-requested file extension type
* "text/html," "img/__," etc. is returned
**/
private String ofType(String extension) {
String content;
if (extension.equals(".html"))
content = "text/html";
else if (extension.equals(".gif"))
content = "image/gif";
else if (extension.equals(".png"))
content = "image/png";
else if (extension.equals(".jpg") || extension.equals(".jpeg"))
content = "image/jpg";
else if (extension.equals(".ico"))
content = "image/x-icon";
else content = null;
 
return content;
}
 
 /**************************/
/* END PATH MANIPULATIONS */
/**************************/
 
/**
* Reads the HTTP request header and sets necessary info to retrieve it
* @param is the InputStream object to read from
**/
private void readHTTPRequest(InputStream is) {
BufferedReader requestReader = new BufferedReader(new InputStreamReader(is));
while (true) {
try {
//Reads the content of the URL request
while (!requestReader.ready()) Thread.sleep(1);
String urlRequest = requestReader.readLine();
System.err.println("Request line: ("+urlRequest+")");
if (urlRequest.length()==0) break;
 
//Determines the path to the requested file
String relativePath = parseFileRequest(urlRequest);
if(relativePath == null)
break;
filePath = toCompletePath(relativePath);
 
//Sets the type of content requested based on the file extension in the request
String fileType = getFileExtension(relativePath);
contentType = ofType(fileType);
break;
  
} catch (Exception e) {
System.err.println("Request error: " + e);
break;
}//end try-catch
}//end while loop
return;
}//END
 
/**
* Outputs different content to the webserver based upon the request
* @param os is the output stream
**/
private void postHTTPResponse(OutputStream os) throws Exception {
//If no file is requested, display default webserver content
if(noFileRequested)
HTMLcontent = "<html><h1>Welcome to " + serverName + "</h1><body>\n" +
 "<p>You may request a file in the URL path.</p>\n" +
 "</body></html>\n";
//Check if the file exists. If it does, display the contents, if it doesn't set the status accordingly
else {
File fileRequested = new File(filePath);
if(!fileRequested.exists()) {
streamStatus = "HTTP/1.1 404 NOT FOUND\n";
HTMLcontent = "<html><h1>404 Error.</h1><body>\n" +
 "<p>Page not found.</p>\n" +
 "</body></html>\n";
}
else
if(contentType.equals("text/html"))
displayTextFile(os);
else
displayImageFile();
}
}
 
/**
* Checks a line of a HTML file for certain tags and replaces them with the corresponding content
* @param line is one line of an HTML file
**/
private String replaceTags(String line) {
Date d = new Date();
DateFormat df = DateFormat.getDateTimeInstance();
line = line.replaceAll("<cs371date>", df.format(d));
line = line.replaceAll("<cs371server>", serverName);
 
return line;
}
 
/**
* Reads an HTML file and writes its content to the client network connection
* @param os is the OutputStream object to write to
**/
private void displayTextFile(OutputStream os) throws Exception {
while (true) {
try {
BufferedReader fileReader = new BufferedReader (new FileReader (filePath));
 
//Replace tags with indicated content
String line = "";
while((line = fileReader.readLine()) != null) {
line = replaceTags(line);
HTMLcontent += line;
}
} catch (Exception e) {
System.err.println("Read error: "+e);
break;
}//end try-catch
break;
}//end while loop
}
 
/**
* Reads an image file and displays it to the client network connection
**/
private void displayImageFile() throws Exception {
try {
File file = new File(filePath);
imageContent = new byte[(int) file.length()];
InputStream imageReader = new FileInputStream(filePath);
 
imageReader.read(imageContent);
imageReader.close();
 
} catch (IOException e){
System.err.println("Read error: "+e);
}
 
}
  
/**
* Write the HTTP header lines to the client network connection.
* @param os is the OutputStream object to write to
* @param contentType is the string MIME content type (e.g. "text/html")
**/
private void writeHTTPHeader(OutputStream os, String contentType) throws Exception {
 Date d = new Date();
 DateFormat df = DateFormat.getDateTimeInstance();
 df.setTimeZone(TimeZone.getTimeZone("GMT"));
 os.write(streamStatus.getBytes());
 os.write("Date: ".getBytes());
 os.write((df.format(d)).getBytes());
 os.write("\n".getBytes());
 os.write("Connection: close\n".getBytes());
 os.write("Content-Type: ".getBytes());
 os.write(contentType.getBytes());
 os.write("\n\n".getBytes());
 return;
}
 
/**
* Write the data content to the client network connection. This MUST
* be done after the HTTP header has been written out.
* @param os is the OutputStream object to write to
**/
private void writeContent(OutputStream os) throws Exception {
if(contentType.contains("image"))
os.write(imageContent);
else
os.write(HTMLcontent.getBytes());
}
} //END
