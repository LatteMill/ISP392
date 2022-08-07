DROP DATABASE IF EXISTS manageuser;
create database manageuser;
use manageuser;

CREATE TABLE gender (
    ma INT NOT NULL AUTO_INCREMENT,
    gioi_tinh NVARCHAR(10),
    PRIMARY KEY (ma)
);
insert into gender(gioi_tinh)
values (N'Nam'), (N'Nữ'), (N'Chưa rõ');

/*create table trang_thai(
	ma int NOT NULL AUTO_INCREMENT,
    trang_thai1 varchar(10),
    primary key(ma)
);
insert into status(trang_thai)
values ('Active'), ('Deactive');*/
#============================================================== USER MODEL ===========================================================
create table user(
	id int primary key auto_increment,
    email varchar(30) not null unique, 
	password varchar(100), 
    captcha varchar(6), 
    verify boolean,
    fullName varchar(30) not null, 
    rollNumber varchar(20) unique,
    mobile varchar(20), 
    sex int,
    roles int not null, 
    status int,  
    jobPosition varchar(30),  
    company varchar(20),  
    address varchar(20),
	last_login varchar(100), 
	foreign key(sex) references gender(ma)
    #foreign key (status) references trang_thai(trang_thai1)
);
insert into user (email, password, captcha, verify, fullName,mobile, sex, roles, status, jobPosition, company, address)
values /*( 'linhhnhe163254@fpt.edu.vn', 'L123456L','hyalu7',true, 'linh', '0978456123',2,3,1,null,null,null),*/
( 'philiple182@gmail.com', 'P123456P','miksj4',true, 'phuoc', '0987654129',1,1,1,null,null,null);


insert into user (password, rollNumber, fullName,sex, email, mobile,jobPosition, company, address, roles, status)
values 
('abc123456', 'FUDN', 'Hải Anh', 2, 'anhknhe@gmail.com', '0123456987','Student', 'FPT', 'Hà Giang', 4, 2),
#3
('abc123456', 'FUL', 'Nguyễn Trung Kiên', 1, 'kienntvn@gmail.com', '0912656836','Lecture', 'FPT', 'Thái Bình', 4, 1),
#4
('abc123456', 'FUHL', 'Nguyễn Thanh Mai', 1, 'maint@gmail.com', '0912656863','Lecture', 'FPT', 'Ninh Binh', 4, 1),
#5
('abc123456', 'FUHCM', 'Trần Văn Tuấn', 1, 'tuantv@yahoo.com', '0912642835', 'Engineer', 'FPT', 'Hòa Bình', 4, 3),
#6
('abc123456', 'HE160111', 'Ngọc Hân ',3, 'hannnhhe@gmail.com', '0937486421', 'Student', 'FPT', 'Cao Bằng', 4, 2),
#7
('abc123456', 'HE140123', 'Minh Hiếu', 3, 'minhhieu@gmail.com', '0462382761','Other', 'FPT', 'Quang Ninh', 2, 3),
#8
('abc123456', 'HL001', 'Hồ Phan', 1, 'hophan@gmail.com', '0945271836', 'Teacher', 'Viettel', 'Hà Nội', 4, 2),
#9
('abc123456', 'HA140177', 'Văn Hiến', 1, 'hienvan@gmail.com', '0984623782', 'Student', 'FPT', 'Hà Nội', 4, 2),
#10
('abc123456', 'HS150256', 'Ngô Liên', 2, 'lienngo@gmail.com', '0937561849','Student', 'FPT', 'Thanh Hóa', 4, 2),
#11
('abc123456', 'HM160987', 'Trần Thu Thủy', 2, 'thuytt@gmail.com', '0912656836', 'Student', 'FPT', 'Hà Giang', 4, 1),
#12
('abc123456', 'HA120023', 'Người Không Tên', 1, 'nguoikt@gmail.com', '0123456987', 'Student', 'FPT', 'Bắc Ninh', 4, 1),
#13
('abc123456', 'HUM7862', 'Lê Khả Dụng', 2, 'dunglk@fpt.com', '0913456889', 'Teacher', 'FPT', 'Hà Giang', 3, 2),
#14
('abc123456', 'HLMU888', 'Mộc Miên', 2, 'anhxnhe@gmail.com', '0921357822', 'Co-Worker', 'VNPT', 'Thanh Hóa', 4, 2),
#15
('abc123456', 'HAC88', 'Hiền Mai', 2, 'maihn@gmail.com', '0921356593', 'Co-Worker', 'VNPT', 'Thanh Hóa', 3, 1),
#16
('abc123456', 'HL28478', 'Hiếu Hiền', 1, 'hienhieu@gmail.com', '0975037822', 'Co-Worker', 'VNPT', 'Thanh Hóa', 3, 2),
#17
('abc123456', 'HX7864', 'Minh Hương ', 2, 'aminhhuong@gmail.com', '0846254759', 'Student', 'Vin', 'Hà Giang', 4, 2),
#18
('abc123456', 'HB08837', 'Hong Hanh HN ', 2, 'honghanhhng@gmail.com', '0846254759', 'BA', 'Vin', 'Ninh Binh', 3, 1)

