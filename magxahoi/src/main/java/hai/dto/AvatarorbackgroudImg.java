package hai.dto;

import org.springframework.web.multipart.MultipartFile;

public class AvatarorbackgroudImg {
    private MultipartFile imgUrl;

    public AvatarorbackgroudImg() {
    }

    public AvatarorbackgroudImg(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
    }

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
    }
}
