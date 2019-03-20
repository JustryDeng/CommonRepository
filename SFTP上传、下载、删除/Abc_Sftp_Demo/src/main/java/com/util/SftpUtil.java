package com.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * SFTP工具类
 *
 * @author JustryDeng
 * @date 2019/3/15 12:33
 */
public class SftpUtil {

    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    private static final String NODE_SEPERATOR = "/";

    /** 未调用初始化方法 错误码 */
    private static final int DONOT_INIT_ERROR_CODE = 19940205;

    /** 未调用初始化方法 错误提示信息 */
    private static final String DONOT_INIT_ERROR_MSG = "please invoke init(...) first!";

    private static Session session;

    private static Channel channel;

    private static ChannelSftp channelSftp;

    /**
     * 下载时, 释放资源的标识, 当其值为 0 时， 则调用close()
     * 注:当递归下载时，我们希望 递归内部不进行close(),只有最外层的方法才进行close()
     */
    private static int recursiveDownloadCloseFlag = 0;

    /**
     * 初始化
     *
     * @param ip
     *            sftp地址
     * @param port
     *            sftp端口
     * @param username
     *            用户名
     * @param password
     *            密码
     * @throws JSchException JSch异常
     * @date 2019/3/15 12:41
     */
    public static void init(String ip, Integer port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        jsch.getSession(username, ip, port);
        session = jsch.getSession(username, ip, port);
        session.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        session.setConfig(sshConfig);
        session.connect();
        logger.info("Session connected!");
        channel = session.openChannel("sftp");
        channel.connect();
        logger.info("Channel connected!");
        channelSftp = (ChannelSftp) channel;
    }

    /**
     * 上传
     *
     * @param remoteDir
     *            要上传到sftp上哪一个文件夹  【绝对路径】,如:/files/test/
     * @param src
     *            要上传的文件的位置         【绝对路径】,如: /var/abc.txt
     *
     * @throws SftpException 当未初始化就调用此方法时，会出现异常；当sftp上传出错时会抛出SftpException
     * @date 2019/3/15 12:44
     */
    public static void upload(String remoteDir, String src)
            throws SftpException {
        if (channelSftp == null) {
            throw new SftpException(DONOT_INIT_ERROR_CODE, DONOT_INIT_ERROR_MSG);
        }
        remoteDir = handllePath(remoteDir, true, true);
        String fileName = src.substring(src.lastIndexOf(NODE_SEPERATOR) + 1);
        try {
            // 确保文件夹存在
            pMkdirSftp(remoteDir);
            // 执行上传
            channelSftp.put(src,remoteDir + fileName);
            logger.info("upload FROM 【{}】 TO 【{}】 success!", src, remoteDir + fileName);
        } finally {
            close();
        }
    }

