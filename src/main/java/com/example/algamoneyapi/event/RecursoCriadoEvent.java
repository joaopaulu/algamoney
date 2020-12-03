package com.example.algamoneyapi.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class RecursoCriadoEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    private final HttpServletResponse response;
    private final Long codigo;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo){
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getCodigo() {
        return codigo;
    }
}
