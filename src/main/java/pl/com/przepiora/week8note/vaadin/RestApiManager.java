package pl.com.przepiora.week8note.vaadin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.com.przepiora.week8note.model.Note;

import java.util.Arrays;
import java.util.List;

public class RestApiManager {

  private RestTemplate restTemplate;
  private static final String HOME_URL = "https://week8-note.herokuapp.com/";


  public RestApiManager() {
    restTemplate = new RestTemplate();
  }

  public List<Note> getAllNotes() {
    final String URL = HOME_URL + "/all";
    ResponseEntity<Note[]> responseEntity = restTemplate.getForEntity(URL, Note[].class);
    return Arrays.asList(responseEntity.getBody());
  }

  public void addNote(Note note) {
    final String URL = HOME_URL + "/add";
    restTemplate.postForObject(URL, note, Note.class);
  }

  public void deleteNote(Long id){
    final String URL = HOME_URL + "/delete/" + id;
    restTemplate.delete(URL);
  }
}
