# Group 5 - k17 N01
Thành viên:
1. Nguyễn Thị Thùy Linh - ID: 23010633
2. Nguyễn Anh Quân - ID: 23010375
# Bài tập lớn: 02
Nội dung: 
- Giao diện cửa sổ JavaFX.
- Có chức năng quản lý sinh viên.
+ Thêm, sửa, xóa sinh viên.
+ Liệt kê thông tin về sinh viên, có thể lọc ra các sinh viên có điểm trung bình > n (n tùy chọn).
- Có chức năng quản lý môn học.
+ Thêm, sửa, xóa môn học.
- Có chức năng đăng ký học cho sinh viên
+ Sinh viên có thể đăng ký môn học mà mình muốn.
- Có chức năng nhập điểm cho sinh viên
+ Nhập điểm môn học mà sinh viên đã học bằng cách sử dụng chức năng này.
- Dữ liệu được lưu trữ xuống file nhị phân
+ Cần tạo các lớp liên quan đến “sinh viên”, “môn học” để đọc, ghi xuống 1 hay nhiều file.
- Khi làm việc với dữ liệu trong bộ nhớ, dữ liệu cần được lưu trữ dưới dạng các Collection tùy chọn
như ArrayList, LinkedList, Map, ….
- Sinh viên có thể thêm các chức năng vào chương trình để ứng dụng phong phú hơn bằng cách thêm
các nghiệp vụ cho bài toán (tùy chọn).
# 1. Sơ đồ khối yêu cầu
## 1.1  UML Class Diagram
![Untitled](https://github.com/user-attachments/assets/9127a77b-95bf-41e3-a505-32fb2e6af1b6)
## 1.2 UML Activity Diagram
### Đăng nhập: 
![activity-Login](https://github.com/user-attachments/assets/8a43721f-febc-42f5-9b3a-eed207030324)
### Thêm sinh viên:
![activity-Add Student](https://github.com/user-attachments/assets/b2965eb1-3be7-4570-80b1-5ebf1a5fd7b5)
### Sửa sinh viên:
![activity-Update Student ](https://github.com/user-attachments/assets/510f4425-a294-40ee-bfd3-a0740be5d314)
### Xóa sinh viên:
![activity-Delete Student](https://github.com/user-attachments/assets/bfb487fe-7eae-499e-a55c-1a46c9ac0dd3)
### Thêm môn học:
![activity-Add Subject](https://github.com/user-attachments/assets/ecc798aa-a128-483f-ac25-337cd0cbb39d)
### Sửa môn học:
![activity-Update Subject](https://github.com/user-attachments/assets/aa5930af-39ee-4114-be06-79f11fcf2236)
### Xóa môn học:
![activity-Delete Subject](https://github.com/user-attachments/assets/44d9b0da-9b06-48ed-9a72-9cdc535f15bf)
### Sinh viên đăng kí môn học:
![activity-Dang ki mon hoc](https://github.com/user-attachments/assets/875cbf5b-2934-459c-ad8d-c453a22d41a9)
### Đăng xuất: 
![activity-Login](https://github.com/user-attachments/assets/cfb67e32-8c33-435a-9f6e-dfa785b8898d)