;
#============================================================== SETTING MODEL ===========================================================
select* from user;


create table setting(
	setting_id int  NOT NULL AUTO_INCREMENT,
    type_id int,
    setting_title varchar(50),
    setting_value varchar(50),
    display_order int,
    status int,
    primary key(setting_id)
);

/*
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Nhật 1', 'Medium', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Nhật 2', 'Advange', 6, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Trung 1', 'Simple', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Trung 2', 'ABC', 3, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values 		(2, 'Cờ Vây', 'Medium', 1, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'C/C#', 'Advange', 5, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'SSL101', 'Simple', 11, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'Kỹ năng mềm', 'AXb', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'ISP391', 'Advange', 1, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'C/C++', 'Complex', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'Python', 'Simple', 5, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'Quản lý dự án', '15', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'MAE', 'Medium', 3, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'LAB211', 'Complex', 10, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'PRJ', 'Hard', 6, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Subject Topic', '3ND', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'MAD', 'Medium', 1, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Hàn 1', 'Complex', 1, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'DGT', 'Hard', 1, 2 );
*/
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Video Lesson', 'Easy', 3, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(1, 'MAS', 'Medium', 3, 1 );
#insert into setting (type_id, setting_title, setting_value, display_order, status)
#values		(2, 'Hàn 2', 'Advange', 1, 2 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'Debate', 'Hard', 1, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(2, 'HTML Lesson', '5', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(3, 'IOT', 'Medium', 3, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(4, 'Trung 3', 'Advanced', 5, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(3, 'Group skills', 'Hard', 5, 1 );
insert into setting (type_id, setting_title, setting_value, display_order, status)
values		(4, 'Military', 'Medium', 3, 1 );

select*from setting;
#============================================================== SUBJECT MODEL ===========================================================
create table subject(
	subject_id int primary key auto_increment,
    subject_code varchar(10) not null unique,
    subject_name varchar(50) not null,
    author_id int,
    foreign key (author_id) references user(id),
    status boolean, 
    description varchar(30)
);

insert into subject( subject_code, subject_name, author_id, status, description)
values("ITA301","Information System",2,true,"IS subject");
insert into subject( subject_code, subject_name, author_id, status, description)
values("MAS291","Math Probality",3,true, "SE subject");
insert into subject( subject_code, subject_name, author_id, status, description)
values("JPD113","Japanese 1",1,true,null);
insert into subject( subject_code, subject_name, author_id, status, description)
values("JPD123","Japanese 2",3,true,null);
insert into subject( subject_code, subject_name, author_id, status, description)
values("CSD201","Co so du lieu",2,true,"SE Subject");
insert into subject( subject_code, subject_name, author_id, status, description)
values("ENG123","English 2",3,true, null);
insert into subject( subject_code, subject_name, author_id, status, description)
values("DBI201","Database Program",4,true, "Database");
insert into subject( subject_code, subject_name, author_id, status, description)
values("DBI202","Data Analyst",3,true, "Database");
insert into subject( subject_code, subject_name, author_id, status, description)
values("CSD301","Control Diagram",4,true, "Analyst");
insert into subject( subject_code, subject_name, author_id, status, description)
values("MAS202","Mathematic Machine-Leaning",1,true, "Math");

#update subject set status=false where subject_id = 6;
select user.fullname, subject.subject_code, subject.subject_name
from user join subject
on user.id = subject.author_id;

select * from subject;
#============================================================== SUBJECT SETTING MODEL ===========================================================
create table subject_setting(
	setting_id int primary key auto_increment,
    subject_id int not null,
    type_id int,
    setting_title varchar(50) not null,
    setting_value int,
    display_order int,
    status boolean,
    foreign key(subject_id) references subject(subject_id));

