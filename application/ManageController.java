package application;



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ManageController implements Initializable{

	@FXML
	Button updateButton , DeleteButton , readButton , closeButton;
	
	@FXML
	TextField usernameTextField , userIdTextField , hakTextField , banTextField, bunTextField;
	
	@FXML
	PasswordField pwPasswordField, pw2PasswordField;
	
	@FXML
	TableView<Member> memberTableView;
	
	@FXML
	TableColumn<Member, String > userNameTableColumn , userIdTableColumn , userPwTableColumn , hakTableColumn , banTableColumn , bunTableColumn;
	
	@FXML
	private void updateButtonAction(ActionEvent event) {
		// 빈칸
		// 암호 두개
		// 알맞은 학번
		
		if(checkEmpty() == true && checkPw() == true && checkHak() == true) {
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConn();
			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setContentText(userIdTextField.getText() + "님의 정보를 변경하시겠습니까?");
			Optional<ButtonType> AR = alert1.showAndWait();
			if(AR.get() == ButtonType.OK) {
				String sql = "update user_table "
						+ " set user_name=?, user_pw=?, hak=?, ban=?, bun=? "
						+ " where user_id=?";
				
				try {
					PreparedStatement ps = conn2.prepareStatement(sql);
					ps.setString(1, usernameTextField.getText());
					ps.setString(2, pwPasswordField.getText());
					ps.setString(3, hakTextField.getText());
					ps.setString(4, banTextField.getText());
					ps.setString(5, bunTextField.getText());
					ps.setString(6, userIdTextField.getText());
					
					ps.executeUpdate();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("변경되었습니다.");
					alert.show();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("변경이 취소되었습니다.");
				alert.show();
			}
		}else {
			if(checkEmpty() == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("모두 입력하세요.");
				alert.show();
			}else if(checkPw() == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("암호를 확인해주세요.");
				alert.show();
			}else if(checkHak() == false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("올바른 학번을 입력하세요");
				alert.show();
			}
		}
	}
	
	private boolean checkHak() {
		boolean result = false;
		try {
			int hak = Integer.parseInt(hakTextField.getText());
			int ban = Integer.parseInt(banTextField.getText());
			int bun = Integer.parseInt(bunTextField.getText());
		
			if((hak >= 1) && (hak <= 3) &&
					(ban >= 1) && (ban <= 12) && 
					(bun >= 1) && (bun <= 30)) {
				result = true;
				System.out.println("학번 통과");
			}else {
				System.out.println("학번 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

	private boolean checkPw() {
		
		boolean result = false;
		if(pwPasswordField.getText().equals(pw2PasswordField.getText())) {
			result = true;
			System.out.println("비번 통과");
		}else {
			System.out.println("비번 실패");
		}
		return result;
		
		
	}

	private boolean checkEmpty() {
		boolean result = false;
		if(!usernameTextField.getText().isEmpty() && !pwPasswordField.getText().isEmpty() && 
				!pw2PasswordField.getText().isEmpty() && !hakTextField.getText().isEmpty() && 
				!banTextField.getText().isEmpty() && !bunTextField.getText().isEmpty()) {
				result = true;
				System.out.println("빈칸 통과");
			}else {
				System.out.println("빈칸 실패");
			}
		return result;
		
	}

	@FXML
	private void deleteButtonAction(ActionEvent event) {
		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setContentText(userIdTextField.getText() + "님의 정보를 삭제하시겠습니까?");
		Optional<ButtonType> AR = alert1.showAndWait();
		if(AR.get() == ButtonType.OK) {
			DBconnect conn = new DBconnect();
			Connection conn2 = conn.getConn();
			
			String sql = "DELETE FROM user_table"
					+ " where user_id=?";
			try {
				PreparedStatement ps = conn2.prepareStatement(sql);
				ps.setString(1, userIdTextField.getText());
				ResultSet rs = ps.executeQuery();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("삭제 되었습니다.");
			alert.show();
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("삭제 취소하셨습니다");
			alert.show();
		}
	}
	
	@FXML
	private void memberTableViewAction(MouseEvent e) {
		if(memberTableView.getSelectionModel().getSelectedItem() != null) {
			usernameTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getName());
			userIdTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getId());
			pwPasswordField.setText(memberTableView.getSelectionModel().getSelectedItem().getPw());
			pw2PasswordField.setText(memberTableView.getSelectionModel().getSelectedItem().getPw());
			hakTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getHak());
			banTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getBan());
			bunTextField.setText(memberTableView.getSelectionModel().getSelectedItem().getBun());
		}
	}
	
	@FXML
	private void readButtonAction(ActionEvent event) {
		// DB 연결
		// 사용자 테이블에 있는 자료 전부 가져오기 정렬기준 idx (SQL 작성 , 실행)
		
		DBconnect conn = new DBconnect();
		Connection conn2 = conn.getConn();
		
		String sql = "select *"
					+ " from user_table"
					+ " order by idx";
		
		try {
			PreparedStatement ps = conn2.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			ObservableList<Member> memberlist = FXCollections.observableArrayList();
			
			while(rs.next()) {
				
				memberlist.add(
						new Member(
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getString(7)
								)
						);
			memberTableView.setItems(memberlist);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	@FXML
	private void closeButtonAction(ActionEvent event) {
		
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		userIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		userPwTableColumn.setCellValueFactory(new PropertyValueFactory<>("pw"));
		hakTableColumn.setCellValueFactory(new PropertyValueFactory<>("hak"));
		banTableColumn.setCellValueFactory(new PropertyValueFactory<>("ban"));
		bunTableColumn.setCellValueFactory(new PropertyValueFactory<>("bun"));
		
	}
}
