CREATE SCHEMA `spring_study` DEFAULT CHARACTER SET utf8 ;

use spring_study;

drop table users;
create table users(
	id varchar(10) primary key,
	name varchar(20) not null,
	password varchar(10) not null,
	level TINYINT(1) not null,
    login int(10) not null,
    recommend int(10) not null,
    email varchar(20) not null
)

/*
 *
 * 테스트 할 때 사용하는 데이터 삭제문 
use spring_study;
delete from users where name = '박종훈';
commit;

SELECT * FROM spring_study.users;
 * 
 * 
 */
