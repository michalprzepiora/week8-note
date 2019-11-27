package pl.com.przepiora.week8note.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.przepiora.week8note.model.Note;
import pl.com.przepiora.week8note.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

  private final NoteRepository noteRepository;

  @Autowired
  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public List<Note> findAll(){
    return noteRepository.findAll();
  }

  public void saveNote(Note note){
    noteRepository.save(note);
  }

  public void deleteById(Long id){
    noteRepository.deleteById(id);
  }

  public void updateNote(Long id, Note note){
    note.setId(id);
    noteRepository.save(note);
  }
}
