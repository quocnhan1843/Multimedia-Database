/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;


/**
 *
 * @author ASUS
 */
public class Dictionary{
    public enum Words{
        INSERT("Insert","Thêm"), DELETE("Delete","Xóa"), SEARCH("Search","Tìm kiếm")
        , DELETE_TREE("Delete tree","Xóa cây"), EXIT("Exit","Thoát"), LOADFILE("Load file","Đọc tệp")
        , VALUE("Value","Tọa độ"), COMPLETE("Complete","Xong"), RESET("Reset","Tạo lại")
        , DEFAULT_FONT("Tahoma","Tahoma"), CLEAR_LOG("Clear log","Xóa nhật ký"), OK("OK","OK"), OPEN("Open","Mở")
        , PATH("Path","Đường dẫn"), LOG("Log","Nhật ký"), INSERT_TITLE("INSERT NODE","THÊM NÚT")
        , DELETE_TITLE("DELETE NODE","XÓA NÚT"), SEARCH_TITLE("SEARCH NODE","TÌM NÚT")
        , LOADFILE_TITLE("INSERT NODE FROM FILE","THÊM NÚT TỪ TỆP")
        , MULTI_DIMENSIONAL_DATA_STRUCTURE("Multi Dimensional Data Structure", "Cấu trúc dữ liệu đa phương tiện")
        , LATENT_SEMANTIC_ANALYSIS("Latent Semantic Analysis","Phân tích ngữ nghĩa tìm ẩn")
        , MAIN_TITLE("Main Windows","Cửa sổ chính"), EMPTY_TREE("Empty tree","Cây rỗng")
        , MULTI_DIMENSIONAL("MULTI DIMENSIONAL TREE", "CÂY ĐA CHIỀU")
        , LABEL("   Label","     Nhãn"), RADIO_LABEL("Label","Nhãn")
        , RADIO_POINT("Value","Tọa độ"), QUEUE("Queue", "Hàng đợi")
        , K_DIMENSIONNAL_TREE("K dimensional tree","Cây k chiều")
        , POINT_QUADTREE("Point quadtree","Cây tứ phân điểm")
        , MATRIX_QUADTREE("Matrix quadtree","Cây tứ phân ma trận")
        , SELECT_TREE("Select tree","Chọn cây")
        , SELECT_DIMENSION("Select dimension","Chọn số chiều"), SAVE("Save","Lưu")
        , SUCCESSFUL("Successful","Thành công"), INSERTING("Inserting new node","Đang thêm nút mới")
        , DELETING("Deleting node","Đang xóa nút"), SEARCHING("Searching node", "Đang tìm kiếm")
        , PLEASE_WAIT("Please wait...","Vui lòng đợi..."), MESSAGE("Massage", "Lời nhắn")
        , NAME_KDIMENSIONALTREE("KD","KD"), NAME_POINTQUADTREE("PQ","PQ"), NAME_MATRIXQUADTREE("MQ","MQ")
        , EMPTY_NODE("EMPPTY","EMPTY");
        private String stringEnglish, stringVietnamese;
        private Words(String str1, String str2){
            stringEnglish = str1;
            stringVietnamese = str2;
        }
        public String getString(){
            if(MultiDimensionalDataStructureUI.getLang() == 1) return stringEnglish;
            return stringVietnamese;
        }
    }
    
    public enum TYPE{
        
        NORMAL("Normal"), IF_IDF("IF*IDF"), IF_IDF_SVD("IF*IDF + SVD"), SVD("SVD");
        
