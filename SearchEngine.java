import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    
    List<String> strList = new ArrayList<String>();
    String out = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return strList.toString();
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strList.add(parameters[1]);
                return String.format("String added to the list: %s! The list size is now %d", parameters[1], strList.size());
            }
            return "404 Not Found!";
        } else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    out = "";
                    for(String e: strList) {
                        if(e.contains(parameters[1])) {
                            out += e + "\n";
                        }
                            
                    }
                    if(out.equals(""))
                        return "No strings found. :(";
                    return out;
                }
            return "404 Not Found!";
        } else {
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

