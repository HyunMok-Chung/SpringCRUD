package com.example.crud;

import com.example.crud.model.StudentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {
    // StudentService 를 Controller 에서 사용
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/create-view")
    public String createView() {
        // 데이터 입력 view 를 출력해주는 메소드
        return "create";
    }

    // CRUD 중 CREATE 기능
    @PostMapping("/create")
    public String create(
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        StudentDto student = studentService.createStudent(name, email);
//        List<StudentDto> student = new ArrayList<>();
//        student.add(studentService.createStudent(name, email));
        // post / redirect / get 패턴 => PRG 패턴
        // 데이터 전송(post) / 데이터 처리를 한 후 다른 곳으로 가라(redirect) / 다시 get
//        return "redirect:/create-view";
        return "redirect:/home";
    }

    // CRUD 중 READ 기능
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute(
                "studentList",
                studentService.readStudentAll()
        );
        return "home";
    }

    @GetMapping("/{id}")  // id를 기준으로 판독할 수 있게끔 url에 주입
    public String read(
            // url 의 값을 메소드의 인자에 넣어주는 어노테이션
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute(
                "student",
                studentService.readStudent(id)
        );
        return "read";
    }

    // TODO url 설정 {"/{id}/edit"} OR {"/{id}/update-view"}
    @GetMapping("/{id}/update-view")
    public String updateView(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute(
                "student",
                studentService.readStudent(id)
        );
        return "update";
    }

    @PostMapping("/{id}/update")
    public String update(
            // TODO create 참조
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        // service 활용하기
        StudentDto studentDto = studentService.updateStudent(id, name, email);
        // 상세보기 페이지로 redirect
        return "redirect:/" + id;
    }

    // deleteView 만들기
    // GetMapping 을 써서
    // Long id는 어떻게?
    // studentDto 를 가지고
    @GetMapping("/{id}/delete-view")
    public String deleteView(
            @PathVariable("id") Long id,
            Model model
    ) {
        StudentDto studentDto = studentService.readStudent(id);
        model.addAttribute(
                "student",
                studentDto
        );
        return "delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable("id") Long id
    ) {
        studentService.deleteStudent(id);
        // update 때는 데이터가 남아있지만
        // delete 는 돌아갈 상세보기가 없기때문에
        // home 으로 돌아감.
        return "redirect:/home";
    }
}