insert into subject_setting(subject_id, type_id, setting_title, setting_value, display_order, status ) values 
(1,1,"Complex",240,100,1),
(1,2,"Medium",160,75,1),
(1,3,"Complex",100,50,1),
(2,1,"Complex",240,100,1),
(2,2,"Medium",160,75,1),
(2,3,"Complex",100,50,1),
(3,1,"Complex",240,100,1),
(3,2,"Medium",160,75,1),
(3,3,"Complex",100,50,1),
(4,1,"Complex",240,100,1),
(4,2,"Medium",160,75,1),
(4,3,"Complex",100,50,1),
(5,1,"Complex",240,100,1),
(5,2,"Medium",160,75,1),
(5,3,"Complex",100,50,1),
(6,1,"Complex",240,100,1),
(6,2,"Medium",160,75,1),
(6,3,"Complex",100,50,1),
(7,1,"Complex",240,100,1),
(7,2,"Medium",160,75,1),
(7,3,"Complex",100,50,1),
(8,1,"Complex",240,100,1),
(8,2,"Medium",160,75,1),
(8,3,"Complex",100,50,1);

#============================================================== ITERATION MODEL ===========================================================
create table iteration(
	iteration_id int primary key auto_increment,
    iteration_name varchar(50),
    subject_id int,
    duration double,
    status int,
	foreign key (subject_id) references subject(subject_id)
);

insert into iteration(iteration_name, subject_id, duration, status)
values('Iteration 1', 1, 2, 1),
('Iteration 2', 2, 2, 1),
('Iteration 3', 3, 2, 1),
('Iteration 4', 4, 2, 1),
('Iteration 5', 5, 2, 1);

create table term(
	ma int NOT NULL AUTO_INCREMENT,
    ki_hoc varchar(10),
    primary key(ma)
);
insert into term(ki_hoc)
values (N'Spring'), (N'Summer'), (N'Fall');
#select * from term;

#============================================================== CLASS MODEL ===========================================================
create table class(
	class_id int primary key auto_increment,
    class_code varchar(10),
    trainer_id int,
    subject_id int,
    class_year int,
    class_term int,
    block5_class int,
    status int,
    foreign key(trainer_id) references user(id),
    foreign key(subject_id) references subject(subject_id),
    foreign key(class_term) references term(ma)
);

insert into class(class_code, trainer_id, subject_id, class_year, class_term, block5_class, status)
values('SE1610', 13, 2, 2020, 1, 1, 2),
('SE1632', 13, 5, 2023, 2, 2, 1),
('SE1620', 13, 5, 2022, 3, 1, 2),
('SE1639', 15, 3, 2022, 2, 2, 1),
('SE1626', 15, 2, 2022, 3, 2, 1),
('SE1615', 16, 5, 2025, 1, 1, 1),
('SE1629', 15, 4, 2022, 1, 2, 1),
('SE1636', 13, 2, 2022, 3, 2, 1),
('SE1633', 16, 2, 2022, 2, 2, 1),
('SE1633', 13, 3, 2023, 3, 2, 1),
('SE1630', 15, 4, 2024, 3, 2, 1),
('SE1636', 16, 2, 2021, 2, 2, 1),
('SE1637', 13, 7, 2022, 1, 2, 1),
('SE1630', 15, 3, 2022, 1, 2, 1);



select class.class_id, class.class_code, user.fullname, subject.subject_code, class.class_year, class.class_term,
class.block5_class, class.status 
from subject join class
on subject.subject_id = class.subject_id
join user on subject.author_id = user.id;


#============================================================== Criteria MODEL ===========================================================
create table evaluation_criteria(
	criteria_id int primary key auto_increment,
    iteration_id int,
    evaluation_weight double,
    team_evaluation int,
    criteria_order int,
    max_loc int,
    status int,
    foreign key(iteration_id) references iteration(iteration_id)
);
insert into evaluation_criteria(iteration_id, evaluation_weight
, team_evaluation, criteria_order, max_loc, status)
values(1, 65.5, 1, 1, 60, 1),
(3, 75.45, 1, 2, 120, 1),
(2, 36, 1, 1, 80, 1),
(1, 65.2, 1, 1, 80, 1),
(2, 65.5, 1, 1, 60, 1);

