package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	@FXML
	private Label pontosLabel;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label metaLabel;
	
	@FXML
	private ComboBox<String> materialBox;
	
	@FXML
	private TextField quantidadeField;
	
	@FXML
	private Button botaoRegistrar;
	
	@FXML
	private Button reset;
	
	private String[] materiais = {"Papel", "Plástico", "Vidro", "Metal"};
	
	
	private ReciclaveisManager manager = new ReciclaveisManager();
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		materialBox.getItems().addAll(materiais);
		progressBar.setStyle("-fx-accent: #00FF00;");
		
	    manager.carregarDados();

	    // Atualizar Label "pontos" e ProgressBar
	    pontosLabel.setText("Pontos: " + manager.getPontos());
	    progressBar.setProgress(manager.getPontos() / manager.getMeta());
	    metaLabel.setText("Meta: " + manager.getMeta());
	    quantidadeField.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
	    
	    
	    
	    
		
	}
	
	public void checkInput(KeyEvent e) {
	    if (e.getCharacter().equals(".")) {
	        if (quantidadeField.getText().contains(".")) {
	            e.consume();
	        }
	    } else if (!e.getCharacter().matches("[0-9]")) {
	        e.consume();
	    }
	}

	
	public void registrar() {
		
		if(materialBox.getValue() != null && !quantidadeField.getText().isBlank()) {
			String material = materialBox.getValue();
			Double quantidade = Double.valueOf(quantidadeField.getText());
			quantidade = Math.round(quantidade * 100.0) / 100.0;
			manager.registrar(material, quantidade);
			pontosLabel.setText("Pontos: " + manager.getPontos() );
			progressBar.setProgress(manager.getPontos()/manager.getMeta());
			Alert registro = new Alert(AlertType.INFORMATION);
			Stage stage = (Stage) registro.getDialogPane().getScene().getWindow();
		    stage.getIcons().add(new Image("leaf.png"));
			registro.setHeaderText("Registro ecológico salvo com sucesso!");
			registro.show();
			
			if (manager.getPontos() >= manager.getMeta()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			    stageAlert.getIcons().add(new Image("leaf.png"));
				alert.setTitle("Parabéns!");
				alert.setHeaderText("Você alcançou seu objetivo ecológico!");
				alert.setContentText("\"Parabéns! Você alcançou sua meta de reciclagem! Cada esforço importa e está fazendo a diferença para um futuro mais sustentável. Não pare por aqui! Aumente sua meta e continue cuidando do nosso planeta, pois juntos podemos criar um mundo mais limpo e saudável para todos. Seu compromisso é inspirador!\"");
				alert.show();
			}
			materialBox.setValue(null);
			quantidadeField.setText(null);
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("leaf.png"));
			alert.setHeaderText("Atenção!");
			alert.setContentText("Por favor, preencha todos os dados antes de continuar.");
			alert.show();
		}
		
		
	}
	
	public void status() {
		
		String status = manager.status();
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    stage.getIcons().add(new Image("leaf.png"));
		alert.setTitle("Status da Reciclagem");
		alert.setHeaderText(null);
		alert.setContentText(status);
		alert.show();
		
	}
	
	//caixa de diálog para atualizar meta
	public void atualizarMeta() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Meta.fxml"));
		Parent root = loader.load();
		
		MetaController metaController = loader.getController();
		metaController.setParentController(this);
		Stage dialogoStage = new Stage();
		dialogoStage.getIcons().add(new Image("leaf.png"));
		dialogoStage.initModality(Modality.APPLICATION_MODAL);
		dialogoStage.setTitle("Atualizar Meta");
		dialogoStage.setScene(new Scene(root));
		dialogoStage.showAndWait();
		
		
	}
	
	//atualização da meta, utilizada se OK na caixa de diálogo
	public void setMeta(Integer meta) {
		manager.setMeta(meta);
		metaLabel.setText("Meta: " + meta);
		progressBar.setProgress(manager.getPontos()/meta);
	}
	
	public void reset() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	    stage.getIcons().add(new Image("leaf.png"));
		alert.setTitle(null);
		alert.setHeaderText("Tem certeza que deseja redefinir seu progresso na Gincana Ecológica?");
		
		if(alert.showAndWait().get()==ButtonType.OK) {
			manager.limparDados();
			pontosLabel.setText("Pontos: " + manager.getPontos());
		    progressBar.setProgress(manager.getPontos() / manager.getMeta());
		    Alert sucesso = new Alert(AlertType.INFORMATION);
		    Stage stageSucesso = (Stage) sucesso.getDialogPane().getScene().getWindow();
	        stageSucesso.getIcons().add(new Image("leaf.png"));
		    sucesso.setTitle(null);
		    sucesso.setHeaderText("Dados apagados com sucesso!");
		    sucesso.showAndWait();
		   
		}

	}
	


}
