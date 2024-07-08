package com.shuige.weblog.admin.service;

import com.shuige.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {

    Response uploadFile(MultipartFile file);
}
