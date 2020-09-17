A) SELECT s.sid, s.name, s.role,c.name,s.sex,s.age,s.email,s.contactno FROM STAFF s, clinic c where role = 'Dentist' and s.cid=c.cid order by c.name

B) SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid and 
   a.sid in (select sid from staff where name = 'William') and appointmentdate between '2020-04-01' and '2020-04-03' order by a.aid

C) SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid and 
   a.cid in (select cid from clinic where name = 'AMS Clinic') and appointmentdate= '2020-04-01'  order by a.aid

D) SELECT a.aid,a.description,a.appointmentdate,a.pid,a.sid,a.cid,a.isvisit,p.name,s.name,c.name FROM appointment a, patient p, staff s, clinic c where a.pid=p.pid and a.sid=s.sid and a.cid=c.cid

E) SELECT a.pid,count(*) as count, p.name from appointment a, patient p where a.isvisit='No' and a.pid=p.pid group by a.pid having count >=1

F) SELECT * FROM treatment where aid =1

G) SELECT * FROM BILL where ispaid = 'No'