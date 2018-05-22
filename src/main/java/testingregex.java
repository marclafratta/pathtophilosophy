
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testingregex {

    public static void main(String [] args){

        Pattern linkPattern = Pattern.compile("(<a href.*?=.*?\"(\\S+?)\".+?title.*?=.*?\"(.*?)\")");

        String paragraphText = "<p><b>Ontology</b> (<a href=\"/wiki/Protologism\" title=\"Protologism\">introduced</a> in 1606) is the <a href=\"/wiki/Philosophical\" class=\"mw-redirect\" title=\"Philosophical\">philosophical</a> <a href=\"https://en.wiktionary.org/wiki/study\" class=\"extiw\" title=\"wikt:study\">study</a> of the nature of <a href=\"/wiki/Being\" title=\"Being\">being</a>, <a href=\"/wiki/Becoming_(philosophy)\" title=\"Becoming (philosophy)\">becoming</a>, <a href=\"/wiki/Existence\" title=\"Existence\">existence</a>, or <a href=\"/wiki/Reality\" title=\"Reality\">reality</a>, as well as the basic <a href=\"/wiki/Category_of_being\" title=\"Category of being\">categories of being</a> and their relations.<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">[1]</a></sup> Traditionally listed as a part of the major branch of philosophy known as <a href=\"/wiki/Metaphysics\" title=\"Metaphysics\">metaphysics</a>, ontology often deals with questions concerning what <a href=\"/wiki/Entities\" class=\"mw-redirect\" title=\"Entities\">entities</a> exist or may be said to exist and how such entities may be grouped, related within a <a href=\"/wiki/Hierarchy\" title=\"Hierarchy\">hierarchy</a>, and subdivided according to similarities and differences. A very simple <a href=\"https://en.wiktionary.org/wiki/ontology\" class=\"extiw\" title=\"wiktionary:ontology\">definition of ontology</a> is that it is the examination of what is meant by 'being'.</p>";

        paragraphText = paragraphText.replaceAll("\\(.*?\\)","");
        paragraphText = paragraphText.replaceAll("\\[.*?\\]","");


        paragraphText = paragraphText.replaceAll("<i>.*?<\\/i>","");

        Matcher m = linkPattern.matcher(paragraphText);
        if(m.find()){
            System.out.println(m.groupCount());
            System.out.println(m.group(2));
            System.out.println(m.group(3));

        }
        //System.out.println(paragraphText);
    }
}
