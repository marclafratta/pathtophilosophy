package philosophy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple example, used on the jsoup website.
 */
public class Wikipedia {
    public static final String PHILOSOPHY_HEADING = "Philosophy";

    public static final String WIKIKPEDIA_BASE = "https://en.wikipedia.org";


    public static void main(String[] args) throws IOException {

        HashSet<String> locationsSet = new HashSet<>();
        ArrayList<String> locationsList = new ArrayList<>();

        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Cicero").get();
        String firstHeading = doc.select("#firstHeading").html();
        locationsList.add(firstHeading);
        locationsSet.add(firstHeading);

        while(!firstHeading.equals(PHILOSOPHY_HEADING)) {
            log(doc.title());
            System.out.println(doc.location());

            String firstLink = findFirstLink(doc);

            doc = Jsoup.connect(WIKIKPEDIA_BASE + firstLink).get();
            firstHeading = doc.select("#firstHeading").html();
            if(!locationsSet.contains(firstHeading)){
                locationsList.add(firstHeading);
                locationsSet.add(firstHeading);
            } else {
                break;
            }
        }

        locationsList.add(firstHeading);

        for(String location : locationsList){
            System.out.println(location);
        }

    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
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

        // remove all parenthesized and italics text
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