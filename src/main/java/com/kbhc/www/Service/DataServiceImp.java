package com.kbhc.www.Service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kbhc.www.Mapper.DataMapper;
import com.kbhc.www.VO.DataInfoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class DataServiceImp implements DataService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * '생성자 주입 형태'로 사용합니다.
     * - Autowired 는 권장되지 않기에 생성자 주입 형태로 구성합니다.
     */
	private final SqlSession sqlSession;
	private final SqlSession masterSqlSession;
	private final SqlSession slaveSqlSession;
	
	public DataServiceImp(SqlSession sqlSession,
			@Qualifier("masterSqlSession") SqlSession masterSqlSession,
			@Qualifier("slaveSqlSession") SqlSession slaveSqlSession) {
		super();
		this.sqlSession = sqlSession;
		this.masterSqlSession = masterSqlSession;
		this.slaveSqlSession = slaveSqlSession;
	}
	
	/**
	 * 1초에 2번 Auto Insert
	 */
	@Scheduled(fixedDelay = 10000, zone = "Asia/Seoul")
	public void insertData() {
		AutoCRUD_thread insert = null;
		insert = new AutoCRUD_thread("WW",masterSqlSession);
		insert.start(); // Thread 시작
	}
	
	/**
	 * 1초에 1번 Auto Select > 화면이 호출될때마다 자동으로 Select됨
	 * @return 
	 */
	@Transactional(readOnly = true)
	@Scheduled(fixedDelay = 20000, zone = "Asia/Seoul")
	public DataInfoVO selectCountData() {
		DataMapper dm = slaveSqlSession.getMapper(DataMapper.class);
		AutoCRUD_thread insert = null;
		insert = new AutoCRUD_thread("RR",masterSqlSession);
		insert.start(); // Thread 시작
		return dm.selectCountData();
	}
}
