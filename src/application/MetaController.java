package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MetaController implements Initializable {

	@FXML
	TextField metaField;

	@FXML
	Button metaOK;

	@FXML
	Button metaCancela;

	private Controller parentController;

	public void setParentController(Controller parentController) {
		this.parentController = parentController;
	}

	public void definirMeta() {

		try {
			int meta = Integer.valueOf(metaField.getText());
			parentController.setMeta(meta);

			Stage stage = (Stage) metaOK.getScene().getWindow();
			stage.close();

		} catch (NumberFormatException e) {
			e.printStackTrace(); 
		}
	}

	public void cancela() {
		Stage stage = (Stage) metaCancela.getScene().getWindow();
		stage.close();
	}
	
	public void checkInput(KeyEvent e) {
		if(!e.getCharacter().matches("[0-9]*")) {
			e.consume();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		metaField.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
		
	}



}