#============================================================== TEAM MODEL ===========================================================
create table team(
	team_id int primary key auto_increment,
    team_name varchar(30),
	class_id int not null,
	topic_code varchar(20) not null,
	topic_name varchar(50) not null,
	gitlab_url varchar(100) unique,
	status boolean,
	foreign key(class_id) references class(class_id)
);

insert into team(class_id, team_name, topic_code, topic_name, gitlab_url, status)
values(1,"Team 1-SE1610", "SMP12", "Student Manager Program", "link1", 1),
(1,"Team 2-SE1610", "SMP12", "Student Manager Program", "link2", 1),
(1,"Team 3-SE1610", "SMP12", "Student Manager Program", "link3", 1),
(2,"Team 1-SE1632", "FBM202", "Fix Bug Program", "link4", 0),
(2,"Team 2-SE1632", "FSM112", "Fruit Shop Program", "link5", 0),
(2,"Team 3-SE1632", "FSM115", "Function System Manager", "link6", 1),
(3,"Team 1-SE1620", "CSR103", "Coursera Check 1", "link7", 1),
(3,"Team 2-SE1620", "CSR103", "Cousera Check 2", "link8", 1),
(3,"Team 3-SE1620", "CSR103", "Coursera Check 3", "link9", 0),
(3,"Team 4-SE1620", "CSR103", "Coursera Check 4", "link10", 1),
(4,"Team 1-SE1639", "TSM142", "Teacher Manager Program", "link11", 1),
(4,"Team 2-SE1639", "ESM142", "Education Manager Program", "link12", 1),
(4,"Team 3-SE1639", "PSM142", "Product Shop Program", "link13", 1);

select * from team;

#SET DATE_FORMAT (date,'%d/%m/%Y');

#============================================================== MILESTONE MODEL ===========================================================
create table milestone(
	milestone_id int primary key auto_increment,
    milestone_name varchar(30),
    iteration_id int,
    class_id int,
    from_date date,
    to_date date,
    status int,
    foreign key(iteration_id) references iteration(iteration_id),
    foreign key(class_id) references class(class_id),
    constraint to_date check ( to_date > from_date )
);
#insert into milestone(iteration_id, class_id, from_date, to_date, status)
#values(3,2,'2022-01-22','2022-02-21',1);

insert into milestone(milestone_name, iteration_id, class_id, from_date, to_date, status) values
('M1-SE1610',1,1,'2022-01-01','2022-02-02',1),
('M2-SE1610',2,1,'2022-02-02','2022-03-03',1),
('M3-SE1610',3,1,'2022-03-03','2022-04-04',1),
('M4-SE1610',4,1,'2022-04-04','2022-05-05',1),
('M1-SE1632',1,2,'2022-01-01','2022-02-02',1),
('M2-SE1632',2,2,'2022-02-02','2022-03-03',1),
('M3-SE1632',3,2,'2022-03-03','2022-04-04',1),
('M4-SE1632',4,2,'2022-04-04','2022-05-05',1),
('M1-SE1620',1,3,'2022-01-01','2022-02-02',1),
('M2-SE1620',2,3,'2022-02-02','2022-03-03',1),
('M3-SE1620',3,3,'2022-03-03','2022-04-04',1),
('M4-SE1620',4,3,'2022-04-04','2022-05-05',1),
('M1-SE1639',1,4,'2022-01-01','2022-02-02',1),
('M2-SE1639',2,4,'2022-02-02','2022-03-03',1),
('M3-SE1639',3,4,'2022-03-03','2022-04-04',1),
('M4-SE1639',4,4,'2022-04-04','2022-05-05',1),
('M1-SE1626',1,5,'2022-01-01','2022-02-02',1),
('M2-SE1626',2,5,'2022-02-02','2022-03-03',1),
('M3-SE1626',3,5,'2022-03-03','2022-04-04',1),
('M4-SE1626',4,5,'2022-04-04','2022-05-05',1),
('M1-SE1615',1,6,'2022-01-01','2022-02-02',1),
('M2-SE1615',2,6,'2022-02-02','2022-03-03',1),
('M3-SE1615',3,6,'2022-03-03','2022-04-04',1),
('M4-SE1615',4,6,'2022-04-04','2022-05-05',1),
('M1-SE1629',1,7,'2022-01-01','2022-02-02',1),
('M2-SE1629',2,7,'2022-02-02','2022-03-03',1),
('M3-SE1629',3,7,'2022-03-03','2022-04-04',1),
('M4-SE1629',4,7,'2022-04-04','2022-05-05',1),
('M1-SE1636',1,8,'2022-01-01','2022-02-02',1),
('M2-SE1636',2,8,'2022-02-02','2022-03-03',1),
('M3-SE1636',3,8,'2022-03-03','2022-04-04',1),
('M4-SE1636',4,8,'2022-04-04','2022-05-05',1);

