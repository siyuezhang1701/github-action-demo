alter table `Employee`
ADD CONSTRAINT FK_Employee_Company_id
FOREIGN KEY (Company_id) references Company(id)