use sql_register;
/*select stu.FirstName, sub.SubjectName, sst.Grade1, sst.Grade2, sst.Grade3, sst.Grade4, sst.Grade5  from subjects_subscriptions_table as sst
inner join students_table as stu on sst.StudentId=stu.Id
inner join subjects_table as sub on sst.SubjectId=sub.Id*/

select st.FirstName, ROUND(avg(Grade1), 2) as average_grade from subjects_subscriptions_table as sst 
inner join students_table as st on st.Id=sst.StudentId
where StudentId=4;