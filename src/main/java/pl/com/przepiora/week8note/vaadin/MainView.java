package pl.com.przepiora.week8note.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import pl.com.przepiora.week8note.model.Note;

import java.util.List;

@Route
public class MainView extends VerticalLayout {

  private RestApiManager restApiManager;
  private Grid<Note> noteGrid;
  private List<Note> noteList;
  private Button addNote;
  private Button updateNote;
  private Button deleteNote;
  private Dialog dialog;
  private Note selectedNote;

  public MainView() {
    this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    restApiManager = new RestApiManager();
    noteList = restApiManager.getAllNotes();
    add(new Label("Notatnik Online"));

    noteGrid = new Grid<>();
    noteGrid.addColumn((ValueProvider<Note, String>) note -> note.getDate().toString())
        .setHeader("Data").setResizable(true).setAutoWidth(true)
        .setFooter(getFooterRefresh());
    noteGrid.addColumn((ValueProvider<Note, String>) Note::getTitle).setHeader("Tytuł")
        .setResizable(true).setAutoWidth(true);
    noteGrid.addColumn((ValueProvider<Note, String>) note -> cutText(note.getText()))
        .setHeader("Treść").setResizable(true).setAutoWidth(true);

    noteGrid.setItems(noteList);
    noteGrid.setWidth("800px");
    noteGrid.setColumnReorderingAllowed(true);
    noteGrid.addItemClickListener(noteItemClickEvent -> {
      boolean isGridItemSelected = !noteGrid.getSelectedItems().isEmpty();
      setEnabledButtons(isGridItemSelected);
      selectedNote = noteItemClickEvent.getItem();
    });

    HorizontalLayout gridLayout = new HorizontalLayout(noteGrid);
    add(gridLayout);

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    addNote = new Button("Nowa notatka");
    addNote.addClickListener(event -> {
      dialog = new NoteDialog(Operation.ADD);
      dialog.open();
      dialog.addDetachListener(eventClose -> {
        refreshGrid();
        setEnabledButtons(false);
      });
    });

    updateNote = new Button("Wyświetl");
    updateNote.setEnabled(false);
    updateNote.addClickListener(event -> {
      dialog = new NoteDialog(selectedNote);
      dialog.open();
      dialog.addDetachListener(eventClose -> {
        refreshGrid();
        setEnabledButtons(false);
      });
    });
    deleteNote = new Button("Usuń");
    deleteNote.setEnabled(false);
    deleteNote.addClickListener(event -> {
      restApiManager.deleteNote(selectedNote.getId());
      setEnabledButtons(false);
      refreshGrid();
    });
    horizontalLayout.add(addNote, updateNote, deleteNote);
    add(horizontalLayout);
  }

  private String getFooterRefresh() {
    return "Ilość notatek: " + restApiManager.getAllNotes().size();
  }

  private void refreshGrid() {
    noteList = restApiManager.getAllNotes();
    noteGrid.setItems(noteList);
    noteGrid.getColumns().get(0).setFooter(getFooterRefresh());
  }

  private String cutText(String text) {
    int length = 50;
    if (text.length() < length) {
      return text;
    }
    return text.substring(0, length) + " ...";
  }

  private void setEnabledButtons(boolean isEnabled) {
    updateNote.setEnabled(isEnabled);
    deleteNote.setEnabled(isEnabled);
  }
}
