package com.example.finalpractice;

import com.example.finalpractice.entities.Student;
import com.example.finalpractice.repositories.StudentRepository;
import com.example.finalpractice.web.StudentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;/**/
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.View;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class StudentControllerTest {

    Student student;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    StudentRepository studentRepository;


    @Mock
    View mockView;


    @InjectMocks
    StudentController studentController;


    @BeforeEach
    void setup() throws ParseException {
        student = new Student();
        student.setId(1L);
        student.setName("John");

        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        student.setDob(date1);
        student.setPassed(true);
        student.setGpa(3.5);

        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(studentController).setSingleView(mockView).build();
    }

    @Test
    public void findAll_ListView() throws Exception {
        List<Student> list = new ArrayList<Student>();
        list.add(student);
        list.add(student);

        when(studentRepository.findAll()).thenReturn(list);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", list))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("listStudents", hasSize(2)));

        verify(studentRepository, times(1)).findAll();
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findbyid() throws Exception {
        List<Student> list = new ArrayList<Student>();
        list.add(student);
        when(studentRepository.findStudentById(1L)).thenReturn(list);

        mockMvc.perform(get("/index").param("keyword", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", list))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("listStudents", hasSize(1)));

        verify(studentRepository, times(1)).findStudentById(anyLong());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void delete() throws Exception {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        doNothing().when(studentRepository).deleteById(idCapture.capture());

        mockMvc.perform(get("/delete").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/index"));

        assertEquals(1L, idCapture.getValue());
        verify(studentRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void editStudents() throws Exception {
        Student s2 = new Student();
        s2.setId(1L);
        s2.setName("John Mast");
        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        s2.setDob(date1);
        s2.setPassed(true);
        s2.setGpa(3.5);
        Long iid = 1l;
        when(studentRepository.findById(iid)).thenReturn(Optional.of(s2));

        mockMvc.perform(get("/editStudents").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", s2))
                .andExpect(view().name("editStudents"));

        verify(studentRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void formStudents() throws Exception {
        mockMvc.perform(get("/formStudents"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", new Student()))
                .andExpect(view().name("formStudents"));
    }

    @Test
    void save() throws Exception {
        Student s2 = new Student();
//        s2.setName("John Mast");
//        String sDate1 = "2012/11/11";
//        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
//        s2.setDob(date1);
//        s2.setPassed(true);
//        s2.setGpa(3.5);
        when(studentRepository.save(s2)).thenReturn(s2);

        //studentRepository.save(student);
        mockMvc.perform(post("/save").param("student", String.valueOf(s2)))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:index"));

        verify(studentRepository, times(1)).save(s2);
    }
}