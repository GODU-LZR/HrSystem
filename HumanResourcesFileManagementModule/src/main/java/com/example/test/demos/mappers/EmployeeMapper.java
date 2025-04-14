package com.example.test.demos.mappers;

import com.example.test.demos.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmployeeMapper {

    @Insert("INSERT INTO employee (archiveId, orgLevel1, orgLevel2, orgLevel3, positionCategory, positionName, salaryStander, employeePhotoUrl, name, gender, email, phone, qq, mobile, address, postalCode, nationality, birthday, ethnicity, religion, politicalAffiliation, idNumber, socialSecurityNumber, age, education, account, registerTime, resume, familyInfo, remarks, reviewId, isdeleted, registrant, changePerson, reviewer) " +
            "VALUES (#{archiveId}, #{orgLevel1}, #{orgLevel2}, #{orgLevel3}, #{positionCategory}, #{positionName}, #{salaryStander}, #{employeePhotoUrl}, #{name}, #{gender}, #{email}, #{phone}, #{qq}, #{mobile}, #{address}, #{postalCode}, #{nationality}, #{birthday}, #{ethnicity}, #{religion}, #{politicalAffiliation}, #{idNumber}, #{socialSecurityNumber}, #{age}, #{education}, #{account}, #{registerTime}, #{resume}, #{familyInfo}, #{remarks}, #{reviewId}, #{isdeleted}, #{registrant}, #{changePerson}, #{reviewer})")
    void insert(Employee employee);


    @Delete("DELETE FROM employee WHERE archiveId = #{archiveId}")
    void delete(Integer archiveId);

    @Update("UPDATE employee SET salaryStander = #{salaryStander}, employeePhotoUrl = #{employeePhotoUrl}, name = #{name}, gender = #{gender}, email = #{email}, phone = #{phone}, qq = #{qq}, mobile = #{mobile}, address = #{address}, postalCode = #{postalCode}, nationality = #{nationality}, birthday = #{birthday}, ethnicity = #{ethnicity}, religion = #{religion}, politicalAffiliation = #{politicalAffiliation}, idNumber = #{idNumber}, socialSecurityNumber = #{socialSecurityNumber}, age = #{age}, education = #{education}, account = #{account}, registerTime = #{registerTime}, resume = #{resume}, familyInfo = #{familyInfo}, remarks = #{remarks}, reviewId = #{reviewId}, isdeleted = #{isdeleted}, registrant = #{registrant}, changePerson = #{changePerson}, reviewer = #{reviewer} WHERE archiveId = #{archiveId}")
    void update(Employee employee);

    @Select("SELECT * FROM employee")
    List<Employee> select();

    @Select("<script>" +
            "SELECT * FROM employee" +
            "<where>" +
            "  AND isdeleted = 0" +
//            <!-- 固定查询未删除的记录 -->
            "  <if test='orgLevel1 != null'>AND orgLevel1 = #{orgLevel1}</if>" +
                    "  <if test='orgLevel2 != null'>AND orgLevel2 = #{orgLevel2}</if>" +
                    "  <if test='orgLevel3 != null'>AND orgLevel3 = #{orgLevel3}</if>" +
                    "  <if test='positionCategory != null'>AND positionCategory = #{positionCategory}</if>" +
                    "  <if test='positionName != null'>AND positionName = #{positionName}</if>" +
                    "  <if test='archiveId != null'>AND archiveId = #{archiveId}</if>" +
                    "  <if test='reviewId != null'>AND reviewId = #{reviewId}</if>" +

//            <!-- 处理时间条件 -->
            "  <if test='registerTime != null and deadlineTime != null'>" +
                    "    AND registerTime BETWEEN #{registerTime} AND #{deadlineTime}" +
                    "  </if>" +
                    "  <if test='registerTime != null and deadlineTime == null'>" +
                    "    AND registerTime &gt;= #{registerTime}" +
                    "  </if>" +
                    "  <if test='registerTime == null and deadlineTime != null'>" +
                    "    AND registerTime &lt;= #{deadlineTime}" +
                    "  </if>" +

//            <!-- 如果两个时间都为 null，不加任何时间条件 -->
            "</where>" +
                    "</script>")
    List<Employee> selectByConditions(Employee employee);
}
