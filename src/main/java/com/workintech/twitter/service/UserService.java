

/*
* HTTP isteklerini karşılayan yer => Controller
* Controller Service ile konuşur.
* Service Repository ile konuşur
* Repository entityleri kullanarak veritabanıyla konuşur
* Repository entityleri kullanarak service ile konuştuktan sonra Service'e geri döner.
* Service bu bilgiyi alıp Controller'a döner.
*
* Service sadece UsersRequestDto alıp bunu => UsersResponseDto oluşturmalı. (Controllerdan alıp geri controller a dönmeli)
* UsersRequestDto Users yaratmak için yapılan POST requestin validasyonlarını içermeli
*
* UsersResponseDto Controllerdan requeste karşı verilen cevap içeriği. entity'nin görünmemesi gereken fieldlerini
* içermeyen class/record
*
* Repositoryler sadece ve sadece entityleri biliyor. bundan dolayı UsersRequestDto repository'e örneğin kayıt için
* gönderilirken Users entitysine çevrilmek zorundadır.
*
* Repository sadece Entity geri döndürdüğünden dolayı Entityleri (users) controller a göndermeden önce service
* UsersResponseDto ya çevirmelidir!

 */

//HTTP metodları CRUD :
//GET => read
//POST => Create
//PUT/PATCH => update
//DELETE => delete

package com.workintech.twitter.service;


import com.workintech.twitter.dto.patch.UserPatchRequestDto;
import com.workintech.twitter.dto.request.UserRequestDto;
import com.workintech.twitter.dto.response.UserResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto findById(Long id);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(Long id, @Valid UserPatchRequestDto userPatchRequestDto);

    void deleteById(Long id);

    UserResponseDto getLoggedInUserDto();

}

