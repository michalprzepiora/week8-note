package pl.com.przepiora.week8note.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import pl.com.przepiora.week8note.model.Note;

import java.time.LocalDate;

public class NoteDialog extends Dialog {

  private TextField titleTextField;
  private TextArea messageTextArea;
  private Button addButton;
  private Button updateButton;
  private Button cancelButton;
  private RestApiManager restApiManager;
  private Note note;

  public NoteDialog(Operation operation) {
    restApiManager = new RestApiManager();
    this.setWidth("700px");
    VerticalLayout mainView = new VerticalLayout();
    mainView.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    titleTextField = new TextField("Tytuł");
    titleTextField.setWidth("700px");
    titleTextField.addInputListener(event -> updateButton.setEnabled(true));
    mainView.add(titleTextField);

    messageTextArea = new TextArea("Treść");
    messageTextArea.setWidth("700px");
    messageTextArea.setHeight("400px");
    messageTextArea.addInputListener(event -> updateButton.setEnabled(true));
    mainView.add(messageTextArea);

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    addButton = new Button("Dodaj");
    addButton.addClickListener(event -> saveNote());
    updateButton = new Button("Zapisz zmiany");
    updateButton.addClickListener(event->saveNote());
    cancelButton = new Button("Anuluj");
    cancelButton.addClickListener(buttonClickEvent -> this.close());

    if (operation.equals(Operation.ADD)) {
      horizontalLayout.add(addButton);
    } else {
      horizontalLayout.add(updateButton);

    }
    horizontalLayout.add(cancelButton);
    mainView.add(horizontalLayout);

    add(mainView);
  }

  public NoteDialog(Note note) {
    this(Operation.EDIT);
    this.note = note;
    titleTextField.setValue(note.getTitle());
    messageTextArea.setValue(note.getText());
    updateButton.setEnabled(false);
  }

  private void saveNote() {
    Note note = new Note(LocalDate.now(), titleTextField.getValue(), messageTextArea.getValue());
    if (this.note != null) {
      note.setId(this.note.getId());
    }
    restApiManager.addNote(note);
    this.close();
  }
}
