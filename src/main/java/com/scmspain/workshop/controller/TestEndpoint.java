package com.scmspain.workshop.controller;

import com.google.inject.Singleton;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;
import scmspain.karyon.restrouter.annotation.Endpoint;
import scmspain.karyon.restrouter.annotation.Path;

import javax.ws.rs.HttpMethod;

/**
 * Created by victor.caldentey on 17/6/15.
 */
@Singleton
@Endpoint
public class TestEndpoint {

    @Path(value = "/forlayos", method = HttpMethod.GET )
    public Observable<Void> getForlayosResource(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {


        return response.writeStringAndFlush("Minglanillas!" + "\n")
                .concatWith(response.close());



    }

}
