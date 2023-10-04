package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyController implements Initializable{
	@FXML
	Button modifyButton, cancelButton, closeButton;
	@FXML
	TextField nameTextField, idTextField, hakTextField, banTextField, bunTextField;
	@FXML
	PasswordField pw1PasswordField, pw2PasswordField;
	
	
	@FXML
	private void modifyButtonAction(ActionEvent e) {
		
		Boolean checkEmpty = checkEmpty();
		Boolean checkHak = checkHak();
		Boolean checkPw = checkPw();
		
		if(checkEmpty == true && checkHak == true && checkPw == true) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText(Main.global_id + "님의 정보를 변경하시겠습니까?");
			Optional<ButtonType> AR = alert.showAndWait();
			
			if(AR.get() == ButtonType.OK) {
				
				DBconnect conn = new DBconnect();
				Connection conn2 = conn.getConn();
				
				String sql = "update user_table set user_name = ?, user_pw = ?, hak = ?, ban = ?, bun = ?"
						+ " where user_id = ?";
				
				try {
					PreparedStatement ps = conn2.prepareStatement(sql);
					ps.setString(1, nameTextField.getText());
					ps.setString(2, pw1PasswordField.getText());
					ps.setString(3, hakTextField.getText());
					ps.setString(4, banTextField.getText());
					ps.setString(5, bunTextField.getText());
					ps.setString(6, Main.global_id);
					
					ResultSet rs = ps.executeQuery();
					
					if(rs.next()) {
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setContentText(Main.global_id + "님의 정보가 변경되었습니다.");
						alert1.show();
						closeButtonAction(e);
					}
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			

			
			
		}else { 
			if(checkEmpty == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("모든 항목을 입력해주세요.");
			alert.show();
		}
		else if(checkHak == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("학번을 확인해주세요.");
			alert.show();
		}
		else if(checkPw == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("2차 비밀번호를 확인해주세요.");
			alert.show();
		}
	}
}
	
	private Boolean checkPw() {
		
		if(pw1PasswordField.getText().equals(pw2PasswordField.getText())) {
			return true;
		}
		return false;
	}

	private Boolean checkHak() {
		try {
			int hak = Integer.parseInt(hakTextField.getText());
			int ban = Integer.parseInt(banTextField.getText());
			int bun = Integer.parseInt(bunTextField.getText());
		
			if((hak >= 1 && hak <=3) &&
				(ban >= 1 && ban <=12) &&
				(bun >= 1 && bun <=30)) {
				return true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	private Boolean checkEmpty() {
		if(!nameTextField.getText().isEmpty() && !pw1PasswordField.getText().isEmpty() && 
			!pw2PasswordField.getText().isEmpty() && !hakTextField.getText().isEmpty() && 
			!banTextField.getText().isEmpty() && !bunTextField.getText().isEmpty())  {
			return true;
		}
		return false;
	}

	@FXML
	private void cancelButtonAction(ActionEvent e) {
		nameTextField.setText("");
		hakTextField.setText("");
		banTextField.setText("");
		bunTextField.setText("");
		pw1PasswordField.setText("");
		pw2PasswordField.setText("");
	}

	@FXML
	private void closeButtonAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select *"  
				+ " from user_table "
				+ " where user_id = ?";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			ps.setString(1, Main.global_id);
		
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				nameTextField.setText(rs.getString(2));
				idTextField.setText(rs.getString(3));
				pw1PasswordField.setText(rs.getString(4));
				pw2PasswordField.setText(rs.getString(4));
				hakTextField.setText(rs.getString(5));
				banTextField.setText(rs.getString(6));
				bunTextField.setText(rs.getString(7));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}


}
