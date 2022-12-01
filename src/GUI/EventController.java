package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.*;
import model.lists.EventList;
import model.lists.StudentList;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

//Kateryna//
public class EventController
{
    // todo delete all modelManager fields in astah!!
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private VBox actionsForSelectedEventBox;
    @FXML
    private CheckComboBox<Student> attenders;//todo change in class diagram "participants"
    @FXML
    private Button editEventButton;
    @FXML
    private Button deleteEventButton;
    @FXML
    private Button addEventButton;
    @FXML
    private Button exportEventButton;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> dateColumn;
    @FXML
    private TableColumn<Event, String> attendersColumn;

    public void initialize()
    {
        tableListener = new MyTableListener();
        eventsTable.getSelectionModel().selectedItemProperty().addListener(tableListener);

        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<Event, String>("description"));
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Event, String>("date"));
        attendersColumn.setCellValueFactory(
                new PropertyValueFactory<Event, String>("attenders"));


        updateTable();
    }

    public void handleAction(ActionEvent e)
    {
        if (e.getSource() == addEventButton)
        {
            String title = titleField.getText();
            String description = descriptionColumn.getText();

            MyDate date = new MyDate();
            LocalDate tempDate = datePicker.getValue();
            if(tempDate == null)
                date = null;
            else
            {
                date.setDay(tempDate.getDayOfMonth());
                date.setMonth(tempDate.getMonthValue());
                date.setYear(tempDate.getYear()); // date initialization
            }

            Event event = null;
            try
            {
                event = new Event(title, description, date);
            }
            catch (IllegalArgumentException exception)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
                alert.setHeaderText(null);
                alert.show();
                return;
            }

            try
            {
                for (int i = 0; i < checkComboBox.getCheckModel().getCheckedItems().size(); i++)
                {
                    event.addAttender(checkComboBox.getCheckModel().getCheckedItems().get(i));
                }
            }
            catch (ArrayIndexOutOfBoundsException exception)
            {
                System.err.println("Student list is empty in event tab");
            }

            Association association = AssociationModelManager.getAssociation();
            association.addEvent(event);
            AssociationModelManager.saveAssociation(association);
            updateTable();
        }
        else if (e.getSource() == exportEventButton)
        {
            //todo export
        }
        else if (e.getSource() == editEventButton)
        {
            String title = titleField.getText();
            String description = descriptionColumn.getText();



            Association association = AssociationModelManager.getAssociation();
          //  association.getEventList().setEvent(new Event(title, description, date), eventsTable.getSelectionModel().getSelectedIndex());
            AssociationModelManager.saveAssociation(association);

            updateTable();
        }
        else if (e.getSource() == deleteEventButton)
        {
            Association association = AssociationModelManager.getAssociation();
            int temp = eventsTable.getSelectionModel().getSelectedIndex();
            association.removeEvent(temp);
            AssociationModelManager.saveAssociation(association);
            updateTable();
        }
        else if (e.getSource() == eventsTable)
        {
            titleField.setText(titleColumn.getText());
            descriptionArea.setText(descriptionColumn.getText());
            String tempDate = dateColumn.getText();
            MyDate eDate = new MyDate();
            //eDate.setDay(dateColumn.getText());
            //datePicker.setValue();

        }
    }/**/

    public void updateTable()
    {
        EventList eventList = AssociationModelManager.getAssociation()
                .getEventList();
        eventsTable.getItems().clear();

        for (int i = 0; i < eventList.getSize(); i++)
        {
            Event event = eventList.getEvent(i);
            eventsTable.getItems().add(event);
        }

        System.out.println(checkComboBox.getItems().size());
        checkComboBox.getItems().clear();
        checkComboBox.getItems().addAll(AssociationModelManager.getAssociation().getStudentList().getArrayOfStudents());
        System.out.println(checkComboBox.getItems().size());
    }

}

