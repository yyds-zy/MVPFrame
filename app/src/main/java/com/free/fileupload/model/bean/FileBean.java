package com.free.fileupload.model.bean;

import java.util.List;

public class FileBean {

    /**
     * code : 200
     * msg : 查询成功
     * flag : true
     * currentPage : 1
     * pageSize : 10
     * totalSize : 66
     * data : [{"id":22,"uin":"1654486319157","fileName":"1654486319157.mp4","filePath":"C:/xinxin/file/mv/1654486319157.mp4","fileExtension":"mp4","fileUpTime":"2022-06-06 11:31:59:157","fileSize":"1840.0KB","fileState":0},{"id":23,"uin":"1654486530827","fileName":"98c125875425ed9b0d14c8a1849cb30d.mp4","filePath":"C:/xinxin/file/mv/98c125875425ed9b0d14c8a1849cb30d.mp4","fileExtension":"mp4","fileUpTime":"2022-06-06 11:35:30:819","fileSize":"1840.0KB","fileState":0},{"id":24,"uin":"1654487598372","fileName":"37455874e26ba4212fa6dc895fa65655.html","filePath":"C:/xinxin/file/html/37455874e26ba4212fa6dc895fa65655.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:18:372","fileSize":"9.0KB","fileState":0},{"id":25,"uin":"1654487602624","fileName":"6f725fce150f9adb5aa3fa7a26890069.html","filePath":"C:/xinxin/file/html/6f725fce150f9adb5aa3fa7a26890069.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:22:624","fileSize":"9.0KB","fileState":0},{"id":26,"uin":"1654487604031","fileName":"864ddc9216ff051572421f3137469efe.html","filePath":"C:/xinxin/file/html/864ddc9216ff051572421f3137469efe.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:24:031","fileSize":"9.0KB","fileState":0},{"id":27,"uin":"1654487605253","fileName":"bd52891e795b06e4d1c5620e4e974e28.html","filePath":"C:/xinxin/file/html/bd52891e795b06e4d1c5620e4e974e28.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:25:253","fileSize":"9.0KB","fileState":0},{"id":28,"uin":"1654487606462","fileName":"508f9ccf8f0cd5d94934df5707f4914d.html","filePath":"C:/xinxin/file/html/508f9ccf8f0cd5d94934df5707f4914d.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:26:462","fileSize":"9.0KB","fileState":0},{"id":29,"uin":"1654487607529","fileName":"5dcf851f0c678ff317f4d8ac68f26493.html","filePath":"C:/xinxin/file/html/5dcf851f0c678ff317f4d8ac68f26493.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:27:529","fileSize":"9.0KB","fileState":0},{"id":30,"uin":"1654487608525","fileName":"4cf33aec104bd04a50fc422cdb54b72d.html","filePath":"C:/xinxin/file/html/4cf33aec104bd04a50fc422cdb54b72d.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:28:525","fileSize":"9.0KB","fileState":0},{"id":31,"uin":"1654487609378","fileName":"6beab6dfffaa962f4f6e408984fbf17e.html","filePath":"C:/xinxin/file/html/6beab6dfffaa962f4f6e408984fbf17e.html","fileExtension":"html","fileUpTime":"2022-06-06 11:53:29:378","fileSize":"9.0KB","fileState":0}]
     */

    private int code;
    private String msg;
    private boolean flag;
    private int currentPage;
    private int pageSize;
    private int totalSize;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 22
         * uin : 1654486319157
         * fileName : 1654486319157.mp4
         * filePath : C:/xinxin/file/mv/1654486319157.mp4
         * fileExtension : mp4
         * fileUpTime : 2022-06-06 11:31:59:157
         * fileSize : 1840.0KB
         * fileState : 0
         */

        private int id;
        private String uin;
        private String fileName;
        private String filePath;
        private String fileExtension;
        private String fileUpTime;
        private String fileSize;
        private int fileState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUin() {
            return uin;
        }

        public void setUin(String uin) {
            this.uin = uin;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public void setFileExtension(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        public String getFileUpTime() {
            return fileUpTime;
        }

        public void setFileUpTime(String fileUpTime) {
            this.fileUpTime = fileUpTime;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public int getFileState() {
            return fileState;
        }

        public void setFileState(int fileState) {
            this.fileState = fileState;
        }
    }
}
