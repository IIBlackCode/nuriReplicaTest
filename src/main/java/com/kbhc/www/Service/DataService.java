package com.kbhc.www.Service;

import java.util.List;

import com.kbhc.www.VO.DBServerInfoVO;
import com.kbhc.www.VO.DataInfoVO;
import com.kbhc.www.VO.DataVO;
import com.kbhc.www.VO.DatabaseVO;

public interface DataService {
	
//	void insertData();
//	DataInfoVO selectCountData();
	List<DataVO> selectEcxeption();
	DataInfoVO selectCountData();
	Boolean deleteExceptionData();
	Boolean deleteAllData();
	DBServerInfoVO showServerID();
	List<DatabaseVO> selectAllDatabase();
}
