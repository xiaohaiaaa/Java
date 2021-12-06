package com.hai.test.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.hai.test.entity.CmdResult;

/**
 * @ClassName FfmpegUtil
 * @Description
 * @Author ZXH
 * @Date 2021/12/4 15:55
 * @Version 1.0
 **/
public class FfmpegUtil {

    /**
     * 执行Cmd命令方法
     *
     * @param command 相关命令
     * @return 执行结果
     */
    public static CmdResult runCommand(List<String> command) {
        CmdResult cmdResult = new CmdResult(false, "");
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            final StringBuilder stringBuilder = new StringBuilder();
            final InputStream inputStream = process.getInputStream();
            //启动新线程为异步读取缓冲器，防止线程阻塞
            ThreadPoolUtil.execute(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            process.waitFor();
            cmdResult.setSuccess(true);
            cmdResult.setMsg(stringBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException("ffmpeg执行异常：" + e.getMessage());
        }
        return cmdResult;
    }

    /**
     * 获取视频文件时长
     *
     * @param file 文件
     * @return 时长 格式hh:MM:ss
     * @throws FileNotFoundException 视频不存在抛出此异常
     */
    public static String getVideoTime(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "不存在");
        }
        List<String> commands = new ArrayList<>();
        // cmd /c 是执行完命令后关闭命令窗口的意思
        commands.add("cmd");
        commands.add("/c");
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(file.getAbsolutePath());
        CmdResult result = runCommand(commands);
        String msg = result.getMsg();
        if (result.isSuccess()) {
            Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}.\\d{2}");
            msg = msg.substring(msg.lastIndexOf("Duration:"));
            Matcher matcher = pattern.matcher(msg);
            String time = "";
            if (matcher.find()) {
                time = matcher.group();
            }
            return time;
        } else {
            return "";
        }
    }

    /**
     * MultipartFile转File方法1
     * @param file
     * @return
     * @throws Exception
     */
    public static File multipartFileToFile1(MultipartFile file) throws Exception {
        File toFile;
        if (file.getSize() <= 0) {
            return null;
        } else {
            InputStream ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            OutputStream os = new FileOutputStream(toFile);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        }
        return toFile;
    }

    /**
     * MultipartFile转File方法2
     * @param multiFile
     * @return
     */
    public static File multipartFileToFile2(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