        private String stringValue;
        private TYPE(String str){
            stringValue = str;
        }
        public String getString(){
            return stringValue;
        }
    }
    public enum ERROR{
        ERROR("Error","Lỗi"), ERROR_LABEL_EXISTS("Label already exists","Nhãn đã tồn tại")
        , ERROR_POINT_EXISTS("Point already exists","Điểm đã tồn tại")
        , ERROR_LABEL_NOT_EXISTS("Label already not exists","Nhãn không tồn tại")
        , ERROR_POINT_NOT_EXISTS("Point already not exists","Điểm không tồn tại")
        , ERROR_POINT_INVALID("Points invalid","Các tọa độ không hợp lệ");
        private String stringEnglish, stringVietnamese;
        private ERROR(String str1, String str2){
            stringEnglish = str1;
            stringVietnamese = str2;
        }
        public String getString(){
            if(MultiDimensionalDataStructureUI.getLang() == 1) return stringEnglish;
            return stringVietnamese;
        }
    }
    public enum CONFIRM{
        DETELE_TREE("Do you want to delete this tree?","Bạn muốn xóa cây này không?")
        , DETELE_AND_CREATE_TREE("Do you want to delete this tree and create new tree?","Bạn muốn xóa cây này và tạo cây mới?")
        , CONFIRM("CONFIRM","XÁC NHẬN"), REPLACE("Do you want to replace it?","Bạn có muốn thay thế nó không?")
        , ALREADY_EXISTS("already exists" ,"đã tồn tại");
        private String stringEnglish, stringVietnamese;
        private CONFIRM(String str1, String str2){
            stringEnglish = str1;
            stringVietnamese = str2;
        }
        public String getString(){
            if(MultiDimensionalDataStructureUI.getLang() == 1) return stringEnglish;
            return stringVietnamese;
        }
    }
    public enum Icons{
        INSERT("insertIcon.png"), DELETE("deleteIcon.png")
        , SEARCH("searchIcon.png"), DELETE_TREE("deleteIcon.png")
        , LOADFILE("loadfileIcon.png")
        , MULTI_DIMENSIONAL_DATA_STRUCTURE("binarytreeIcon.png")
        , LATENT_SEMANTIC_ANALYSIS("LSAIcon.png"), ZOOM_IN("zoomIn.png")
        , ZOOM_OUT("zoomOut.png"), FORWARD("forwardIcon.png")
        , SKIP_FORWARD("skipForwardIcon.png"), PLAY_PAUSE("playPauseIcon.png")
        , PLAY_PAUSE_ACTIVE("playPauseActiveIcon.png"), BACKWARD("backwardIcon.png")
        , SKIP_BACKWARD("skipBackwardIcon.png"), PLAY("playIcon.png"), PAUSE("pauseIcon.png")
        , SAVE("saveIcon.png");
        private String stringValue;
        private Icons(String documents){
            stringValue = documents;
        }
        public String getString(){
            return stringValue;
        }
    }
    public enum Font_Size{
        TITLE(30), DEFAULT(14), TITLE_TAB(15), TITLE_CONTROL(24)
        , TITLE_LOADFILE(20),FONT_LOG(18), TREE_TEXT(20);
        private int value;
        private Font_Size(int vl){
            value = vl;
        }
        public int getValue(){
            return value;
        }
    }
    public enum Font{
        DEFAULT("Tahoma");
        private String font;
        private Font(String vl){
            font = vl;
        }
        public String getString(){
            return font;
        }
    }
    public enum COLOR{
        TITLE_TAB(Color.black), TITLE_TAB_SELECT(Color.white)
        , BACKGROUND_NODE(Color.decode("#FCD04A"))
        , BACKGROUND_NODE_WHEN_CHOOSE(Color.decode("#5BC0DE"))
        , BACKGROUND_NODE_WHEN_RUN(Color.decode("#f7fbfe"))
        , FOREGROUND_LABEL(Color.decode("#260DF6"))
        , BOX_NODE(Color.black), TEXT_NODE(Color.black)
        , DEFAULT(Color.BLACK), BACKGROUND_SWAP(Color.decode("#6A0888"));
        private Color value;
        private COLOR(Color vl){
            value = vl;
        }
        public Color getColor(){
            return value;
        }
    }
    public enum SIZE{
        HEIGHT(42),WIDTH(120);
        private int value;
        private SIZE(int vl){
            value = vl;
        }
        public int getValue(){
            return value;
        }
    }
}
