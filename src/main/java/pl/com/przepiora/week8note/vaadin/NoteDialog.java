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

  public NoteDialog(Operation operation) {
    restApiManager = new RestApiManager();
    this.setWidth("700px");
    VerticalLayout mainView = new VerticalLayout();
    mainView.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    titleTextField = new TextField("Tytuł");
    titleTextField.setWidth("700px");
    mainView.add(titleTextField);

    messageTextArea = new TextArea("Treść");
    messageTextArea.setWidth("700px");
    messageTextArea.setHeight("400px");
    mainView.add(messageTextArea);

    HorizontalLayout horizontalLayout = new HorizontalLayout();
    addButton = new Button("Dodaj");
    addButton.addClickListener(event -> saveNote());
    updateButton = new Button("Zapisz zmiany");
    cancelButton = new Button("Anuluj");
    if (operation.equals(Operation.ADD)) {
      horizontalLayout.add(addButton);
    } else {
      horizontalLayout.add(updateButton);
    }
    horizontalLayout.add(cancelButton);
    mainView.add(horizontalLayout);

    add(mainView);
  }

  private void saveNote() {
    Note note = new Note(LocalDate.now(),titleTextField.getValue(),messageTextArea.getValue());
    restApiManager.addNote(note);
    this.close();
  }
}
