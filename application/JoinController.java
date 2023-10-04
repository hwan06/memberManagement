package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class JoinController {

	@FXML
	private TextField nameTextField, idTextField, hakTextField, banTextField,
			bunTextField;
	@FXML
	private Button joinButton, cancelButton, closeButton;
	@FXML
	private PasswordField pw1PasswordField, pw2PasswordField;
	
	
	
	@FXML
	private void joinButtonAction(ActionEvent e) {
		
		Boolean checkEmpty = false;
		Boolean checkId = false;
		Boolean checkPw = false;
		Boolean checkNum = false;
		
		checkEmpty = ischeckEmpty(); // 빈칸 확인
		checkId = ischeckId(); // ID 중복 여부
		checkPw = ischeckPw(); // 암호 확인
		checkNum = ischeckNum(); // 올바른 학번
		
		//빈칸, id중복 확인, 비번 두개 일치, 학번 확인
		if(checkEmpty == true && checkId == true && checkPw == true && checkNum == true) {
		
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConn();
			
			String sql = "insert into user_table(idx, USER_NAME, USER_ID, USER_PW, HAK, BAN, BUN)"
					+ " values(user_idx_pk.nextVal, ?, ?, ?, ?, ?, ?)";
			
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				ps.setString(1, nameTextField.getText());
				ps.setString(2, idTextField.getText());
				ps.setString(3, pw1PasswordField.getText());
				ps.setString(4, hakTextField.getText());
				ps.setString(5, banTextField.getText());
				ps.setString(6, bunTextField.getText());
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("회원정보가 저장되었습니다.");
					alert.show();
					closeButtonAction(e);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("회원정보 저장에 실패하였습니다.");
					alert.show();
				}
				
				conn2.close();
				rs.close();
				ps.close();
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			
			
		}else {
			if(checkEmpty == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("모두 입력하세요.");
				alert.show();
			}
			else if(checkId == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("중복된 ID입니다.");
				alert.show();
			}
			else if(checkPw == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("2차 비밀번호를 확인해주세요.");
				alert.show();
			}
			else if(checkNum == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("올바른 학년, 반 ,번호를 입력해주세요.");
				alert.show();
			}
		}
	}

	private Boolean ischeckNum() {
		
		Boolean result = false;
		
		try {
			int hak = Integer.parseInt(hakTextField.getText());
			int ban = Integer.parseInt(banTextField.getText());
			int bun = Integer.parseInt(bunTextField.getText());
			
			if( (hak >=1 && hak <=3) &&
				(ban >=1 && ban <=12)&&
				(bun >=1 && bun <=30)) {
				result = true;
			}
		}catch (Exception e) {
			
		}
		
		
		return result;
	
	}

	private Boolean ischeckPw() {
		
		Boolean result = false;
		
		if(pw1PasswordField.getText().equals(pw2PasswordField.getText())) {
			result = true;
		}
		return result;
	}

	private Boolean ischeckId() {
		
		Boolean result = false;
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select USER_ID"
				+ " from user_table"
				+ " where user_id = ?";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			ps.setString(1, idTextField.getText());
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				result = false;
			}else {
				result = true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result;
	}

	private Boolean ischeckEmpty() {
		
		Boolean result = false;
		
		if(nameTextField.getText().isEmpty()==false && idTextField.getText().isEmpty()==false && pw1PasswordField.getText().isEmpty()==false &&
				pw2PasswordField.getText().isEmpty()==false && hakTextField.getText().isEmpty()==false && banTextField.getText().isEmpty()==false &&
				bunTextField.getText().isEmpty()==false) {
			result = true;
		}
		return result;
	}

	@FXML
	private void cancelButtonAction(ActionEvent e) {
		nameTextField.setText("");
		idTextField.setText("");
		pw1PasswordField.setText("");
		pw2PasswordField.setText("");
		hakTextField.setText("");
		banTextField.setText("");
		bunTextField.setText("");
	}

	@FXML
	private void closeButtonAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
