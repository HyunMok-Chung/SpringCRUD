package com.example.crud;

import com.example.crud.model.StudentDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private Long studentId = 1L;  // long값이다 라는 뜻.
    // 복수의 StudentDto를 담는 변수
    private final List<StudentDto> studentList = new ArrayList<>();

    public StudentService() {
        // 원래는 안되는 과정이지만 테스트를 위해 삽입한 생성자
        createStudent("alex", "alex@gmail.com");
        createStudent("bred", "bread@gmail.com");
        createStudent("chad", "chad@gmail.com");
    }

    // 새로운 StudentDto를 생성하는 메소드
    public StudentDto createStudent(String name, String email) {
        StudentDto newStudent = new StudentDto(studentId, name, email);
        studentId++;
        studentList.add(newStudent);
        return newStudent;
    }

    public List<StudentDto> readStudentAll() {
        return studentList;
    }

    // Service에서 단일 StudentDto를 주는 메소드
    public StudentDto readStudent(Long id) {
        // TODO
        for (StudentDto studentDto : studentList) {
            if (studentDto.getId().equals(id)) {
                return studentDto;
            }
        }
        System.out.println("존재하지 않는 아이디입니다.");
        return null;
//        return studentList
//                .stream()
//                .filter(studentDto -> studentDto.getId().equals(id))
//                .findFirst()
//                .orElse(null);
    }

    // 어떤 학생 데이터를 갱신할 것인지
    // 그 학생의 갱신될 데이터는?
    public StudentDto updateStudent(Long id, String name, String email) {
        // TODO
        // 하나의 studentDto를 찾아서
        int target = -1;
        // studentList의 크기만큼 반복
        for (int i = 0; i < studentList.size(); i++) {
            // id가 동일한 StudentDto 찾았으면
            if (studentList.get(i).getId().equals(id)) {
                // index 기록
                target = i;
                // 반복 종료
                break;
            }
        }

        if (target != -1) {
            // name 과 email 을 바꿔주자
            studentList.get(target).setName(name);
            studentList.get(target).setEmail(email);
            return studentList.get(target);
        } else {
            // 대상을 못 찾았다면
            return null;
        }
    }

    public boolean deleteStudent(Long id) {
        int target = -1;
        // 학생 리스트 살펴봄
        for (int i = 0; i < studentList.size(); i++) {
            // 대상 선정
            if (studentList.get(i).getId().equals(id)) {
                target = i;
                break;
            }
        }

        // 검색 성공 시
        if (target != -1) {
            // 삭제
            studentList.remove(target);
            return true;
        } else {
            return false;
        }
    }
}