#update milestone SET from_date = '2022-01-19' where milestone_id = 1;

Select a.milestone_id, a.iteration_id, a.class_id, 
DATE_FORMAT(a.from_date,'%d/%m/%Y') as from_date, 
DATE_FORMAT(a.to_date,'%d/%m/%Y') to_date, a.status, 
a.milestone_name,
b.class_code,
c.iteration_name
from milestone as a join class as b on a.class_id = b.class_id 
join iteration as c on a.iteration_id = c.iteration_id;
#=============================Feature=====================================
create table feature(
	feature_id int primary key auto_increment,
     feature_name varchar(50),
     team_id int,
    foreign key (team_id) references team(team_id),
    status int
);
insert into feature(feature_id,team_id,feature_name,status)
values (1,1,"Trainer",1),
(2,2,"Student",3),
(3,2,"Student",1),
(4,1,"Author",2);


select * from milestone;

#Bang hien thi thong tin sinh vien
/*select user.email, user.password,
user.captcha, user.verify,
user.fullName, user.rollNumber, user.mobile,
gender.gioi_tinh as Gender, user.roles,
user.status, user.jobPosition,
user.company, user.address
from gender join user on 
gender.ma = user.sex;
*/

#SET DATE_FORMAT (date,'%d/%m/%Y');
#=====================================================Class user MODEL ====================================================
#SET DATE_FORMAT (date,'%d/%m/%Y');

create table class_user(
	class_id int,
    team_id int,
    user_id int,
    foreign key (class_id) references class(class_id),
    foreign key (team_id) references team(team_id),
    foreign key (user_id) references user(id),
    team_leader boolean,
    dropout_date date,
    user_notes varchar(100),
    final_pres_eval int,
    final_topic_eval int,
    status int,
	primary key(class_id,team_id,user_id)
);
#class 6 team 5 user 15
insert into class_user( class_id, team_id, user_id, team_leader, dropout_date,final_pres_eval, final_topic_eval, status)
value
(1, 1, 1, true,'2022-09-20',8, 8, 1 ),
(1, 1, 2, false,'2022-09-20',8, 8, 1 ),
(1, 2, 3, false,'2022-09-20',8, 8, 1 ),
(1, 4, 4, false,'2022-09-20',8, 8, 1 ),
(2, 4, 5, true,'2022-06-20',7, 9, 2 ),
(2, 3, 6, false,'2022-06-20',7, 9, 2 ),
(2, 3, 7, false,'2022-06-20',7, 9, 2 ),
(3, 8, 8, false,'2022-06-20',7, 9, 2 ),
(3, 8, 9, true,'2022-09-20',9, 6, 3 ),
(3, 9, 10, false,'2022-09-20',9, 6, 3 ),
(4, 11, 11, true,'2022-07-20',9, 8, 1 ),
(4, 12, 12, false,'2022-07-20',9, 8, 2 )
;
select*from user;
select*from class_user;
select c.class_id, t.team_id, cu.user_id, u.roles, u.fullName, u.email, cu.team_leader, cu.dropout_date,
CONCAT(cu.final_pres_eval, " ", cu.final_topic_eval) as Grade, cu.status
from class_user as cu join class as c on cu.class_id= c.class_id
						join team as t on cu.team_id= t.team_id
                        join user as u on cu.user_id= u.id;
create table functionTable(
	function_id int primary key auto_increment,
    team_id int,
    function_name varchar(100),
	feature_id int,
    access_roles varchar(100),
    description varchar(100),
    complexity_id int,
    owner_id int,
    priority varchar(100),
    status int,
	foreign key (team_id) references team(team_id),
	foreign key (feature_id) references feature(feature_id)
);
insert into functionTable(function_id,team_id,function_name, feature_id,access_roles,description,complexity_id,owner_id,priority,status)
 values(1,1,'function 1',1,'dsad','dfds',3,2,'fffs',1),
 (2,3,'function 2',1,'czxad','uufds',2,1,'fffs',2),
 (3,2,'function 3',1,'asad','llfds',5,3,'fffs',3),
 (4,4,'function 4',1,'nsad','kkds',1,4,'fffs',4);
 select *from functionTable;
