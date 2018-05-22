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

        // list of headings of articles visited and set to quickly check for loops
        locationsList.add(firstHeading);
        locationsSet.add(firstHeading);

        while(!firstHeading.equals(PHILOSOPHY_HEADING)) {
            String firstLink = findFirstLink(doc);

            doc = Jsoup.connect(WIKIKPEDIA_BASE + firstLink).get();
            firstHeading = doc.select("#firstHeading").html();
            if(!locationsSet.contains(firstHeading)){
                locationsList.add(firstHeading);
                locationsSet.add(firstHeading);
            } else {
                System.out.println("Found loop");
                break;
            }
        }

        boolean foundPhilosophy = false;
        if(locationsList.get(locationsList.size()-1).equals(PHILOSOPHY_HEADING)){
            foundPhilosophy = true;
        }
        return new Path(locationsList, foundPhilosophy);

    }

    private static String findFirstLink(Document doc){
        String firstLink = "";

        // remove elements that do not contain the first valid link
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

        Elements paragraphs = doc.select(".mw-parser-output p");

        String paragraphsHtml = paragraphs.html();

        // remove all parenthesized and italics links
        paragraphsHtml = paragraphsHtml.replaceAll("\\([^\\)]*?(?:<a.*?a>)+.*?\\)","");
        paragraphsHtml = paragraphsHtml.replaceAll("\\[[^\\]]*?(?:<a.+?a>)+.*?\\]","");
        paragraphsHtml = paragraphsHtml.replaceAll("<i>[^\\/i]*?(?:<a.+?a>)+.*?<\\/i>","");

        // regex to find link with href and title
        Pattern linkPattern = Pattern.compile("(<a href.*?=.*?\"(\\S+?)\".+?title.*?=.*?\"(.*?)\")");
        Matcher linkMatcher = linkPattern.matcher(paragraphsHtml);

        if(linkMatcher.find()){
            firstLink = linkMatcher.group(2);
        }

        return firstLink;
    }
}
