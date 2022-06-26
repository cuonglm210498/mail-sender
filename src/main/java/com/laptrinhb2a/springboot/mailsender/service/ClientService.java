package com.laptrinhb2a.springboot.mailsender.service;

import com.laptrinhb2a.springboot.mailsender.dto.ResDTO;
import com.laptrinhb2a.springboot.mailsender.dto.ClientSdi;

import java.util.List;

public interface ClientService {
    ResDTO create(ClientSdi sdi);

    List<ResDTO> search(ClientSdi sdi);
}