create table tracking(
	tracking_id int not null primary key auto_increment,
    team_id int,
    milestone_id int,
    function_id int,
    foreign key (team_id) references team(team_id),
    foreign key (milestone_id) references milestone(milestone_id),
    foreign key (function_id) references functionTable(function_id),
    assigner_id int,
    assignee_id int,
    tracking_note varchar(100),
    updates boolean,
    status int
);

#team 5 milestone 4 fuction_id tam cho la 4
insert into tracking (team_id, milestone_id, function_id, assigner_id,assignee_id,  status) 
values
(1,1,1,1,1,1),
(3,1,1,1,7,3),
(1,1,1,1,3,3),
(2,2,4,2,4,1),
(2,2,4,4,5,3),
(2,2,4,4,6,2),
(3,3,2,3,7,1),
(2,2,4,4,8,2),
(2,3,2,3,9,1),
(2,2,4,4,10,2),
(3,3,2,3,11,1),
(3,3,2,3,12,1),
(2,2,4,4,13,2),
(3,3,2,3,14,1),
(3,3,2,3,15,1),
(2,2,4,4,16,2),
(1,3,2,3,17,1),
(3,3,2,3,1,1),
(2,2,4,4,2,2),
(3,3,2,3,3,1);
select*from tracking;
select t.tracking_id, t.team_id, t.milestone_id, t.function_id, t.assigner_id, us.fullName as assigner_name, 
t.assignee_id, u.fullName, u.email as assignee_name, t.tracking_note, t.updates, t.status
from tracking as t 	join user as u on t.assignee_id= u.id
					join user as us on t.assigner_id= us.id
					join functiontable as f on t.function_id=f.function_id;
#============================================================ISSUE MODEL =====================================================

CREATE TABLE Issue (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    assignee_id INT,
    description VARCHAR(30),
    gitlab_id INT,
    gitlab_url VARCHAR(30),
    created_at DATE,
    due_date DATE,
    team_id INT,
    milestone_id INT,
    function_ids varchar(30),
    labels VARCHAR(30),
    status INT,
    issue_title varchar(30),
    FOREIGN KEY (assignee_id) REFERENCES user (id),
    FOREIGN KEY (team_id) REFERENCES team (team_id)
);

insert into Issue(assignee_id, description, gitlab_id, gitlab_url, 
created_at, due_date, team_id, milestone_id, function_ids, labels, status, issue_title)
values (1,'Bai 1',1,'link1','2022-01-01','2022-04-04',1,1,'1,2,3','lables',1, "Check bug" ),
(2,'Bai project',3,'link3','2022-03-01','2022-12-04',1,2,'1,2,3,4','lables2',1, "Console idea" ),
(3,'mon ITA',4,'link4','2022-02-01','2022-12-23',1,4,'1,2,3,4','lables3',1, "WBS idea" ),
(4,'mon ISP',2,'link2','2022-03-07','2022-11-04',3,2,'1,2,3','lables4',1, "Fix screen A" ),
(5,'Project demo csd',5,'link5','2022-01-09','2022-10-03',3,4,'1,2','lables5',1, "Check graph tree" ),
(6,'Demo DBI',8,'link8','2022-03-02','2022-08-04',3,3,'3,4','lables6',1, "For select issue" ),
(7,'Check test java',7,'link7','2022-04-01','2022-09-24',3,2,'1,4','lables7',1, "Test Java code" ),
(8,'Python',6,'link6','2022-05-12','2022-07-29',4,2,'1,3,4','lables8',1, "Testing Python" ),
(9,'C++',9,'link9','2022-01-01','2022-11-29',4,3,'1,4','lables9',1, "Testing C++" );


