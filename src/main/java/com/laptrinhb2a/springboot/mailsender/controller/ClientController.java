package com.laptrinhb2a.springboot.mailsender.controller;

import com.laptrinhb2a.springboot.mailsender.dto.ResDTO;
import com.laptrinhb2a.springboot.mailsender.dto.ClientSdi;
import com.laptrinhb2a.springboot.mailsender.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/client")
public class ClientController {

    /*
        https://laptrinhb2a.blogspot.com/2021/08/spring-boot-gui-mail-trong-ung-dung.html
        https://howtodoinjava.com/spring-boot2/send-email-with-attachment/
     */

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/create")
    public ResponseEntity<Void> create( @RequestBody ClientSdi sdi ) {
        clientService.create(sdi);
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<ResDTO>> search(@RequestBody ClientSdi sdi) {
        List<ResDTO> res = clientService.search(sdi);
        return ResponseEntity.ok(res);
    }
}
