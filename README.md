# 회원관리 프로그램
![image](https://github.com/hwan06/memberManagement/assets/114748934/a7c6ccc6-3cbd-4b78-9dfd-aa348857e560)   

관리자에 체크하면 관리자로 로그인 가능하고 기본은 사용자 로그인 방식이다.
```java
if(adminCheckBox.isSelected() == true) { // 관리자 체크박스에 체크 되어있고
				if(isAdminLogin() == true) { // 메소드를 만들어서 관리자 DB에 저장되어 있는 데이터와 입력받은 데이터를 비교하여 값이 true이라면
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("관리자 로그인 성공!");
					alert.show();
					loginButton.setText("로그아웃"); // 로그인 버튼을 로그아웃 버튼으로 전환
					joinButton.setDisable(false); // 회원가입 버튼 활성화
					joinButton.setText("회원관리메뉴"); // 회원가입 버튼을 회원관리메뉴로 전환
					Adminlogin = isAdminLogin();
				}
```
![image](https://github.com/hwan06/memberManagement/assets/114748934/7710b77a-1a55-49db-ac3f-e043c30952cb)

```java
else { // 관리자 체크박스에 체크 X
				if(isUserLogin() == true) { // 메소드를 만들어서 사용자DB에 저장되어 있는 데이터와 입력받은 데이터를 비교하여 값이 true이라면
					Main.global_id = idTextField.getText();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("사용자 로그인 성공!");
					alert.show();
					loginButton.setText("로그아웃"); // 로그인 버튼을 로그아웃 버튼으로 전환
					joinButton.setText("회원정보수정"); // 회원가입 버튼을 회원정보수정으로 전환
					Userlogin = isUserLogin(); 
				}
```
![image](https://github.com/hwan06/memberManagement/assets/114748934/efcae498-9409-4481-91d5-1c82c612a341)

## 회원가입(사용자 전용)
![image](https://github.com/hwan06/memberManagement/assets/114748934/69cf3285-aa38-49e0-a975-f81b697be39a)   
그림의 모든 항목에 조건에 맞추어 작성한 뒤 회원 가입하기 버튼을 누르면 회원정보가 저장된다.

## 본인정보 수정(사용자 전용)
![image](https://github.com/hwan06/memberManagement/assets/114748934/3124eb00-8d39-4c5a-b7f0-32a9087fb045)
수정하고 싶은 정보를 바꾼 뒤에 회원 정보 수정하기를 선택하면 정보가 수정된다.

## 회원관리메뉴(관리자 전용)
![image](https://github.com/hwan06/memberManagement/assets/114748934/4ad55bf5-7b62-4727-bf8f-b3da02b481c1)   
밑의 표에서 사용자를 선택하면, 그 사용자의 정보가 자동으로 입력되어 바꾸고 싶은 정보를 바꾼 뒤에 수정 버튼을 누르면 수정된다.
