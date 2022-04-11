package mna.homespital.controller;

import mna.homespital.dto.Pharmacy;
import mna.homespital.service.PharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.PhoneCheckService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/pharmacy")
public class PharmacyController {

    @Autowired
    HttpSession session;

    @Autowired
    PharService pharService;

    //약국메인
    @GetMapping({"", "/"})
    public ModelAndView pharmacyMain() {
        return new ModelAndView("admin/phar/pharmacyIndex");
    }

    //약국 로그인Form
    @GetMapping("/loginForm")
    public ModelAndView pharmacyLogin() {
        return new ModelAndView("admin/phar/pharmacyLoginForm");
    }

    //약국회원가입
    @GetMapping("/joinForm")
    public ModelAndView phamacyJoinForm() {
        return new ModelAndView("admin/pharside/joinForm");
    }

    //용식:약사 로그인
    @PostMapping("login.do")
    public String pharmacyLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {
        try {

            pharService.pharmacyLogin(email, password);
            Pharmacy pharmacy = pharService.getPharInfo(email);
            pharmacy.setPharmacy_password("");
            session.setAttribute("pharmacy", pharmacy);
            return "redirect:/pharmacy/customerList";
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('로그인 실패 : 아이디와 비밀번호를 다시 한 번 확인해주세요.');history.go(-1);</script>");
                out.flush();
            } catch (Exception ee) {
            }
            return "redirect:/pharmacy/loginForm";
        }
    }

    //용식:약사 회원가입
    @PostMapping("join.do")
    public ModelAndView pharmacyJoin(HttpServletRequest request) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setPharmacy_email(request.getParameter("pharmacy_email"));
        pharmacy.setPharmacy_password(request.getParameter("pharmacy_password"));
        pharmacy.setPharmacy_mobile(request.getParameter("pharmacy_mobile"));
        pharmacy.setPharmacy_business(request.getParameter("pharmacy_business"));
        pharmacy.setPharmacy_name(request.getParameter("pharmacy_name"));
        ;
        pharmacy.setPharmacy_phone(request.getParameter("pharmacy_phone"));
        pharmacy.setZip_code(request.getParameter("zipNo"));
        pharmacy.setStreet_address(request.getParameter("roadFullAddr"));
        pharmacy.setDetail_address(request.getParameter("addrDetail"));
        ModelAndView mv = new ModelAndView();
        try {
            pharService.join(pharmacy);
            mv.setViewName("redirect:/pharmacy/loginForm");
        } catch (Exception e) {
            e.printStackTrace();
            mv.setViewName("redirect:/pharmacy/joinForm");
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/phoneCheck", method = RequestMethod.GET)
    //용식: 회원가입 문자전송API
    public String sendSMS(@RequestParam("phone") String userPhoneNumber) { // 휴대폰 문자보내기
        int randomNumber = (int) ((Math.random() * (9999 - 1000 + 1)) + 1000);//난수 생성
        PhoneCheckService phoneCheckService = new PhoneCheckService();
        phoneCheckService.certifiedPhoneNumber(userPhoneNumber, randomNumber);
        System.out.println(randomNumber);
        return Integer.toString(randomNumber);
    }

    //용식:약사 회원가입 이메일중복체크
    @ResponseBody
    @PostMapping("/emailoverlap")
    public boolean emailOverLap(@RequestParam String email) {
        boolean result = false;
        try {
            result = pharService.emailCheck(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //환자진료내역 (인성)
    @GetMapping("/customerList")
    public String customerList(HttpSession session, Model m) throws Exception {
        try {
            Pharmacy pharmacy = (Pharmacy) session.getAttribute("pharmacy");
            int searchNumber = pharmacy.getPharmacy_number();
            List<HashMap<String, Object>> diagnosis = pharService.pharCustomerRecordsList(searchNumber);
            m.addAttribute("diagnosis", diagnosis);
        } catch (Exception e) {
            System.out.println("Catch() join");
            e.printStackTrace();
            return "common/err";
        }
        return "admin/phar/customerList";
    }

    //진료내역 리스트 출력 (인성)
    @ResponseBody
    @GetMapping("/customerRecordsList")
    public ArrayList<HashMap<String, Object>> pharCustomerRecordsList() {
        ArrayList<HashMap<String, Object>> customerList = new ArrayList<>();
        try {
            Pharmacy pharmacy = (Pharmacy) session.getAttribute("pharmacy");
            if (pharmacy == null) throw new Exception("로그인 되어있지않음.");
            customerList = pharService.pharCustomerRecordsList(pharmacy.getPharmacy_number());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }
}