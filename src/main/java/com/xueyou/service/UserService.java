package com.xueyou.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.xueyou.model.pojo.QDepartment;
import com.xueyou.model.pojo.QUser;
import com.xueyou.model.pojo.User;
import com.xueyou.model.vo.UserDeptVo;
import com.xueyou.model.vo.UserInDeptVo;
import com.xueyou.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建 by xueyo on 2019/7/19
 */
@Service
public class UserService {

    private final JPAQueryFactory jpaQueryFactory;

    private final UserRepository userRepository;

    public UserService(JPAQueryFactory jpaQueryFactory, UserRepository userRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.userRepository = userRepository;
    }

    /**
     * 根据部门id查询用户基本信息+所属部门信息
     * @param departmentId  部门id
     * @return  用户集合
     */
    public List<UserDeptVo> findByDepartmentId(Integer departmentId) {
        QUser user = QUser.user;
        QDepartment department = QDepartment.department;

        return jpaQueryFactory
                // 投影只取部分字段
                .select(
                        user.username,
                        user.nickname,
                        user.birthday,
                        department.deptName,
                        department.createDate
                )
                .from(user, department)
                .where(user.departmentId.eq(department.id).and(department.id.eq(departmentId)))
                .fetch()
                .stream()
                .map(tuple ->
                        UserDeptVo.builder()
                            .username(tuple.get(user.username ))
                            .nickname(tuple.get(user.nickname ))
                            .birthday(tuple.get(user.birthday ))
                            .deptName(tuple.get(department.deptName))
                            .deptCreateDate(tuple.get(department.createDate ))
                            .build()
                )
                .collect(Collectors.toList());
    }

    /**
     * 根据部门id查询用户基本信息+所属部门提示
     * @param departmentId  部门id
     * @return  用户集合
     */
    public List<UserInDeptVo> findInDeptByDepartmentId(Integer departmentId) {
        QUser user = QUser.user;
        QDepartment department = QDepartment.department;

        return jpaQueryFactory
                // 投影只取部分字段
                .select(
                        user.username,
                        user.nickname,
                        user.birthday,
                        department
                )
                .innerJoin(department)
                .from(user, department)
                // .on(user.departmentId.eq(department.id).and(department.id.eq(departmentId)))
                .where(user.departmentId.eq(department.id).and(department.id.eq(departmentId)))
                .fetch()
                .stream()
                .map(tuple ->
                        UserInDeptVo.builder()
                                .username(tuple.get(user.username ))
                                .nickname(tuple.get(user.nickname ))
                                .birthday(tuple.get(user.birthday ))
                                .department(tuple.get(department))
                                .build()
                )
                .collect(Collectors.toList());
    }


}
