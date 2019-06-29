import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class UpdateWarningInformation {

	public String warningLevel;
	Map failingCourseMap=new HashMap();

	public Map classificationWarningLevels(Map subjectMap, Map subjectAttributeMap,String majorAttribute) {
/**该类用于更新预警信息，包括学生绩点，不及格科目与预警等级，根据学生专业属性区分毕业要求*/

		int fail = 0, pass = 0;
		float GPA=0,gradePoint=0;
		double demand=0;
		if(majorAttribute.equals("文学")||majorAttribute.equals("法学")||majorAttribute.equals("教育学")||majorAttribute.equals("管理学")||majorAttribute.equals("艺术学")||majorAttribute.equals("历史学")){
			demand=2.0;
		}else {
			demand=1.8;
		}

			Set keySet=subjectAttributeMap.keySet();
			Iterator iterator=keySet.iterator();
			while (iterator.hasNext()){
				Object key=iterator.next();
				String value=String.valueOf(subjectAttributeMap.get(key));
				if (value.equals("专业课")) {
					float score = Float.parseFloat(subjectMap.get(key).toString());
					if (score < 60) {
						fail = fail + 1;
						failingCourseMap.put("不及格科目" + fail, key);
					} else if (score >= 60) {
						BigDecimal b1 = new BigDecimal(Float.toString((score - 50) / 10));
						BigDecimal b2 = new BigDecimal(Float.toString(gradePoint));
						gradePoint = b1.add(b2).floatValue();
						pass = pass + 1;
					}
				}
			}
			GPA=gradePoint/(pass+fail);
			BigDecimal bigDecimal=new BigDecimal(GPA);
			GPA=bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue();
			failingCourseMap.put("不及格科目数", fail);
		failingCourseMap.put("绩点", GPA);
		if (fail < 4 && GPA > demand * 0.95) {
			warningLevel = "通过";
		} else if (fail >= 6 || GPA <= demand * 0.85) {
			warningLevel = "红色";
		} else if(fail < 6 || GPA <demand * 0.95) {
			warningLevel = "黄色";
		}

		failingCourseMap.put("预警等级", warningLevel);
		return failingCourseMap;
	}
}