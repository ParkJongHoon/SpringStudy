package service;

import domain.User;


/*
 * 작성일: 2018-05-26
 * 작성자: 박종훈
 * 작성내용: UserService에 대한 인터페이스
 * 
 * 선언적 트랜잭션 기능을 구현하기 위해 인터페이스와 구현체를 나누었다
 * 1) 인터페이스
 * 2) 구현체1 - 비즈니스 로직 구현
 * 3) 구현체2 - 트랜잭션 경계설정
 * 
 * 구현체 2 생성후 구현체 1을 인터페이스 형태로 주입하는 형태
 * 
 * 이를 전략 패턴이다.
 * 
 */

public interface UserService {
	void add(User user);
	void upgradeLevels();
}
