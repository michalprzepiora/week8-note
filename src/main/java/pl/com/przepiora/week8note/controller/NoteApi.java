package pl.com.przepiora.week8note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.przepiora.week8note.model.Note;
import pl.com.przepiora.week8note.service.NoteService;

import java.util.List;

@RestController()
public class NoteApi {

  private final NoteService noteService;

  @Autowired
  public NoteApi(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("/all")
  public List<Note> getAllNotes() {
    return noteService.findAll();
  }

  @PostMapping("/add")
  public void addNewNote(@RequestBody Note note) {
    noteService.saveNote(note);
  }

  @DeleteMapping("/delete/{id}")
  void deleteNote(@PathVariable Long id) {
    noteService.deleteById(id);
  }

  @PutMapping("/update/{id}")
  void updateNote(@PathVariable Long id, @RequestBody Note note) {
    noteService.updateNote(id, note);
  }
}
