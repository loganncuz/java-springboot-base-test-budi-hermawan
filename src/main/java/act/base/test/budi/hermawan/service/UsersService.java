package act.base.test.budi.hermawan.service;

import act.base.test.budi.hermawan.entity.User;
import act.base.test.budi.hermawan.entity.UserSetting;
import act.base.test.budi.hermawan.payload.ErrorResponsePayload;
import act.base.test.budi.hermawan.payload.UserPayload;
import act.base.test.budi.hermawan.payload.UserResponsePayload;
import act.base.test.budi.hermawan.repo.UserRepository;
import act.base.test.budi.hermawan.repo.UserSettingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSettingRepository userSettingRepository;

    public static boolean isContainSpecialChar(String s)
    {
        return !s.matches("[a-zA-Z0-9]*");
    }

    public Boolean beforeCreateValidation(UserPayload data,ErrorResponsePayload errorResponse){
        boolean isInvalid=false;
        List<String> messages=new ArrayList<>();
        if(data.getSsn().length()>16){
            messages.add(String.format("Invalid value for field SSN, rejected value: %s",data.getSsn()));
            messages.add(String.format("Field Max Size over 16, value size %d",data.getSsn().length()));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);
              isInvalid = true;
        }

        if(data.getFirstName().length()>100 || data.getFirstName().length()<3){
            String text=data.getFirstName().length()>100?"Field Max Size over 100":(
                    data.getFirstName().length()<3?"Field Min Size less 3":""
            );
            messages.add(String.format("Invalid value for field FirstName, rejected value: %s",data.getFirstName()));
            messages.add(String.format("%1$s, value size %2$d",text,data.getFirstName().length()));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);
            isInvalid=true;
        }

        if(data.getLastName().length()>100 || data.getLastName().length()<3){
            String text=data.getLastName().length()>100?"Field Max Size over 100":(
                    data.getLastName().length()<3?"Field Min Size less 3":""
            );
            messages.add(String.format("Invalid value for field LastName, rejected value: %s",data.getLastName()));
            messages.add(String.format("%1$s, value size %2$d",text,data.getLastName().length()));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);
            isInvalid=true;
        }

        if(isContainSpecialChar(data.getFirstName()) || isContainSpecialChar(data.getLastName())){
            String fieldName=isContainSpecialChar(data.getFirstName())?"FirstName":(
                    isContainSpecialChar(data.getLastName())?"LastName":""
            );
            String rejectValue=fieldName.equals("FirstName")?data.getFirstName():(
                    fieldName.equals("LastName")?data.getLastName():""
            );
            messages.add(String.format("Invalid value for field %1$s, rejected value: %2$s",fieldName,rejectValue));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);
            isInvalid=true;
        }

        return isInvalid;
    }

    public ResponseEntity<?> updateUser(Integer id,UserPayload data){
        List<String> messages=new ArrayList<>();
        boolean isInvalid=false;
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        if(StringUtils.isNumeric(data.getSsn())) {
            isInvalid=beforeCreateValidation(data,errorResponse);

            isInvalid=beforeCreateValidation(data,errorResponse);

            if(isInvalid){
                return ResponseEntity
                        .status(errorResponse.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            Integer ssn= Integer.valueOf(data.getSsn());
            System.out.println("body :" + data.getSsn() + "|" + String.format("%016d",ssn));
            Optional<User> user=userRepository.findById(id);
            if(user.isEmpty()){
                messages.add(String.format("Cannot find resource with Id %d",id));
                errorResponse.setCode(30000);
                errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
                errorResponse.setMessage(messages);

                return ResponseEntity
                        .status(errorResponse.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            user.get().setSsn(String.format("%016d",ssn));
            user.get().setFirstName(data.getFirstName());
            user.get().setLastName(data.getLastName());
            user.get().setBirthDate(data.getBirthDate());
            userRepository.save(user.get());
            userResponsePayload.setUserData(user.get());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userResponsePayload);
        }else{
            messages.add(String.format("Invalid value for field SSN, rejected value: %s",data.getSsn()));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }

    }

    public ResponseEntity<?> updateUserSetting(Integer id,List<Map<String, Object>> data){
        ObjectMapper objectMapper=new ObjectMapper();
        List<String> messages=new ArrayList<>();
        boolean isInvalid=false;
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();

        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            messages.add(String.format("Cannot find resource with Id %d",id));
            errorResponse.setCode(30000);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        System.out.println("User :"+user.get().getUserSettings().size());
        user.get().getUserSettings().stream().forEach(n ->
                    {
                        for (Map<String, Object> entry : data)
                            for (Map.Entry<String,Object> record : entry.entrySet()) {
                                if(n.getKeyName().equals(record.getKey())) {
                                    n.setValueName((String) record.getValue());
                                    userSettingRepository.save(n);
                                }
                            }
                        }
                );
        userResponsePayload.setUserData(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponsePayload);
    }


    public ResponseEntity<?> createUser(UserPayload data){
        List<String> messages=new ArrayList<>();
        boolean isInvalid=false;
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        if(StringUtils.isNumeric(data.getSsn())) {

            isInvalid=beforeCreateValidation(data,errorResponse);

            if(isInvalid){
                return ResponseEntity
                        .status(errorResponse.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            Integer ssn= Integer.valueOf(data.getSsn());
            System.out.println("body :" + data.getSsn() + "|" + String.format("%016d",ssn));
            User user=userRepository.findBySsn(String.format("%016d",ssn));

            if(user!=null){
                messages.add(String.format("Record with unique value %s already exists in the system ",user.getSsn()));
                errorResponse.setCode(30001);
                errorResponse.setStatus(HttpStatus.CONFLICT.value());
                errorResponse.setMessage(messages);

                return ResponseEntity
                        .status(errorResponse.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }
            user=new User();
            user.setSsn(String.format("%016d",ssn));
            user.setFirstName(data.getFirstName());
            user.setLastName(data.getLastName());
            user.setBirthDate(data.getBirthDate());
            user.setIsActive(true);
            Set<UserSetting> userSettings=new HashSet<>();
            user.setUserSettings(userSettings);
            User finalUser = userRepository.save(user);
            userSettings.add(new UserSetting("biometric_login",String.valueOf(false)));
            userSettings.add(new UserSetting("push_notification",String.valueOf(false)));
            userSettings.add(new UserSetting("sms_notification",String.valueOf(false)));
            userSettings.add(new UserSetting("show_onboarding",String.valueOf(false)));
            userSettings.add(new UserSetting("widget_order","1,2,3,4,5"));
            userSettings.forEach(j -> {
                j.setUserId(finalUser);
            });
            userSettingRepository.saveAll(userSettings);
            userResponsePayload.setUserData(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userResponsePayload);
        }else{
            messages.add(String.format("Invalid value for field SSN, rejected value: %s",data.getSsn()));
            errorResponse.setCode(30002);
            errorResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }

    }

    public ResponseEntity<?> findUsers(Integer maxRecords,Integer offset){
        List<String> messages=new ArrayList<>();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        userResponsePayload.setMaxRecords((maxRecords==null)?5:maxRecords);
        userResponsePayload.setOffset((offset==null)?5:offset);
        List<User> users=userRepository.findAll();
        if(users.isEmpty()){
            messages.add(String.format("Cannot find resource "));
            errorResponse.setCode(30000);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        userResponsePayload.setUserData(users);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponsePayload);
    }

    public ResponseEntity<?> findUserById(Integer id,Integer maxRecords,Integer offset){
        List<String> messages=new ArrayList<>();
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        userResponsePayload.setMaxRecords((maxRecords==null)?5:maxRecords);
        userResponsePayload.setOffset((offset==null)?5:offset);
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        Optional<User> userOpt=userRepository.findById(id);

        if(userOpt.isEmpty()){
            messages.add(String.format("Cannot find resource with Id %d",id));
            errorResponse.setCode(30000);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        userResponsePayload.setUserData(userOpt.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponsePayload);
    }

    public ResponseEntity<?> refreshUser(Integer id){
        List<String> messages=new ArrayList<>();
        boolean isInvalid=false;
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            messages.add(String.format("Cannot find resource with Id %d",id));
            errorResponse.setCode(30000);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        user.get().setIsActive(true);
        user.get().setDeletedTime(null);
        userRepository.save(user.get());
        userResponsePayload.setUserData(user.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponsePayload);
    }

    public ResponseEntity<?> deleteUser(Integer id){
        List<String> messages=new ArrayList<>();
        boolean isInvalid=false;
        UserResponsePayload userResponsePayload=new UserResponsePayload();
        ErrorResponsePayload errorResponse=new ErrorResponsePayload();
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            messages.add(String.format("Cannot find resource with Id %d",id));
            errorResponse.setCode(30000);
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage(messages);

            return ResponseEntity
                    .status(errorResponse.getStatus())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
        user.get().setIsActive(false);
        user.get().setDeletedTime(new Date());
        userRepository.save(user.get());
        userResponsePayload.setUserData(user.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResponsePayload);
    }


}
