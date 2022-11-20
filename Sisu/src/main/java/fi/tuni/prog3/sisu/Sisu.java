package fi.tuni.prog3.sisu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * User interface class JavaFX app
 */
public class Sisu extends Application {

    private Student student = new Student("Nimi", "opiskelijanumero", 2000, 2005, new DegreeProgramme("tutkinto-ohjelma", "koodi", 180));
    private StudentInfo students;
    private SisuAPI sisuAPI = new SisuAPI();

    /**
     * User interface for Sisu
     *
     * @param stage rootStage
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        // Creating StudenInfo object for getting saved Student objects and saving
        // Student objects to a json file
        students = new StudentInfo("./students.json");

        // setting stage information
        stage.setMinWidth(1050);
        stage.setMinHeight(650);
        stage.setMaxHeight(650);
        stage.setMaxWidth(1050);
        stage.setTitle("Sisu");

        // Setting up the 'login page' view
        AnchorPane pane = new AnchorPane();
        pane.setMaxWidth(1050);
        pane.setMaxHeight(650);
        Scene scene = new Scene(pane, 1050, 650);

        // Setting up the actual SISU view and its tabs
        TabPane tpane = new TabPane();
        tpane.setMaxHeight(650);
        tpane.setMaxWidth(1050);
        Scene scene2 = new Scene(tpane, 1050, 650);

        // Pane for User information tab
        AnchorPane userPane = new AnchorPane();

        // Pane for Course listing tab
        AnchorPane courseListingPane = new AnchorPane();

        // TreeView for showing the course listing
        TreeItem<String> rootItem = new TreeItem<>(student.getDegreeProgramme().getName());
        TreeView<String> tree = new TreeView<>(rootItem);

        // Pane for Progress view tab
        AnchorPane progressViewPane = new AnchorPane();

        // Setting up the tabs on the actual SISU view
        Tab user = new Tab("Matti Meikäläinen");
        Tab courseListing = new Tab("Kurssinäkymä");
        Tab progressView = new Tab("Tutkinnon edityminen");

        tpane.getTabs().add(courseListing);
        tpane.getTabs().add(progressView);
        tpane.getTabs().add(user);
        
        SingleSelectionModel<Tab> selectionModel = tpane.getSelectionModel();

        // *** Content for login page starts here ***
        var label = new Label("Tervetuloa Sisuun!");
        label.setFont(new Font("Arial", 26));
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(label, 10.0);
        AnchorPane.setRightAnchor(label, 10.0);
        AnchorPane.setLeftAnchor(label, 10.0);

        pane.getChildren().add(label);

        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        sep.setPrefHeight(206.0);
        sep.setPrefWidth(0.0);
        AnchorPane.setTopAnchor(sep, 90.0);
        AnchorPane.setBottomAnchor(sep, 90.0);
        AnchorPane.setLeftAnchor(sep, 30.0);
        AnchorPane.setRightAnchor(sep, 30.0);

        pane.getChildren().add(sep);

        var newUser = new Label("Luo uusi opiskelijaprofiili");
        newUser.setFont(new Font("Arial", 20));
        newUser.setAlignment(Pos.TOP_LEFT);
        newUser.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(newUser, 90.0);
        AnchorPane.setRightAnchor(newUser, 10.0);
        AnchorPane.setLeftAnchor(newUser, 50.0);

        pane.getChildren().add(newUser);

        var name = new Label("Nimi:");
        name.setFont(new Font("Arial", 14));
        name.setAlignment(Pos.TOP_LEFT);
        name.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(name, 150.0);
        AnchorPane.setRightAnchor(name, 10.0);
        AnchorPane.setLeftAnchor(name, 50.0);

        pane.getChildren().add(name);

        var studentNumber = new Label("Opiskelijanumero:");
        studentNumber.setFont(new Font("Arial", 14));
        studentNumber.setAlignment(Pos.TOP_LEFT);
        studentNumber.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(studentNumber, 200.0);
        AnchorPane.setRightAnchor(studentNumber, 10.0);
        AnchorPane.setLeftAnchor(studentNumber, 50.0);

        pane.getChildren().add(studentNumber);

        var startYear = new Label("Opintojen aloitusvuosi:");
        startYear.setFont(new Font("Arial", 14));
        startYear.setAlignment(Pos.TOP_LEFT);
        startYear.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(startYear, 250.0);
        AnchorPane.setRightAnchor(startYear, 10.0);
        AnchorPane.setLeftAnchor(startYear, 50.0);

        pane.getChildren().add(startYear);

        var endYear = new Label("Valmistumisen tavoitevuosi:");
        endYear.setFont(new Font("Arial", 14));
        endYear.setAlignment(Pos.TOP_LEFT);
        endYear.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(endYear, 300.0);
        AnchorPane.setRightAnchor(endYear, 10.0);
        AnchorPane.setLeftAnchor(endYear, 50.0);

        pane.getChildren().add(endYear);

        var nameInput = new TextField();
        nameInput.setId("name");
        nameInput.setAlignment(Pos.TOP_LEFT);
        nameInput.setPromptText("Matti Meikäläinen");
        AnchorPane.setTopAnchor(nameInput, 145.0);
        AnchorPane.setLeftAnchor(nameInput, 230.0);

        pane.getChildren().add(nameInput);

        var studentNumberInput = new TextField();
        studentNumberInput.setId("studentNumber");
        studentNumberInput.setAlignment(Pos.TOP_LEFT);
        studentNumberInput.setPromptText("12345678");
        AnchorPane.setTopAnchor(studentNumberInput, 195.0);
        AnchorPane.setLeftAnchor(studentNumberInput, 230.0);

        pane.getChildren().add(studentNumberInput);

        var endYearSelector = new Spinner<Integer>();
        endYearSelector.setId("endYear");
        SpinnerValueFactory<Integer> values2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1991, 2001);
        endYearSelector.setValueFactory(values2);
        endYearSelector.setEditable(true);
        AnchorPane.setTopAnchor(endYearSelector, 295.0);
        AnchorPane.setLeftAnchor(endYearSelector, 230.0);

        var startYearSelector = new Spinner<Integer>();
        startYearSelector.setId("startYear");
        SpinnerValueFactory<Integer> values = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2022);
        startYearSelector.setValueFactory(values);
        startYearSelector.setEditable(true);
        // Giving the start year modifies the endYearSelector spinner to have values ten years furher than the selected start year
        startYearSelector.valueProperty().addListener((ObservableValue<? extends Integer> obs, Integer oldValue, Integer newValue) -> {
            SpinnerValueFactory<Integer> values3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue + 1, newValue + 11);
            endYearSelector.setValueFactory(values3);
        });
        AnchorPane.setTopAnchor(startYearSelector, 245.0);
        AnchorPane.setLeftAnchor(startYearSelector, 230.0);

        pane.getChildren().add(startYearSelector);

        pane.getChildren().add(endYearSelector);

        var selectDegreeProgram = new Label("Valitse opinto-ohjelma:");
        selectDegreeProgram.setFont(new Font("Arial", 14));
        selectDegreeProgram.setAlignment(Pos.TOP_LEFT);
        selectDegreeProgram.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(selectDegreeProgram, 350.0);
        AnchorPane.setRightAnchor(selectDegreeProgram, 10.0);
        AnchorPane.setLeftAnchor(selectDegreeProgram, 50.0);

        pane.getChildren().add(selectDegreeProgram);

        var degreeProgramSelector = new ChoiceBox();
        degreeProgramSelector.setId("selectDegreeProgram");
        sisuAPI.getAllDegreeProgrammes();
        for (DegreeProgramme degreeprogramme : sisuAPI.DegreeProgrammeList) {
            degreeProgramSelector.getItems().add(degreeprogramme.getName());
        }
        degreeProgramSelector.setPrefWidth(200.0);
        AnchorPane.setTopAnchor(degreeProgramSelector, 345.0);
        AnchorPane.setLeftAnchor(degreeProgramSelector, 230.0);

        pane.getChildren().add(degreeProgramSelector);

        Alert missingInformation = new Alert(AlertType.ERROR);
        missingInformation.setTitle("Virhe profiilin luonnissa");
        missingInformation.setContentText("Osa tiedoista puuttuu. Täytäthän kaikki kysytyt tiedot profiilin luomiseksi.");
        missingInformation.setHeaderText(null);
        
        Alert sameStudentNumber = new Alert(AlertType.ERROR);
        sameStudentNumber.setTitle("Virhe profiilin luonnissa");
        sameStudentNumber.setContentText("Antamallasi opiskelijanumerolla löytyi olemassa oleva profiili."
                + " Anna uniikki opiskelijanumro tai valitse opiskelija olemassa olevista profiileista.");
        sameStudentNumber.setHeaderText(null);

        var continueButton = new Button("Jatka");
        continueButton.setId("continueButton");
        AnchorPane.setTopAnchor(continueButton, 400.0);
        AnchorPane.setLeftAnchor(continueButton, 50.0);

        // Getting all the modules and courses for the selected degreeprogramme
        // from Sisu API and then creating the TreeItems to show them in the
        // course listing tab
        continueButton.setOnAction((ActionEvent e) -> {
            var studentName = nameInput.getText();
            var studentsStudentNumber = studentNumberInput.getText();
            var studentDegreeProgram = degreeProgramSelector.getValue();
            DegreeProgramme studentDP = new DegreeProgramme("name", "code", 180);

            // checking that the selected degreeprogramme is found from Sisu API
            // and getting its iformation (name, code, credits) from there
            for (DegreeProgramme d : sisuAPI.DegreeProgrammeList) {
                if (d.getName().equals(studentDegreeProgram)) {
                    studentDP = d;
                    break;
                }
            }
            var studentStartYear = startYearSelector.getValue();
            var studentEndYear = endYearSelector.getValue();

            // If all the inforamtion asked is filled
            // getting the modules and courses for the selected degreeprogramme
            if (!"".equals(studentName) && !"".equals(studentsStudentNumber) && studentDegreeProgram != null) {
                Boolean isAlreadyAStudentNumber = false;
                // Check that the studentnumber is unique / there is not an
                // existing student with the same studentnumber
                try {
                    for (Student s : students.getStudents()) {
                        if (s.getStudentNumber().equals(studentsStudentNumber)) {
                            isAlreadyAStudentNumber = true;
                            break;
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                if (!isAlreadyAStudentNumber) {
                    student = new Student(studentName, studentsStudentNumber, studentStartYear, studentEndYear, studentDP);
                    user.setText(studentName);
                    try {
                        sisuAPI.getDegreeProgramme(student.getDegreeProgramme());
                        TreeItem<String> newRootItem = new TreeItem<>(student.getDegreeProgramme().getName());
                        for (Module m : sisuAPI.degreeProgramme.getModules()) {
                            newRootItem.getChildren().add(getCourseView(m));
                        }
                        tree.setRoot(newRootItem);
                        student.getDegreeProgramme().setCredits(sisuAPI.degreeProgramme.getCredits());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    stage.setScene(scene2);
                    nameInput.setText("");
                    studentNumberInput.setText("");
                    degreeProgramSelector.setValue("");
                    startYearSelector.getValueFactory().setValue(1990);
                } else {
                    sameStudentNumber.showAndWait();
                }
            } else {
                missingInformation.showAndWait();
            }
        });

        pane.getChildren().add(continueButton);

        var selectUser = new Label("Valitse olemassa olevista opiskelijaprofiileista");
        selectUser.setFont(new Font("Arial", 20));
        selectUser.setAlignment(Pos.TOP_RIGHT);
        selectUser.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(selectUser, 90.0);
        AnchorPane.setRightAnchor(selectUser, 30.0);
        AnchorPane.setLeftAnchor(selectUser, 500.0);

        pane.getChildren().add(selectUser);

        var profileSelector = new ChoiceBox();
        profileSelector.setPrefWidth(400.0);
        AnchorPane.setTopAnchor(profileSelector, 145.0);
        AnchorPane.setRightAnchor(profileSelector, 30.0);

        for (Student s : students.getStudents()) {
            profileSelector.getItems().add(s.getName());
        }

        pane.getChildren().add(profileSelector);

        var selectButton = new Button("Valitse");
        AnchorPane.setTopAnchor(selectButton, 190.0);
        AnchorPane.setRightAnchor(selectButton, 30.0);
        selectButton.setOnAction((ActionEvent e) -> {
            try {
                for (Student s : students.getStudents()) {
                    if (s.getName().equals(profileSelector.getValue())) {
                        student = s;
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            user.setText(student.getName());
            // getting the modules and courses for the selected degreeprogramme
            try {
                for (DegreeProgramme d : sisuAPI.DegreeProgrammeList) {
                    if (d.getName().equals(student.getDegreeProgramme().getName())) {
                        student.setProgramme(d);
                        break;
                    }
                }
                sisuAPI.getDegreeProgramme(student.getDegreeProgramme());
                TreeItem<String> newRootItem = new TreeItem<>(student.getDegreeProgramme().getName());
                for (Module m : sisuAPI.degreeProgramme.getModules()) {
                    newRootItem.getChildren().add(getCourseView(m));
                }
                tree.setRoot(newRootItem);
                student.getDegreeProgramme().setCredits(sisuAPI.degreeProgramme.getCredits());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            stage.setScene(scene2);
            profileSelector.setValue("");
            nameInput.setText("");
            studentNumberInput.setText("");
            degreeProgramSelector.setValue("");
            startYearSelector.getValueFactory().setValue(1990);
        });

        pane.getChildren().add(selectButton);

        // ** Content for User Information Tab starts here **
        user.setContent(userPane);

        Separator sep2 = new Separator();
        sep2.setOrientation(Orientation.VERTICAL);
        sep2.setPrefHeight(400.0);
        sep2.setPrefWidth(0.0);
        AnchorPane.setTopAnchor(sep2, 20.0);
        AnchorPane.setBottomAnchor(sep2, 20.0);
        AnchorPane.setLeftAnchor(sep2, 300.0);

        userPane.getChildren().add(sep2);

        Button saveAndLogOut = new Button("Tallenna ja kirjaudu ulos");
        AnchorPane.setTopAnchor(saveAndLogOut, 50.0);
        AnchorPane.setLeftAnchor(saveAndLogOut, 75.0);
        saveAndLogOut.setOnAction((ActionEvent e) -> {
            try {
                Student studentToSave = student;
                students.addStudent(studentToSave);
                student = null;
                profileSelector.getItems().clear();
                for (Student s : students.getStudents()) {
                    profileSelector.getItems().add(s.getName());
                }
                profileSelector.setValue("");
                stage.setScene(scene);
                selectionModel.select(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        userPane.getChildren().add(saveAndLogOut);

        Alert logOutWithoutSaving = new Alert(AlertType.CONFIRMATION);
        logOutWithoutSaving.setTitle("Kirjaudu ulos tallentamatta");
        logOutWithoutSaving.setContentText("Olet kirjautumassa ulos tallentamatta tehtyjä muokkauksia."
                + " Kaikki talentamaton tieto katoaa. Oletko varma että haluat jatkaa.");
        logOutWithoutSaving.setHeaderText(null);

        var logOut = new Button("Kirjaudu ulos tallentamatta");
        AnchorPane.setTopAnchor(logOut, 100.0);
        AnchorPane.setLeftAnchor(logOut, 75.0);
        logOut.setOnAction((ActionEvent e) -> {
            Optional<ButtonType> result = logOutWithoutSaving.showAndWait();
            if (result.get() == ButtonType.OK) {
                student = null;
                profileSelector.setValue("");
                stage.setScene(scene);
                selectionModel.select(0);
            }
        });

        userPane.getChildren().add(logOut);

        var name2 = new Label("Nimi:");
        name2.setFont(new Font("Arial", 14));
        name2.setAlignment(Pos.TOP_LEFT);
        name2.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(name2, 50.0);
        AnchorPane.setRightAnchor(name2, 10.0);
        AnchorPane.setLeftAnchor(name2, 330.0);

        userPane.getChildren().add(name2);

        var studentNumber2 = new Label("Opiskelijanumero:");
        studentNumber2.setFont(new Font("Arial", 14));
        studentNumber2.setAlignment(Pos.TOP_LEFT);
        studentNumber2.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(studentNumber2, 100.0);
        AnchorPane.setRightAnchor(studentNumber2, 10.0);
        AnchorPane.setLeftAnchor(studentNumber2, 330.0);

        userPane.getChildren().add(studentNumber2);

        var startYear2 = new Label("Opintojen aloitusvuosi:");
        startYear2.setFont(new Font("Arial", 14));
        startYear2.setAlignment(Pos.TOP_LEFT);
        startYear2.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(startYear2, 150.0);
        AnchorPane.setRightAnchor(startYear2, 10.0);
        AnchorPane.setLeftAnchor(startYear2, 330.0);

        userPane.getChildren().add(startYear2);

        var endYear2 = new Label("Valmistumisen tavoitevuosi:");
        endYear2.setFont(new Font("Arial", 14));
        endYear2.setAlignment(Pos.TOP_LEFT);
        endYear2.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(endYear2, 200.0);
        AnchorPane.setRightAnchor(endYear2, 10.0);
        AnchorPane.setLeftAnchor(endYear2, 330.0);

        userPane.getChildren().add(endYear2);

        var selectDegreeProgram2 = new Label("Valitse opinto-ohjelma:");
        selectDegreeProgram2.setFont(new Font("Arial", 14));
        selectDegreeProgram2.setAlignment(Pos.TOP_LEFT);
        selectDegreeProgram2.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(selectDegreeProgram2, 250.0);
        AnchorPane.setRightAnchor(selectDegreeProgram2, 10.0);
        AnchorPane.setLeftAnchor(selectDegreeProgram2, 330.0);

        userPane.getChildren().add(selectDegreeProgram2);

        var nameInput2 = new TextField();
        nameInput2.setId("name2");
        nameInput2.setText(student.getName());
        nameInput2.setAlignment(Pos.TOP_LEFT);
        nameInput2.setText(student.getName());
        AnchorPane.setTopAnchor(nameInput2, 45.0);
        AnchorPane.setLeftAnchor(nameInput2, 510.0);

        userPane.getChildren().add(nameInput2);

        var studentNumberInput2 = new TextField();
        studentNumberInput2.setId("studentNumber2");
        studentNumberInput2.setText(student.getStudentNumber());
        studentNumberInput2.setAlignment(Pos.TOP_LEFT);
        studentNumberInput2.setText(student.getStudentNumber());
        AnchorPane.setTopAnchor(studentNumberInput2, 95.0);
        AnchorPane.setLeftAnchor(studentNumberInput2, 510.0);

        userPane.getChildren().add(studentNumberInput2);

        var endYearSelector2 = new Spinner<Integer>();
        endYearSelector2.setId("endYear2");
        SpinnerValueFactory<Integer> values4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(student.getGraduationYear(), student.getGraduationYear() + 10);
        endYearSelector2.setValueFactory(values4);
        endYearSelector2.setEditable(true);
        AnchorPane.setTopAnchor(endYearSelector2, 195.0);
        AnchorPane.setLeftAnchor(endYearSelector2, 510.0);

        var startYearSelector2 = new Spinner<Integer>();
        startYearSelector2.setId("startYear2");
        SpinnerValueFactory<Integer> values5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, student.getStartYear() + 10);
        startYearSelector2.setValueFactory(values5);
        startYearSelector2.getValueFactory().setValue(student.getStartYear());
        startYearSelector2.setEditable(true);
        AnchorPane.setTopAnchor(startYearSelector2, 145.0);
        AnchorPane.setLeftAnchor(startYearSelector2, 510.0);

        startYearSelector2.valueProperty().addListener((ObservableValue<? extends Integer> obs, Integer oldValue, Integer newValue) -> {
            SpinnerValueFactory<Integer> values6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue + 1, newValue + 11);
            endYearSelector2.setValueFactory(values6);
        });

        userPane.getChildren().add(startYearSelector2);

        userPane.getChildren().add(endYearSelector2);

        var degreeProgramSelector2 = new ChoiceBox();
        degreeProgramSelector2.setId("degreeProgram2");
        for (DegreeProgramme degreeprogramme : sisuAPI.DegreeProgrammeList) {
            degreeProgramSelector2.getItems().add(degreeprogramme.getName());
        }
        degreeProgramSelector2.getSelectionModel().select(student.getDegreeProgramme().getName());
        degreeProgramSelector2.setPrefWidth(200.0);
        AnchorPane.setTopAnchor(degreeProgramSelector2, 245.0);
        AnchorPane.setLeftAnchor(degreeProgramSelector2, 510.0);

        userPane.getChildren().add(degreeProgramSelector2);

        Alert changeDegreeProgramme = new Alert(AlertType.CONFIRMATION);
        changeDegreeProgramme.setTitle("Tutkinto-ohjelman vaihtaminen");
        changeDegreeProgramme.setContentText("Olet vaihtamassa tutkinto-ohjelmaa."
                + " Kaikki suorituksesi postuvat toiminnon yhteydessä. Oletko"
                + " varma että haluat jatkaa?");
        changeDegreeProgramme.setHeaderText(null);
        
        Alert studentNumberExists = new Alert(AlertType.ERROR);
        studentNumberExists.setTitle("Virhe tietojen tallennuksessa");
        studentNumberExists.setContentText("Antamasi opiskelijanumero on jo toisen opiskelija käytössä."
                + " Anna uniikki opiskelijanumro.");
        studentNumberExists.setHeaderText(null);
        
        Alert saveComplete = new Alert(AlertType.INFORMATION);
        saveComplete.setTitle("Tiedot tallennettu");
        saveComplete.setContentText("Tiedot tallennettiin onnistuneesti.");
        saveComplete.setHeaderText(null);

        Button save = new Button("Tallenna henkilötiedot");
        AnchorPane.setTopAnchor(save, 300.0);
        AnchorPane.setLeftAnchor(save, 330.0);
        save.setPrefWidth(150);
        save.setOnAction((ActionEvent e) -> {
            Boolean isAlreadyAStudentNumber = false;
            // Check that the studentnumber is unique / there is not an
            // existing student with the same studentnumber
            try {
                for (Student s : students.getStudents()) {
                    if (s.getStudentNumber().equals(studentNumberInput2.getText()) 
                            && !s.getStudentNumber().equals(student.getStudentNumber())) {
                        isAlreadyAStudentNumber = true;
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            if (!isAlreadyAStudentNumber) {
                student.setName(nameInput2.getText());
                student.setStudentNumber(studentNumberInput2.getText());
                student.setStartYear(startYearSelector2.getValue());
                student.setGraduationYear(endYearSelector2.getValue());
                // Checking if degreeprogramme is changed
                if (!student.getDegreeProgramme().getName().equals(degreeProgramSelector2.getValue().toString())) {
                    // Giving a warning message for user
                    Optional<ButtonType> result = changeDegreeProgramme.showAndWait();
                    // Changin degreeprogramme if user is sure they want it
                    // Deleting all the attainments from previous degreeprogramme
                    if (result.get() == ButtonType.OK) {
                        DegreeProgramme newProgramme = new DegreeProgramme("name", "code", 180);
                        for (DegreeProgramme d : sisuAPI.DegreeProgrammeList) {
                            if (d.getName().equals(degreeProgramSelector2.getValue().toString())) {
                                newProgramme = d;
                                break;
                            }
                        }
                        student.setProgramme(newProgramme);
                        student.removeAllAttainments();
                        // Setting up the TreeView of degreeprogramme again
                        try {
                            sisuAPI.getDegreeProgramme(student.getDegreeProgramme());
                            TreeItem<String> newRootItem = new TreeItem<>(student.getDegreeProgramme().getName());
                            for (Module m : sisuAPI.degreeProgramme.getModules()) {
                                newRootItem.getChildren().add(getCourseView(m));
                            }
                            tree.setRoot(newRootItem);
                            student.getDegreeProgramme().setCredits(sisuAPI.degreeProgramme.getCredits());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // If user doesn't want to change their degreeprogramme, set the
                        // degreeprogramme selector to show students programme again
                    } else {
                        degreeProgramSelector2.setValue(student.getDegreeProgramme().getName());
                    }
                }
                user.setText(student.getName());
                saveComplete.showAndWait();
            } else {
                studentNumberExists.showAndWait();
            }
        });

        userPane.getChildren().add(save);

        // Event to set up the user information tab info
        EventHandler<Event> event = (Event e) -> {
            if (user.isSelected()) {
                user.setText(student.getName());
                nameInput2.setText(student.getName());
                studentNumberInput2.setText(student.getStudentNumber());

                SpinnerValueFactory<Integer> values7 = new SpinnerValueFactory.IntegerSpinnerValueFactory(student.getGraduationYear(), student.getGraduationYear() + 10);
                endYearSelector2.setValueFactory(values7);

                SpinnerValueFactory<Integer> values8 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, student.getStartYear() + 10);
                startYearSelector2.setValueFactory(values8);
                startYearSelector2.getValueFactory().setValue(student.getStartYear());

                // Giving the start year modifies the endYearSelector spinner to have values ten years furher than the selected start year
                startYearSelector2.valueProperty().addListener((ObservableValue<? extends Integer> obs, Integer oldValue, Integer newValue) -> {
                    SpinnerValueFactory<Integer> values9 = new SpinnerValueFactory.IntegerSpinnerValueFactory(newValue + 1, newValue + 11);
                    endYearSelector2.setValueFactory(values9);
                });
                endYearSelector2.getValueFactory().setValue(student.getGraduationYear());

                degreeProgramSelector2.setValue(student.getDegreeProgramme().getName());

            }
        };

        user.setOnSelectionChanged(event);

        // *** Content for Course Listing Tab starts here ***
        courseListing.setContent(courseListingPane);

        tree.setMaxHeight(550);

        StackPane spane = new StackPane();
        spane.getChildren().add(tree);
        spane.setAlignment(Pos.TOP_LEFT);
        spane.setPrefWidth(600.0);
        AnchorPane.setLeftAnchor(spane, 20.0);
        AnchorPane.setTopAnchor(spane, 20.0);
        AnchorPane.setBottomAnchor(spane, 20.0);

        courseListingPane.getChildren().add(spane);

        TextArea tbox = new TextArea();
        tbox.setPrefSize(350, 300);
        tbox.setWrapText(true);
        AnchorPane.setRightAnchor(tbox, 20.0);
        AnchorPane.setTopAnchor(tbox, 20.0);

        courseListingPane.getChildren().add(tbox);

        tree.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends TreeItem<String>> observable,
                        TreeItem<String> oldValue, TreeItem<String> newValue) -> {
                    Boolean isACourse = false;
                    for (CourseUnit c : sisuAPI.degreeProgramme.getAllCourses()) {
                        if (c.getName().equals(newValue.getValue().split("  ")[0])) {
                            tbox.setText(c.getCourseInfo());
                            isACourse = true;
                            break;
                        }
                    }
                    if (!isACourse) {
                        tbox.setText("");
                    }
                });

        var gradeSelector = new Spinner<Integer>();
        SpinnerValueFactory<Integer> grades = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5);
        gradeSelector.setValueFactory(grades);
        gradeSelector.setEditable(true);
        gradeSelector.setPrefWidth(110);
        AnchorPane.setTopAnchor(gradeSelector, 380.0);
        AnchorPane.setRightAnchor(gradeSelector, 20.0);

        courseListingPane.getChildren().add(gradeSelector);

        var gradeSelector2 = new Spinner<String>();
        ObservableList<String> g = FXCollections.observableArrayList("Hyväksytty", "Hylätty");
        SpinnerValueFactory<String> grades2 = new SpinnerValueFactory.ListSpinnerValueFactory<>(g);
        gradeSelector2.setValueFactory(grades2);
        gradeSelector2.setVisible(false);
        gradeSelector2.setEditable(true);
        gradeSelector2.setPrefWidth(110);
        AnchorPane.setTopAnchor(gradeSelector2, 380.0);
        AnchorPane.setRightAnchor(gradeSelector2, 20.0);

        courseListingPane.getChildren().add(gradeSelector2);

        Button changeGrades = new Button("Vaihda arviointiasteikkoa");
        AnchorPane.setTopAnchor(changeGrades, 380.0);
        AnchorPane.setRightAnchor(changeGrades, 200.0);
        changeGrades.setOnAction((ActionEvent e) -> {
            if (courseListingPane.getChildren().get(courseListingPane.getChildren().indexOf(gradeSelector)).isVisible()) {
                gradeSelector.setVisible(false);
                gradeSelector2.setVisible(true);
            } else {
                gradeSelector2.setVisible(false);
                gradeSelector.setVisible(true);
            }
        });

        courseListingPane.getChildren().add(changeGrades);

        Label gradeLabel = new Label("Arvosana:");
        AnchorPane.setTopAnchor(gradeLabel, 385.0);
        AnchorPane.setRightAnchor(gradeLabel, 135.0);

        courseListingPane.getChildren().add(gradeLabel);

        Button saveAttainment = new Button("Tallenna kurssisuoritus");
        AnchorPane.setTopAnchor(saveAttainment, 420.0);
        AnchorPane.setRightAnchor(saveAttainment, 20.0);
        saveAttainment.setOnAction((ActionEvent e) -> {
            TreeItem<String> selectedCourse = tree.getSelectionModel().getSelectedItem();
            // check if course is selected
            if (selectedCourse != null) {
                for (CourseUnit c : sisuAPI.degreeProgramme.getAllCourses()) {
                    // if course is found under the students degreeProgramme
                    if ((selectedCourse.getValue().split("  ")[0]).equals(c.getName())) {
                        Boolean isAlreadyAnAttainment = false;
                        // check if student already has an attainment cor the course
                        for (Attainment a : student.getAttainments()) {
                            if (a.getCourse().equals(c)) {
                                isAlreadyAnAttainment = true;
                            }
                            break;
                        }
                        // Checking which grading system is selected and adding
                        // the course to students attainments acordingly
                        if (courseListingPane.getChildren().get(courseListingPane
                                .getChildren().indexOf(gradeSelector)).isVisible()
                                && !isAlreadyAnAttainment) {
                            c.setGradingScale(true);
                            int grade = gradeSelector.getValue();
                            student.addAttainment(c, true, grade);
                            tree.getSelectionModel().getSelectedItem()
                                    .setValue(selectedCourse.getValue() + "  "
                                            + "\u2713" + "  " + grade);

                        } else if (!isAlreadyAnAttainment) {
                            c.setGradingScale(false);
                            String grade = gradeSelector2.getValue();
                            boolean passed = true;
                            if (grade.equals("Hylätty")) {
                                passed = false;
                            }
                            student.addAttainment(c, passed, 0);
                            tree.getSelectionModel().getSelectedItem()
                                    .setValue(selectedCourse.getValue()
                                            + "  " + "\u2713" + "  " + grade);
                        }
                        break;
                    }
                }
            }
        });
        
        Alert deleteAttainmentAlert = new Alert(AlertType.CONFIRMATION);
        deleteAttainmentAlert.setTitle("Kurssisuorituksen poistaminen");
        deleteAttainmentAlert.setContentText("Haluatko varmasti poistaa kurssisuorituksen pysyvästi?");
        deleteAttainmentAlert.setHeaderText(null);

        Button deleteAttainment = new Button("Poista kurssisuoritus");
        AnchorPane.setTopAnchor(deleteAttainment, 420.0);
        AnchorPane.setRightAnchor(deleteAttainment, 200.0);
        deleteAttainment.setOnAction((ActionEvent e) -> {
            Optional<ButtonType> result = deleteAttainmentAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TreeItem<String> selectedCourse = tree.getSelectionModel().getSelectedItem();
                if (selectedCourse != null) {
                    for (CourseUnit c : sisuAPI.degreeProgramme.getAllCourses()) {
                        if (c.getName().equals(selectedCourse.getValue().split("  ")[0])) {
                            tree.getSelectionModel().getSelectedItem().setValue(c.getName() + "  " + c.getCredits() + "op");
                            student.removeAttainment(c);
                        }
                    }
                }
            }
        });

        courseListingPane.getChildren().add(saveAttainment);
        courseListingPane.getChildren().add(deleteAttainment);

        // event for clearing the textBox from previous information
        EventHandler<Event> openCourseListing = (Event e) -> {
            if (courseListing.isSelected()) {
                tbox.setText("");
            }
        };

        courseListing.setOnSelectionChanged(openCourseListing);

        // *** content for progress view pane starts here ***
        progressView.setContent(progressViewPane);

        Label degreeName = new Label(student.getDegreeProgramme().getName());
        degreeName.setFont(new Font("Arial", 26));
        degreeName.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(degreeName, 20.0);
        AnchorPane.setLeftAnchor(degreeName, 0.0);
        AnchorPane.setRightAnchor(degreeName, 0.0);

        progressViewPane.getChildren().add(degreeName);

        ProgressIndicator pi = new ProgressIndicator();
        pi.setMinHeight(300.0);
        pi.setMinWidth(300.0);
        AnchorPane.setTopAnchor(pi, 100.0);
        AnchorPane.setLeftAnchor(pi, 50.0);
        pi.setProgress(student.getCredits() / student.getDegreeProgramme().getCredits());

        progressViewPane.getChildren().add(pi);

        Label credits = new Label("Opintopisteitä suoritettu: "
                + Integer.toString(student.getCredits()) + " / "
                + Integer.toString(student.getDegreeProgramme().getCredits()));
        credits.setFont(new Font("Arial", 20));
        AnchorPane.setTopAnchor(credits, 150.0);
        AnchorPane.setLeftAnchor(credits, 400.0);

        progressViewPane.getChildren().add(credits);

        Label average = new Label("Opintojen keskiarvo: "
                + student.getAverage());
        average.setFont(new Font("Arial", 20));
        AnchorPane.setTopAnchor(average, 200.0);
        AnchorPane.setLeftAnchor(average, 400.0);

        progressViewPane.getChildren().add(average);

        // event for setting up the progress view pane information when that tab
        // is opened
        EventHandler<Event> openProgressView = (Event e) -> {
            if (progressView.isSelected()) {
                pi.setProgress((float) student.getCredits() / student.getDegreeProgramme().getCredits());
                credits.setText("Opintopisteitä suoritettu: "
                        + Integer.toString(student.getCredits()) + " / "
                        + Integer.toString(student.getDegreeProgramme().getCredits()));
                average.setText("Opintojen keskiarvo: "
                        + student.getAverage());
                degreeName.setText(student.getDegreeProgramme().getName());
            }
        };

        progressView.setOnSelectionChanged(openProgressView);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Recursive helper function for getting the TreeItems to be shown on the
     * course listing tab
     *
     * @param m module that the modules and courses want to be returned as Tree
     * Items
     * @return TreeItem that has all of the give modules submodules and courses
     * as its children
     */
    public TreeItem<String> getCourseView(Module m) {
        TreeItem<String> module = new TreeItem<>(m.getName());
        // If monule has modules, function calls itsef recursively
        if (!m.getModules().isEmpty()) {
            for (Module item : m.getModules()) {
                TreeItem<String> subModule = getCourseView(item);
                module.getChildren().add(subModule);
            }
            // Regaldes if there is modules or not inside the given module,
            // getting the modules courses
        }
        if (!m.getCourses().isEmpty()) {
            for (CourseUnit c : m.getCourses()) {
                TreeItem<String> course = new TreeItem<>(c.getName() + "  " + c.getCredits() + "op");
                for (Attainment a : student.getAttainments()) {
                    if (a.getCourse().getName().equals(c.getName())) {
                        course.setValue(course.getValue()
                                +  "  " + "\u2713" + "  " + a.getGradeAsString());
                    }
                }
                module.getChildren().add(course);
            }
        }
        return module;
    }
    
    /**
     * Function for returning the current student
     * 
     * @return Student object
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Main function of the program. Launches the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}