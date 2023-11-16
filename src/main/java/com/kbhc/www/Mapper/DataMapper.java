package com.kbhc.www.Mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kbhc.www.VO.DBServerInfoVO;
import com.kbhc.www.VO.DataInfoVO;
import com.kbhc.www.VO.DataVO;
import com.kbhc.www.VO.DatabaseVO;

/**
 * - 해당 파일은 xml 파일과 1:1 매핑되는 인터페이스 입니다. (환경파일 내에서 지정함)
 * - 해당 Mapper 폴더는 @Repository 어노테이션을 필수적으로 사용합니다.
 * [ 참고 ]
 * - xml 파일이 아닌 해당 파일 내에서 처리하려면 @Mapper 어노테이션을 사용하길 권장합니다.
 */
@Repository
public interface DataMapper {
	
	int insertData(DataVO dataVo);
	DataInfoVO selectCountData();
	List<DataVO> selectException();
	void deleteExceptionData();
	void deleteAllData();
	DBServerInfoVO showServerID();
	void insertDatabase(DatabaseVO database);
	List<DatabaseVO> selectDBList();
	DatabaseVO selectDB(String server_id);
}
