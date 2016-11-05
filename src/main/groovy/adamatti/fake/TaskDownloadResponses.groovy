package adamatti.fake

import adamatti.app.repositories.PostcoderRepository
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j

@Slf4j
class TaskDownloadResponses {
    static void main(String [] args) {
        new TaskDownloadResponses().downloadResponses()
    }

    private static final String DOWNLOAD_FOLDER = "src/main/resources/fake"

    private void downloadResponses(){
        PostcoderRepository repository = new PostcoderRepository()

        [
            [url: "address/ie/D02X285"],
            [url: "address/uk/NR147PZ"],
            [url: "address/uk/manor farm barns"],
            [url: "addressgeo/ie/Adelaide Road", query: [addtags:"w3w"]],
            [url: "position/ie/D02X285"],
            [url: "rgeo/ie/53.332067/-6.255492", query: [distance:50]]

        ].each {Map map ->
            File file = createFile("${DOWNLOAD_FOLDER}/${map.url}.json")

            file << toJson(repository.get(map.url as String, map.query ?: [:]))
            log.info("Downloaded ${map.url}")
        }
    }

    private File createFile(String path){
        String folder = path.split('/').dropRight(1).join("/")
        String fileName = path.split('/').takeRight(1).first()

        File file = new File(folder)
        file.mkdirs()

        file = new File(folder,fileName)
        if (file.exists()){
            file.delete()
        }
        file
    }

    private toJson(Object obj){
        new JsonBuilder(obj).toPrettyString()
    }
}
