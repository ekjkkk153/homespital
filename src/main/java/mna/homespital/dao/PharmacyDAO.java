package mna.homespital.dao;


import mna.homespital.dto.Pharmacy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface PharmacyDAO {

    //소연 : 약사(Pharmacy)정보 가져오기
    Pharmacy PharInfo(int pharmacy_number) throws Exception;

    //  인성: 약사 세션 email로 number 가져오기
    int getNumberByEmail(String user_email) throws Exception;

    // 인성: customerList 출력
    ArrayList<HashMap<String, Object>> pharCustomerRecordsList(int phar_number) throws Exception;

    //용식: 이메일로 약사 정보 가져오기
    Pharmacy PharmacyQueryMember(String pharmacy_email) throws Exception;

    //용식: 약사 정보 넣기
    void insertPharmacyMember(Pharmacy pharmacy) throws Exception;


}