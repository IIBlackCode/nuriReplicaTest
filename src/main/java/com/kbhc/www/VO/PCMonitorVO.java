package com.kbhc.www.VO;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // getter / setter / toString() 사용
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class PCMonitorVO {
	OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
	double load = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad();
	File f = new File("/");

	String CPU_Usage = ("- Usage : "+  load*100.0 );
	String CPU_Usage_Percent = ("- Usage Percent : "+  Math.round(load*100.0) + "%");
	String CPU_Idle_Percent = ("- Idle  Percent : "+  (100 - Math.round(load*100.0)) + "%\n");
	String HDD_Total = ("- Total : " + Math.round( f.getTotalSpace()/(1024*1024) / 1000.0 ) + "(GB)");
	String HDD_Usage = ("- Usage : " + Math.round( (f.getTotalSpace() - f.getUsableSpace())/(1024*1024)/ 1000.0) + "(GB)");
	String HDD_Idle = ("- Idle  : " + Math.round( f.getUsableSpace()/(1024*1024) / 1000.0 ) + "(GB)");
	String HDD_Usage_Percent = ("- Usage Percent : " + Math.round( Double.valueOf(f.getTotalSpace() - f.getUsableSpace())/ Double.valueOf(f.getTotalSpace()) * 100) + "%"   );
	String HDD_Idle_Percent = ("- Idle  Percent : " + Math.round( Double.valueOf(f.getUsableSpace()) / Double.valueOf(f.getTotalSpace()) * 100) + "%\n" );
	String Memory_TotalPhysicalMemorySize = ("- TotalPhysicalMemorySize: " + Math.round( ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() / (1024*1024) / 1000.0) + "(GB)"); 
	String Memory_FreePhysicalMemorySize = ("- FreePhysicalMemorySize: " + Math.round( ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize() / (1024*1024) / 1000.0) + "(GB)");
	String Memory_Idle_Percent = ("- Idle  Percent : " + Math.round( Double.valueOf(((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize()) / Double.valueOf( ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() ) * 100) + "%");
	
}
