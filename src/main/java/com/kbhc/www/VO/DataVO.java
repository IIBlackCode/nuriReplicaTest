package com.kbhc.www.VO;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 객체를 구성하는 목적으로 사용되는 파일입니다.
 * - Lombok 기능을 이용하여 간단한 VO 구성을 하였습니다.
 */
@Data   // getter / setter / toString() 사용
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class DataVO {
    private Integer num;
    private String 	contents;
    private Timestamp date;
    private String rw = "r";
}