SELECT issue_id, assignee_id, lower(description), gitlab_id, gitlab_url, DATE_FORMAT(created_at,'%d/%m/%Y') as created_at, DATE_FORMAT(due_date,'%d/%m/%Y') as due_date, team_id, milestone_id, function_ids, labels, status, issue_title
FROM Issue ;
/*
select class_user.team_id, team.team_name, team.status from class_user join team 
on team.team_id = class_user.team_id;

SELECT a.issue_id, a.assignee_id, b.fullname, a.description, a.gitlab_id, a.gitlab_url, 
DATE_FORMAT(a.created_at,'%d/%m/%Y') as created_at, DATE_FORMAT(a.due_date,'%d/%m/%Y') as due_date, 
a.team_id, c.team_name, a.milestone_id, d.milestone_name, a.function_ids, a.labels, a.status, a.issue_title  
FROM Issue as a join User as b on a.assignee_id = b.id  join team as c on a.team_id = c.team_id
join milestone as d on a.milestone_id = d.milestone_id; where a.team_id = 1;
*/
#=======================================================================================================
select * from iteration;
create table iteration_evaluation(
	evaluation_id int primary key auto_increment,
    iteration_id int,
    class_id int,
    team_id int,
    user_id int,
    bonus int,
    grade int,
    note varchar(100)
);
select * from class_user;
insert into iteration_evaluation(iteration_id, class_id, team_id, user_id, bonus, grade, note) values
(1,1,1,1,0.5,9,'ok1'),
(2,1,1,2,0.5,10,'ok2'),
(3,1,2,3,0.5,8,'ok3'),
(4,1,4,4,0.5,9,'ok4'),
(1,2,3,6,0.5,8,'ok1'),
(2,2,3,7,0.5,8,'ok2'),
(3,2,4,5,0.5,7,'ok3'),
(4,3,8,8,0.5,10,'ok4'),
(1,4,11,1,0.5,9,'ok1'),
(2,1,1,10,0.5,8,'ok2'),
(3,1,1,11,0.5,6,'ok3'),
(1,2,1,12,0.5,3,'ok1'),
(2,2,1,13,0.5,9,'ok2'),
(3,2,1,14,0.5,10,'ok3'),
(1,3,1,15,0.5,9,'ok1'),
(2,3,1,16,0.5,10,'ok2'),
(3,3,1,17,0.5,9,'ok3'),
(1,4,1,1,0.5,10,'ok1'),
(2,4,1,2,0.5,7,'ok2'),
(3,4,1,3,0.5,8,'ok3'),
(1,5,1,4,0.5,9,'ok1'),
(2,5,1,5,0.5,10,'ok2'),
(3,5,1,6,0.5,6,'ok3'),
(1,6,1,7,0.5,8,'ok1'),
(2,6,1,8,0.5,9,'ok2'),
(2,6,1,9,0.5,9,'ok2'),
(2,6,1,10,0.5,9,'ok2'),
(2,6,1,11,0.5,9,'ok2'),
(2,6,1,12,0.5,9,'ok2'),
(2,6,1,13,0.5,9,'ok2'),
(2,6,1,14,0.5,9,'ok2'),
(2,6,1,15,0.5,9,'ok2'),
(2,6,1,16,0.5,9,'ok2'),
(2,6,1,17,0.5,9,'ok2'),
(2,6,1,1,0.5,9,'ok2'),
(2,6,1,2,0.5,9,'ok2');

#================================================== TEAM EVALUATION Model =========================================================
create table TeamEvaluation(
	team_eval_id int primary key auto_increment,
    evaluation_id int,
    criteria_id int,
    team_id int,
    grade int,
    note varchar(100),
#    foreign key (evaluation_id) references iteration_evaluation(evaluation_id),
    foreign key (criteria_id) references evaluation_criteria(criteria_id),
    foreign key (team_id) references team(team_id),
    constraint greade check ( grade >= 0 and grade <=10 )
);

insert into TeamEvaluation (evaluation_id, criteria_id, team_id, grade, note) values 
(1,1,1,9,'Fix file DAO'),
(1,2,1,10,'Good job'),
(2,3,1,7,'Fix Controller code'),
(2,4,1,9,'Fix SQL file'),
(3,1,2,9,'Fix file DAO'),
(3,2,2,10,'Good job'),
(4,3,2,7,'Fix Controller code'),
(4,4,2,7,'Fix SQL, DAO file'),
(1,1,3,9,'Fix file DAO'),
(1,2,3,10,'Good job'),
(2,3,3,7,'Fix Controller code'),
(2,4,3,9,'Fix SQL file'),
(1,1,4,10,'Good job'),
(1,2,4,10,'Good job'),
(2,3,4,8,'Format code'),
(2,4,5,9,'Fix SQL file'),
(1,1,5,9,'Fix file DAO'),
(1,2,5,10,'Good job'),
(2,3,6,9,'Fix Controller code'),
(2,4,6,9,'Fix SQL file');

