package philosophy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaCrawler {

    private static final String PHILOSOPHY_HEADING = "Philosophy";

    private static final String WIKIKPEDIA_BASE = "https://en.wikipedia.org";

    public static Path getPath(String startUrl) throws IOException{

        HashSet<String> locationsSet = new HashSet<>();
        ArrayList<String> locationsList = new ArrayList<>();

        Document doc = Jsoup.connect(startUrl).get();
        String firstHeading = doc.select("#firstHeading").html();
        locationsList.add(firstHeading);
        locationsSet.add(firstHeading);

        while(!firstHeading.equals(PHILOSOPHY_HEADING)) {
            System.out.println(doc.title());
            System.out.println(doc.location());

            String firstLink = findFirstLink(doc);

            doc = Jsoup.connect(WIKIKPEDIA_BASE + firstLink).get();
            firstHeading = doc.select("#firstHeading").html();
            if(!locationsSet.contains(firstHeading)){
                locationsList.add(firstHeading);
                locationsSet.add(firstHeading);
            } else {
                System.out.println("LOOOOP");
                break;
            }
        }


        for(String location : locationsList){
            System.out.println(location);
        }

        boolean foundPhilosophy = false;
        if(locationsList.get(locationsList.size()-1).equals(PHILOSOPHY_HEADING)){
            foundPhilosophy = true;
        }
        return new Path(locationsList, foundPhilosophy);

    }

    private static String findFirstLink(Document doc){
        String firstLink = "";


        ArrayList<String> removeSelectors = new ArrayList<>();
        removeSelectors.add(".navbox");
        removeSelectors.add(".vertical-navbox");
        removeSelectors.add(".toc");
        removeSelectors.add(".infobox");
        removeSelectors.add(".hatnote");
        removeSelectors.add("span");
        removeSelectors.add("sup");
        removeSelectors.add("small");


        for(String removeSelector : removeSelectors) {
            doc.select(removeSelector).remove();
        }

        Elements firstParagraph = doc.select(".mw-parser-output p");


        String firstParagraphText = firstParagraph.html();

        Pattern linkPattern = Pattern.compile("(<a href.*?=.*?\"(\\S+?)\".+?title.*?=.*?\"(.*?)\")");

        // remove all parenthesized and italics links
        firstParagraphText = firstParagraphText.replaceAll("\\([^\\)]*?(?:<a.*?a>)+.*?\\)","");
        firstParagraphText = firstParagraphText.replaceAll("\\[[^\\]]*?(?:<a.+?a>)+.*?\\]","");
        firstParagraphText = firstParagraphText.replaceAll("<i>[^\\/i]*?(?:<a.+?a>)+.*?<\\/i>","");

        Matcher linkMatcher = linkPattern.matcher(firstParagraphText);
        if(linkMatcher.find()){
            /*System.out.println(linkMatcher.groupCount());
            System.out.println(linkMatcher.group(2));
            System.out.println(linkMatcher.group(3));*/

            firstLink = linkMatcher.group(2);

        }

        return firstLink;
    }
}
