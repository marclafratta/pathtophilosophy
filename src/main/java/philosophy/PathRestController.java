package philosophy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
public class PathRestController {

    @Autowired
    PathRepository repository;

    @RequestMapping("/findPath")
    public Path findPath(@RequestParam(value = "startUrl") String startUrl) {

        Path foundPath;
        try {
            foundPath = WikipediaCrawler.getPath(startUrl);
        } catch (Exception e){
            // send empty path if exception
            e.printStackTrace();
            foundPath = new Path(new ArrayList<String>(), false);
        }

        repository.save(foundPath);

        return foundPath;
    }
}