select * from TeamEvaluation;

#============================================================== MEMBER MODEL ===========================================================
create table member_evaluation(
	member_eval_id int primary key auto_increment,
    evaluation_id int,
    criteria_id int,
    foreign key (evaluation_id) references iteration_evaluation(evaluation_id),
    foreign key (criteria_id) references evaluation_criteria(criteria_id),
    converted_loc double,
    totalGrade double,
    note varchar(100)
);
insert into member_evaluation( evaluation_id, criteria_id) values
(24,4),(2,3),(3,2),(25,5),(5,4),(6,2),(7,2),(26,5),(9,1),
(10,5),(11,2),(12,4),(13,3),(15,4),(14,2);

select*from class_user;
select*from member_evaluation;
select* from iteration_evaluation;
select*from evaluation_criteria;

select m.member_eval_id, m.evaluation_id, m.criteria_id,e.iteration_id,i.class_id, i.team_id, i.user_id, e.max_loc, i.bonus,i.grade, m.note
from member_evaluation as m join iteration_evaluation as i on m.evaluation_id= i.evaluation_id
							join evaluation_criteria as e on e.criteria_id= m.criteria_id 
                            join class_user as c on c.class_id= i.class_id and c.team_id= i.team_id and c.user_id= i.user_id
                            join user as u on c.user_id= u.id;
                            
select m.member_eval_id, m.evaluation_id, m.criteria_id,e.iteration_id,i.class_id, i.team_id, i.user_id, u.fullName, e.max_loc, i.bonus,i.grade, m.note
from member_evaluation as m join iteration_evaluation as i on m.evaluation_id= i.evaluation_id
							join evaluation_criteria as e on e.criteria_id= m.criteria_id 
                            join class_user as c on c.class_id= i.class_id and c.team_id= i.team_id and c.user_id= i.user_id
                            join user as u on c.user_id= u.id;

select m.member_eval_id, m.evaluation_id, m.criteria_id,e.iteration_id,i.class_id, i.team_id, i.user_id, u.fullName, e.max_loc, i.bonus,i.grade, m.note
from member_evaluation as m join evaluation_criteria as e on e.criteria_id= m.criteria_id 
							join iteration_evaluation as i on m.evaluation_id= i.evaluation_id
                            join user as u on i.user_id= u.id;


#============================================================== LOC MODEL ===========================================================
create table loc_evaluation(
	evaluation_id int primary key auto_increment,
    evaluation_time double,
    evaluation_note varchar(100),
    complexity_id int,
    quality_id int,
    converted_loc double,
    tracking_id int,
    foreign key (tracking_id) references tracking(tracking_id)
);
insert into loc_evaluation(evaluation_time, evaluation_note, complexity_id, quality_id, converted_loc,tracking_id)
values(1.5, 'good', 1, 2, 30,1)
,(1.5, 'bad', 2, 3, 90,2)
,(2.5, 'try hard', 1, 2, 60,3)
,(4.5, 'have to better', 2, 1, 30,4)
,(1.6, 'not bad', 1, 2, 30,5)
,(3.5, 'wow', 2, 2, 60,6)
,(1.6, 'try better', 3, 3, 135,7)
,(5.4, 'still bad', 1, 2, 30,8)
,(1.5, 'good', 2, 1, 30,10)
,(1.5, 'good', 2, 1, 30,11)
,(1.5, 'good', 2, 1, 30,12)
,(1.5, 'good', 2, 1, 30,13)
,(1.5, 'good', 2, 1, 30,14)
,(1.5, 'good', 2, 1, 30,15)
,(1.5, 'good', 2, 1, 30,16)
,(1.5, 'good', 2, 1, 30,17)
,(1.5, 'good', 2, 1, 30,18)
,(1.5, 'good', 2, 1, 30,19)
,(1.5, 'good', 2, 1, 30,20);
select * from loc_evaluation;
SELECT locs.evaluation_id, locs.evaluation_time, locs.evaluation_note,
                         locs.complexity_id, locs.quality_id,
						tracks.tracking_id, tracks.assignee_id FROM loc_evaluation as locs
                         join tracking as tracks
                         on locs.tracking_id = tracks.tracking_id;
