package com.weloveloli.avlib.service.connect;


import com.weloveloli.avlib.service.AVService;

public interface HtmlContentReader extends AVService {

    String loadFromUrl(String url);

}
