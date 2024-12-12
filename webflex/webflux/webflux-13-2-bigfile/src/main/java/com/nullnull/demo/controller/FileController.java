package com.nullnull.demo.controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * 文件上传操作，单文件操作与多文件上传
 *
 * @author nullnull
 * @since 2024/12/12
 */
@RestController
public class FileController {

    @RequestMapping("/single")
    public Mono<String> singleFile(@RequestPart("file") Mono<FilePart> file) {
        return file.map(filePart -> {
                    Path tmpFile = null;
                    try {
                        tmpFile = Files.createTempFile("file-", filePart.filename());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("文件路径：" + tmpFile.toAbsolutePath());
                    AsynchronousFileChannel channel = null;
                    try {
                        channel = AsynchronousFileChannel.open(tmpFile, StandardOpenOption.WRITE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DataBufferUtils.write(filePart.content(), channel, 0)
                            .doOnNext(System.out::println)
                            .doOnComplete(() -> {
                                System.out.println("文件拷贝完成");
                            })
                            .subscribe();
                    return tmpFile;
                }).map(tmp -> tmp.toFile())
                .flatMap(fileSingle -> file.map(FilePart::filename));
    }

    @RequestMapping(value = "/multi")
    public Mono<List<String>> multiFiles(@RequestPart("file") Flux<FilePart> filePartFlux) {
        return filePartFlux.map(filePart -> {
                    Path tmpFile = null;
                    try {
                        tmpFile = Files.createTempFile("m-file-", filePart.filename());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("m-文件路径：" + tmpFile.toAbsolutePath());
                    //对每个filePart执行写文件操作，零拷贝操作
                    filePart.transferTo(tmpFile.toFile());
                    //返回Path对象
                    return tmpFile;
                })
                .map(mfile -> mfile.toFile())
                .flatMap(fileSingle -> filePartFlux.map(FilePart::filename))
                .collectList();
    }

}