    /**
     * 文件下载
     *
     * @param remoteDirOrRemoteFile
     *             要下载的sftp上的文件 或 sftp上的文件夹 【绝对路径】
     *
     * @param localDir
     *             用于存放下载的文件的本地文件夹 【绝对路径】
     *             注:可以不存在，会自动创建
     *             注:可以不存在，会自动创建
     *
     * @param recursiveDownload
     *             当remoteDirOrRemoteFile指向文件夹时，此参数生效；
     *             为false时,
     *                  只下载remoteDirOrRemoteFile文件夹下的所有文件,remoteDirOrRemoteFile下的所
     *                  有文件夹(及里面的内容)将会被忽略
     *              为true时,
     *                      下载remoteDirOrRemoteFile文件夹下的所有文件、文件夹(及里面的内容)
     *
     * @return 下载到本地的文件的 绝对路径名  集合   如: /var/data/down/meinv.png
     * @throws SftpException 当未初始化就调用此方法是，会出现异常；当sftp下载出错时会抛出SftpException
     * @date 2019/3/15 12:44
     */
    public static List<String> download(String remoteDirOrRemoteFile, String localDir, boolean recursiveDownload)
            throws SftpException {
        if (channelSftp == null) {
            throw new SftpException(DONOT_INIT_ERROR_CODE, DONOT_INIT_ERROR_MSG);
        }
        // 保证 localDir 以 “/” 结尾
        localDir = handllePath(localDir, false, true);
        // 确保本地文件夹存在
        pMkdirLocal(localDir);
        List<String> fileNameList = new ArrayList<>();
        String localAbsoluteFilename;
        String sftpAbsoluteFilename;
        try {
            if (isDirectory(remoteDirOrRemoteFile)) {
                // 保证 remoteDirOrRemoteFile 以 “/” 开头,以 “/” 结尾
                remoteDirOrRemoteFile = handllePath(remoteDirOrRemoteFile, true, true);
                Vector<?> vector = channelSftp.ls(remoteDirOrRemoteFile);
                String fileName;
                // 列出文件名
                for (Object item : vector) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
                    fileName = entry.getFilename();
                    if (invalidFileName(fileName)) {
                        continue;
                    }
                    sftpAbsoluteFilename = remoteDirOrRemoteFile + fileName;
                    localAbsoluteFilename = localDir + fileName;
                    if (!isDirectory(sftpAbsoluteFilename)) {
                        channelSftp.get(sftpAbsoluteFilename, localAbsoluteFilename);
                        fileNameList.add(localAbsoluteFilename);
                        logger.info("downloaded file FROM 【{}】 TO 【{}】 ", sftpAbsoluteFilename, localAbsoluteFilename);
                        continue;
                    }
                    // 下载文件夹
                    if (recursiveDownload) {
                        recursiveDownloadCloseFlag++;
                        download(sftpAbsoluteFilename, localAbsoluteFilename, true);
                        recursiveDownloadCloseFlag--;
                    }
                }
            } else {
                // 保证 remoteDirOrRemoteFile 以 “/” 开头
                remoteDirOrRemoteFile = handllePath(remoteDirOrRemoteFile, true, false);
                localAbsoluteFilename = localDir + getFilenameFromPath(remoteDirOrRemoteFile);
                channelSftp.get(remoteDirOrRemoteFile, localAbsoluteFilename);
                fileNameList.add(localAbsoluteFilename);
                logger.info("downloaded file FROM 【{}】 TO 【{}】 ", remoteDirOrRemoteFile, localAbsoluteFilename);
            }
        } finally {
            if (recursiveDownloadCloseFlag == 0) {
                close();
            }
        }
        return fileNameList;
    }


    /**
     * 删除文件 或 删除文件夹
     * 注: 如果是文件夹， 不论该文件夹中有无内容，都能删除， 因此:此方法慎用
     *
     * @param remoteDirOrRemoteFile
     *            要删除的文件  或 文件夹
     * @throws SftpException
     *             当未初始化就调用此方法是，会出现异常；当sftp删除出错时会抛出SftpException
     * @date 2019/3/17 11:33
     */
    public static void delete(String remoteDirOrRemoteFile) throws SftpException {
        if (channelSftp == null) {
            throw new SftpException(DONOT_INIT_ERROR_CODE, DONOT_INIT_ERROR_MSG);
        }
        List<String> targetFileOrDirContainer = new ArrayList<>(8);
        targetFileOrDirContainer.add(remoteDirOrRemoteFile);
        List<String> toBeDeletedEmptyDirContainer = new ArrayList<>(8);
        if (isDirectory(remoteDirOrRemoteFile)) {
            toBeDeletedEmptyDirContainer.add(remoteDirOrRemoteFile);
        }
        try {
            collectToBeDeletedEmptyDir(toBeDeletedEmptyDirContainer, targetFileOrDirContainer);
            if (!toBeDeletedEmptyDirContainer.isEmpty()) {
                String targetDir;
                for (int i = toBeDeletedEmptyDirContainer.size() - 1; i >= 0; i--) {
                    targetDir = toBeDeletedEmptyDirContainer.get(i);
                    channelSftp.rmdir(targetDir);
                    logger.debug("rmdir 【{}】 success!", targetDir);
                }
            }
        } finally {
            close();
        }
    }

    /**
     * 删除相关文件 并 采集所有 需要被删除的 文件夹
     *
     * 注: 如果是文件夹， 不论该文件夹中有无内容，都能删除， 因此:此方法慎用
     *
     * @param toBeDeletedEmptyDirContainer
     *            所有待删除的空文件夹集合
     *
     * @param targetFileOrDirContainer
     *            本次, 要删除的文件的集合   或   本次, 要删除的文件所在文件夹的集合
     *
     * @throws SftpException 当未初始化就调用此方法是，会出现异常；当sftp删除出错时会抛出SftpException
     * @date 2019/3/17 11:33
     */
    private static void collectToBeDeletedEmptyDir(List<String> toBeDeletedEmptyDirContainer,
                                            List<String> targetFileOrDirContainer)
            throws SftpException {
        List<String> todoCallDirContainer = new ArrayList<>(8);
        List<String> subfolderList;
        for (String remoteDirOrRemoteFile : targetFileOrDirContainer) {
            subfolderList = fileDeleteExecutor(remoteDirOrRemoteFile);
            toBeDeletedEmptyDirContainer.addAll(subfolderList);
            todoCallDirContainer.addAll(subfolderList);
        }
        if (!todoCallDirContainer.isEmpty()) {
            collectToBeDeletedEmptyDir(toBeDeletedEmptyDirContainer, todoCallDirContainer);
        }
    }

    /**
     * 删除remoteDirOrRemoteFile指向的文件 或 删除remoteDirOrRemoteFile指向的文件夹下的所有子级文件
     * 注: 如果是文件夹， 只会删除该文件夹下的子级文件；不会删除该文件夹下的孙子级文件(如果有孙子级文件的话)
     *
     * @param remoteDirOrRemoteFile
     *            要删除的文件 或 要 文件夹   【绝对路径】
     *
     * @return remoteDirOrRemoteFile指向的文件夹 下的 文件夹集合
     *             注: 如果remoteDirOrRemoteFile指向的是文件的话，返回空的集合
     *             注: 只会包含子级文件夹，不包含孙子级文件夹(如果有孙子级文件夹的话)
     *
     * @throws SftpException 当未初始化就调用此方法是，会出现异常；当sftp删除出错时会抛出SftpException
     * @date 2019/3/15 12:44
     */
    private static List<String> fileDeleteExecutor (String remoteDirOrRemoteFile) throws SftpException {
        List<String> subfolderList = new ArrayList<>(8);
        // 如果是文件，直接删除
        if (!isDirectory(remoteDirOrRemoteFile)) {
            channelSftp.rm(remoteDirOrRemoteFile);
            logger.debug("rm 【{}】 success!", remoteDirOrRemoteFile);
            return subfolderList;
        }
        // 保证 remoteDirOrRemoteFile 以 “/” 开头,以 “/” 结尾
        remoteDirOrRemoteFile = handllePath(remoteDirOrRemoteFile, true, true);
        Vector<?> vector = channelSftp.ls(remoteDirOrRemoteFile);
        String fileName;
        String sftpAbsoluteFilename;
        // 列出文件名
        for (Object item : vector) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item;
            fileName = entry.getFilename();
            if (invalidFileName(fileName)) {
                continue;
            }
            sftpAbsoluteFilename = remoteDirOrRemoteFile + fileName;
            // 如果是文件，直接删除
            if (!isDirectory(sftpAbsoluteFilename)) {
                channelSftp.rm(sftpAbsoluteFilename);
                logger.debug("rm 【{}】 success!", sftpAbsoluteFilename);
                continue;
            }
            subfolderList.add(sftpAbsoluteFilename);
        }
        return subfolderList;
    }

    /**
     * 判断SFTP上的path是否为文件夹
     * 注:如果该路径不存在，那么会返回false
     *
     * @param path
     *            SFTP上的路径
     * @return 判断结果
     * @throws SftpException 当path不存在时抛出SftpException异常
     * @date 2019/3/15 17:57
     */
    private static boolean isDirectory(String path) throws SftpException {
        // 合法的错误id
        int legalErrorId = 4;
        try {
            channelSftp.cd(path);
            return true;
        } catch (SftpException e) {
            logger.debug(" channelSftp.cd() fail!  message -> {}, id -> {}", e.getMessage(), e.id);
            // 如果 path不存在，那么报错信息为【No such file】，错误id为【2】
            // 如果 path存在，但是不能cd进去，那么报错信息形如【Can't change directory: /files/sqljdbc4-3.0.jar】，错误id为【4】
            if (legalErrorId == e.id) {
                return false;
            }
            throw new SftpException(e.id, e.getMessage(), e);
        }
    }

    /**
     * 从给定路径中截取文件名
     *
     * @param path
     *            路径，  如: /files/abc/info.yml
     * @return  文件名， 如: info.yml
     * @date 2019/3/15 17:36
     */
    private static String getFilenameFromPath(String path){
        return path.substring(path.lastIndexOf(NODE_SEPERATOR) + 1);
    }

    /**
     * 判断是否为无效的文件名
     * 注:文件名(夹)名为【.】或【..】时，是无效的
     *
     * @param fileName
     *            文件名
     * @return  是有无效
     * @date 2019/3/15 17:06
     */
    private static boolean invalidFileName(String fileName) {
        return ".".equals(fileName) || "..".equals(fileName);
    }

    /**
     * (在本地上)创建文件夹
     * 注:如果要创建的文件夹的长辈级目录不存在，那么会进行相应的创建
     *
     * @param dirPath
     *            文件夹 全路径名 ， 如: /var/data/excel/
     * @date 2019/3/15 15:30
     */
    private static void pMkdirLocal (String dirPath) {
        File file = new File(dirPath);
        if(!file.exists()){
            boolean result = file.mkdirs();
            logger.debug("dir path 【{}】 is not exist, try to create it, result is -> {}", dirPath, result);
        }

    }

    /**
     * 路径处理器
     *
     * 根据参数控制处理类型，如:
     * 当: originPath 为【var/appps】时，
     * 当: handleHead 为 true, 处理结果为【/var/appps】
     * 当: handleTail 为 true, 处理结果为【var/appps/】
     * 当: handleHead 和 handleTail 均为 true, 处理结果为【/var/appps/】
     *
     * @param originPath
     *            要处理的路径
     * @param handleHead
     *            处理 起始处
     * @param handleTail
     *            处理 结尾处
     *
     * @return  处理后的路径
     * @date 2019/3/15 14:08
     */
    private static String handllePath(String originPath, boolean handleHead, boolean handleTail){
        if(originPath == null || "".equals(originPath.trim())) {
            return NODE_SEPERATOR;
        }
        if(handleHead && !originPath.startsWith(NODE_SEPERATOR)) {
            originPath = NODE_SEPERATOR.concat(originPath);
        }
        if (handleTail && !originPath.endsWith(NODE_SEPERATOR)) {
            originPath = originPath.concat(NODE_SEPERATOR);
        }
        return originPath;
    }

    /**
     * (在SFTP上)创建文件夹
     * 注:如果要创建的文件夹的长辈级目录不存在，那么会进行相应的创建
     *
     * @param dirPath
     *            文件夹 全路径名 ， 如: /files/a/b/c/
     * @throws SftpException 创建文件夹异常
     * @date 2019/3/15 15:30
     */
    private static void pMkdirSftp (String dirPath) throws SftpException {
        dirPath = handllePath(dirPath, true, true);
        String[] nodeArray = dirPath.split(NODE_SEPERATOR);
        String currPath = NODE_SEPERATOR;
        for (String node : nodeArray) {
            if ("".equals(node.trim())) {
                continue;
            }
            try {
                currPath = currPath + node + NODE_SEPERATOR;
                channelSftp.cd(currPath);
            } catch (SftpException e) {
                // 文件夹不存在时
                channelSftp.mkdir(currPath);
                logger.debug("no such dir -> {} ; try to create it, result is success!", currPath);
            }
        }
    }

    /**
     * 释放资源
     *
     * @date 2019/3/15 12:47
     */
    private static void close() {
        if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
                channelSftp.exit();
        }
        if (channel != null && channel.isConnected()) {
                channel.disconnect();
        }
        if (session != null && session.isConnected()) {
                session.disconnect();
        }
        logger.info("exit sftp!");
    }

}