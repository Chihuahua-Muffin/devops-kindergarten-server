package devops.kindergarten.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
@Getter @Setter
@NoArgsConstructor
public class ImageFile {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @OneToOne(mappedBy = "thumbnail")
    private Lecture lecture;

    public static ImageFile createImageFile(String fileName, String fileType,byte[] data){
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName(fileName);
        imageFile.setFileType(fileType);
        imageFile.setData(data);
        return imageFile;
    }
    public void update(String fileName, String fileType,byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
